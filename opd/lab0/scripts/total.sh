#!/usr/bin/bash
# Так как все папки должны быть расположены в директории lab0, сразу создадим ее и перейдем в нее
mkdir -p lab0 && cd lab0
bash ../scripts/create.sh
bash ../scripts/chmod.sh
bash ../scripts/p3.sh

bash ../scripts/task1.sh
bash ../scripts/task2.sh
bash ../scripts/task3.sh
bash ../scripts/task4.sh
bash ../scripts/task5.sh
bash ../scripts/task6.sh
bash ../scripts/task7.sh
bash ../scripts/task8.sh
bash ../scripts/task9.sh
bash ../scripts/task10.sh
bash ../scripts/task11.sh
bash ../scripts/task12.sh
bash ../scripts/task13.sh
bash ../scripts/task14.sh
bash ../scripts/task15.sh
bash ../scripts/task16.sh
bash ../scripts/task17.sh
bash ../scripts/task18.sh

bash ../scripts/delete.sh

