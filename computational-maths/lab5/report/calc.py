y = [1.2557, 2.1764, 3.1218, 4.0482, 5.9875, 6.9195, 7.8359]
X2 = 0.384
x0 = 0.4
h = 0.05
t = (X2 - x0) / h

def delta(i, k):
    if(k == 0):
        return y[i]
    return delta(i + 1, k - 1) - delta(i, k - 1)

ans = [
    delta(3, 0),
    t * delta(2, 1),
    t * (t+1) / 2 * delta(2, 2),
    t * (t+1) * (t-1) / 6 * delta(1, 3),
    t * (t+1) * (t-1) * (t+2) / 24 * delta(1, 4),
    t * (t+1) * (t-1) * (t+2) * (t-2) / 120 * delta(0, 5),
    t * (t+1) * (t-1) * (t+2) * (t-2) * (t+3) / 720 * delta(0, 6),
]

for i in ans:
    print(f"{i:.3f}", end = " + ")
print("")
print(f"{sum(ans):.3f}")
