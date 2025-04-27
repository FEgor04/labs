DATE=$(date "+%Y-%m-%d_%H:%M:%S")
BACKUP_DIR="/var/db/postgres1/backup"

REMOTE_SERVER="postgres2@pg184"

echo "Starting backup..."

mkdir -p "$BACKUP_DIR/$DATE"
if [ $? -ne 0 ]; then
    echo "Unable to create backup directory"
    terminate_backup
fi

terminate_backup () {
    echo "Backup process stopped due to error."
    exit 1
}

echo "Creating backup using pg_basebackup..."
pg_basebackup -D "$BACKUP_DIR/$DATE" -F tar -h localhost -z -p 9115
if [ $? -ne 0 ]; then
    echo "Unable to create database backup using pg_basebackup"
    terminate_backup
fi

echo "Archiving backup directory into tar file..."

tar czf "$BACKUP_DIR/$DATE.tar.gz" -C "$BACKUP_DIR" "$DATE"
if [ $? -ne 0 ]; then
    echo "Unable to archive backup directory"
    terminate_backup
fi

echo "Sending backup files to remote server..."
scp "$BACKUP_DIR/$DATE".tar.gz "$REMOTE_SERVER:~/backup"
if [ $? -ne 0 ]; then
    echo "Unable to transfer backup to remote server"
    terminate_backup
fi

echo "Deleting old backups on host machine"
# find "$BACKUP_DIR" -type d -mtime +7 -exec rm -rf {} \;
if [ $? -ne 0 ]; then
    echo "Unable to delete old backups on host machine"
    terminate_backup
fi

echo "Deleting old backups on remote machine"
ssh "$REMOTE_SERVER" bash remove_old.sh
if [ $? -ne 0 ]; then
    echo "Unable to delete old backups on remote machine"
    terminate_backup
fi

echo "Backup created successfully: $BACKUP_DIR/$DATE!"