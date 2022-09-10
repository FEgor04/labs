#!/usr/bin/bash
echo "TASK 12:"
ls -R accelgor3/ 2>/dev/null | grep -v "^$\|:" | sort -r
