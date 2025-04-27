set -e 

BACKUP_DIR="$HOME/backup"
PGDATA="/var/db/postgres1/pgdata"
PGUSER="postgres1"
TMP_DIR="$HOME/tmp_restore"

log() {
  echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1"
}


LAST_ARCHIVE=$(ls -t $BACKUP_DIR/*.tar.gz | head -n1)
if [ -z "$LAST_ARCHIVE" ]; then
  echo "ERROR: No archive file found in $BACKUP_DIR"
  exit 1
fi

log "Using archive: $LAST_ARCHIVE"


rm -rf "$TMP_DIR"
mkdir -p "$TMP_DIR"


log "Extracting backup archive to $TMP_DIR"
tar -xzvf "$LAST_ARCHIVE" -C "$TMP_DIR"


INNER_DIR=$(find "$TMP_DIR" -mindepth 1 -maxdepth 1 -type d)
log "Found extracted directory: $INNER_DIR"


log "Cleaning old PGDATA: $PGDATA"
rm -rf "$PGDATA"/*
mkdir -p "$PGDATA"


log "Extracting base.tar.gz"
tar -xzvf "$INNER_DIR/base.tar.gz" -C "$PGDATA"


log "Extracting pg_wal.tar.gz"
mkdir -p "$PGDATA/pg_wal"
tar -xzvf "$INNER_DIR/pg_wal.tar.gz" -C "$PGDATA/pg_wal"

log "Recreating pg_tblspc"
rm -rf "$PGDATA/pg_tblspc"
mkdir -p "$PGDATA/pg_tblspc"

log "Setting correct permissions"
chown -R "$PGUSER":"postgres" "$PGDATA"
chmod 700 "$PGDATA"


if [ -f "$PGDATA/backup_label" ]; then
  log "Removing old backup_label"
  rm -f "$PGDATA/backup_label"
fi

log "Restore completed successfully!"


if [ -f "$PGDATA/recovery.signal" ]; then
  log "Removing recovery.signal because WALs are already present"
  rm -f "$PGDATA/recovery.signal"
fi


echo "You can now start PostgreSQL with:"
echo "pg_ctl -D $PGDATA start"
