#!/usr/bin/bash
echo "TASK 16:"
ls -lR  accelgor3 2>/tmp/errors | grep -v "total\|^$\|:$" | sort -k2
