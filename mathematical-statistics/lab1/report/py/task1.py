N = 1000

import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats

def generate_series(n):
    return np.random.rand(n)

def generate(n):
    return [generate_series(n) for i in range(n)]

def find_kth(arr, k):
    arr_sorted = sorted(arr)
    return arr_sorted[k - 1]

def plt_reset():
    plt.clf()
    plt.grid()


data = generate(N)
second_order_statistic = np.array([find_kth(arr, 2) for arr in data])

plt.grid()

plt.hist(second_order_statistic * N, label=r"$n F(X_2)$")
x = np.linspace(0, 15, 10000)
y = N * stats.gamma.pdf(x, a=2, scale=1)
plt.plot(x,y, label="$n \Gamma(2,1)$")
plt.legend()
plt.savefig("figures/1_1.pdf")
plt_reset()

last_order_statistic = np.array([find_kth(arr, N) for arr in data])
plt.hist(N * (1 - last_order_statistic), label=r"$n(1 - F(X_{(n)})$")
x = np.linspace(0, 15, 10000)
y = N * stats.expon.pdf(x, scale = 1)
plt.plot(x,y, label="$n \cdot Exp(1)$")
plt.legend()
plt.savefig("figures/1_2.pdf")
plt_reset()


moments = [stats.describe(arr) for arr in data]
plt.hist([m.mean for m in moments])
plt.savefig("figures/1_mean.pdf")
plt_reset()

plt.hist([m.variance for m in moments])
plt.savefig("figures/1_variance.pdf")
plt_reset()

plt.hist([stats.moment(arr, moment=3) for arr in data])
plt.savefig("figures/1_moments_3.pdf")
plt_reset()

plt.hist([stats.moment(arr, moment=4) for arr in data])
plt.savefig("figures/1_moments_4.pdf")
plt_reset()

plt.hist(np.array([np.median(arr) for arr in data]))
plt.savefig("figures/1_median.pdf")
plt_reset()
