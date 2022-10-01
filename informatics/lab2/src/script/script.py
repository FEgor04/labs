n, iCnt = (7, 4) # общее количество разрядов, количество информационных разрядов
rCnt = n - iCnt

data = [int(x) for x in input()]


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

badBitId = ""
for i in calcSyndrom(n, iCnt, data):
    badBitId = str(i) + badBitId

print(f"Синдром: {badBitId[::-1]}")

if badBitId != "000":
    print(f"Ошибка в бите #{int(badBitId, 2)}")
    data[int(badBitId, 2) - 1] ^= data[int(badBitId, 2) - 1] 
else:
    print(f"Либо ошибок нет, либо они везде")

goodMessage = ""
for i in range(n):
    if i not in calcCheckPositions(n):
        goodMessage += str(data[i])

print(f"Корректное сообщение: {goodMessage}")
