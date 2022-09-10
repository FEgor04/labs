#!/usr/bin/bash
echo "TASK 17:"
cat -n accelgor3/{*,*/*,*/*/*,*/*/*/*,*/*/*/*/*,*/*/*/*/*/*,*/*/*/*/*/*/*,*/*/*/*/*/*/*/*} 2>/tmp/errors | sort -k2 -k3 -k4 -k5 -k6 -k7 -k8