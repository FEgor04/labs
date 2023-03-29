#!/bin/bash
export LOGIN=test
export PASSWORD=test
for i in {1..2}
do
echo "show" | java -jar target/client.jar 1234 &
done

