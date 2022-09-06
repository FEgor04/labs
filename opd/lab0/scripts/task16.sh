#!/usr/bin/bash
ls -lR  accelgor3 2>/tmp/errors | grep -v "total\|^$\|:$" | sort -k2
