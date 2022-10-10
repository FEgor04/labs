#!/bin/python3
from sys import exit

n, iCnt = (7, 4) # общее количество разрядов, количество информационных разрядов
rCnt = n - iCnt

data = [int(x) for x in input()]

def verifyData(data):
    if len(data) != n:
        return f"Некорректное сообщение. Ожидалось {n} символов, получено: {len(data)}"
    for i in data:
        if i not in [0, 1]:
            return f"Сообщение не должно содержать символов, отличных от [0, 1]."
    return ""

err = verifyData(data)
if err != "":
    print(err)
    exit()

def calcCheckPositions(n):
    ans = []
    i = 1
    while i < n:
        ans += [i-1]
        i = i * 2
    return ans

def calcSyndrom(n, iCnt, data):
    ans = []
    for i in range(rCnt):
        j = 2**i - 1
        per = 2**i
        now = 0;
        while j < n:
            for k in range(j, min(j+per, n)):
                now = now ^ data[k]
            j += 2*per
        ans += [now]
    return ans

syndrom = ""
for i in calcSyndrom(n, iCnt, data):
    syndrom = str(i) + syndrom

print(f"Синдром: {syndrom[::-1]}")

if syndrom != "000":
    print(f"Ошибка в бите #{int(syndrom, 2)}")
    data[int(syndrom, 2) - 1] ^= data[int(syndrom, 2) - 1] 
else:
    print(f"Либо ошибок нет, либо они везде")

goodMessage = ""
for i in range(n):
    if i not in calcCheckPositions(n):
        goodMessage += str(data[i])

print(f"Корректное сообщение: {goodMessage}")
