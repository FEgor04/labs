#!/usr/bin/bash
ls -R accelgor3/ 2>/dev/null | grep -v "^$\|:" | sort -r
