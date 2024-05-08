import numpy as np
import matplotlib.pyplot as plt
from scipy import interpolate

u = np.array([
    1.23,
    2.47,
    3.49,
    4.32,
    5.07,
    5.67,
    6.16,
    6.56,
    6.87,
    7.14,
    7.4,
    7.62,
    7.87,
    8.1,
    8.11,
])

i = np.array([
    15.47,
    13.52,
    12.13,
    10.88,
    9.78,
    8.88,
    8.13,
    7.55,
    7.09,
    6.67,
    6.3,
    5.95,
    5.58,
    5.275,
    5.24
])

plt.plot(i, u, label=r"$U(I)$")
plt.xlabel(r"$I$, мА")
plt.ylabel(r"$U$, В")
plt.grid()

A = np.vstack([i, np.ones(len(i))]).T
(minus_r, eps), residuals, _, _ = np.linalg.lstsq(A, u, rcond=None)
r = - minus_r

mse = residuals / (len(u) - 2)
cov_matrix = mse * np.linalg.inv(np.dot(A.T, A))
std_errors = np.sqrt(np.diag(cov_matrix))

eps_error = std_errors[1]
r_error = std_errors[0]

plt.plot(i, eps - r * i, '--', label="МНК")

plt.legend()
plt.savefig("./figures/u_i.pdf")

# ЭДС
print(f"Epsilon = {eps:.4f} +- {eps_error:.4f} ({(eps_error / eps * 100):.2f}%)")
# Внутреннее сопротивление источника
print(f"R = {r:.4f} +- {r_error:.4f} ({(r_error / eps * 100):.2f}%)")

p_r = u * i 
p = eps * i
p_s = i**2 * r

def print_arr(a):
    for i in a:
        print(f"{i:.4f}")

plt.cla()
plt.xlabel(r"$I$, мА")
plt.ylabel(r"$P, P_R, P_S$, мВт")
plt.plot(i, p_r, label=r"$P_R$")
plt.plot(i, p_s, label=r"$P_S$")
plt.plot(i, p, label=r"$P$")
plt.legend()
plt.grid()

p_r_max = 0
for ind in range(len(i)):
    if p_r[ind] > p_r[p_r_max]:
        p_r_max = ind


plt.scatter([i[p_r_max]], [p_r[p_r_max]], label=r"$I*$")
plt.savefig("./figures/p_i.pdf")
# Значение I* -- значение, при котором P_R(i) == max
print(f"I* = {i[p_r_max]:.4f}")

R = p_r[p_r_max] / i[p_r_max]**2
# Сопротивление R, соответствующее режиму согласования нагрузки и источника
print(f"R = {R:.4f}")

eta = p_r / p
a, b = np.polyfit(i, eta, 1)
plt.cla()
plt.xlabel(r"$I$, мА")
plt.ylabel(r"$\eta$")
plt.grid()
x = np.linspace(0, 17.5, 1000)
plt.plot(x, a * x + b)

# Значение I*, соответствующее eta = 0.5
i_half = (0.5 - b) / a
print(f"I_half = {i_half:.4f}")
plt.scatter([i_half], [0.5])
plt.savefig("./figures/eta_i.pdf")
