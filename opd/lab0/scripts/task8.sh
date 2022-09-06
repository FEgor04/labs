wc -m $(ls *y */*y */*/*y */*/*/*y */*/*/*/*y */*/*/*/*/*y */*/*/*/*/*/*y */*/*/*/*/*/*/*y 2>/tmp/test) 2>/tmp/test | grep -v "total" | sort -r
