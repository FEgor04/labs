import matplotlib.pyplot as plt
import numpy as np

c = np.array([
    0.000000001,
    0.000000003,
    0.00000001,
    0.00000003,
    0.0000001,
    0.0000003
    ])

f = np.array([
    490117,
    295810,
    15900,
    9103,
    4780,
    2450
    ])

A = np.vstack([1/c, np.ones(len(c))]).T
k, b = np.linalg.lstsq(A, f**2, rcond=None)[0]

plt.plot(1/c, f**2, 'o-', label="Эксп. данные")
plt.xlabel(r"$\frac{1}{C}$, $\frac{1}{Ф}$")
plt.ylabel(r"$\Omega^2_{рез.}$, Гц")

plt.plot(1/c, k * (1/c) + b, label="МНК")

plt.grid()
plt.legend()
plt.savefig("./figures/res_c_plot.pdf")

L = 1 / k
print(f"1/L = {k}, L = {L}")
print(f"- R^2 / (4 L^2) = {b}")
R = (- 4 * L**2 * b)**0.5
print(f"R = {R}")
