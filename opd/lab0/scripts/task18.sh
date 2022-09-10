echo "TASK 18:"
ls -l *t */*t */*/*t */*/*/*t */*/*/*/*t */*/*/*/*/k */*/*/*/*/*/*t */*/*/*/*/*/*/*t 2>/tmp/errors | grep -v "^$\|total\|:$" | sort -k9  | tail -3
