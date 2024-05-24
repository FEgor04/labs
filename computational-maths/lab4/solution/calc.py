import matplotlib.pyplot as plt
from solution import linear, quadratic
import numpy as np

def y(x): 
    return 17*x/(x**4 + 16)

def debug_array_sum(arr):
    for i in arr:
        print(f"{i:.4f}", end = " + ")
    print(f"= {arr.sum():.4f}")

a = -4
h = 0.4
n = 11

xs = []

for i in range(n):
    x = -4 + i * 0.4
    xs += [x]

xs = np.array(xs)
ys = y(xs)

l = linear(xs, ys)
q = quadratic(xs, ys)

#
# for i in range(11):
#     x = xs[i]
#     y = ys[i]
#     print(
#         f"{i+1} & {x:.1f} & {y:.4f} & {phi[i]:.4f} & {err[i]:.4f} \\\\"
#     )

x_space = np.linspace(-5, 1, 1000)

plt.plot(x_space, y(x_space), label=r"y(x)")
plt.scatter(xs, ys)
plt.plot(x_space, l(x_space), label="Линейная аппроксимация")
plt.plot(x_space, q(x_space), label="Квадратичная аппроксимация")
plt.grid()
plt.legend()
plt.savefig('../report/img/chart.pdf')
