wc -l $(ls -R k* */k* */*/k* */*/*/k* */*/*/*/k* */*/*/*/*/*/k* */*/*/*/*/*/*/k*) | grep -v "total" | sort -r
