import numpy as np
import matplotlib.pyplot as plt
from math import ceil
import scipy as sp

data = [
        19.3 , 44.5 , 49.9 , 26.9 , 50.2 , 51.1 , 18.6 , 72.7 , 35.4 , 25.4,
        42.7 , 17.5 , 51.7 , 49.3 , 26.2 , 47.1 , 71.4 , 27.1 , 75.7 , 43.2,
        25.5 , 27.2 , 80.4 , 50.4 , 70.2 , 14.9 , 52.4 , 62.3 , 41.7 , 49.5,
        40.6 , 14.5 , 62.8 , 34.5 , 53.4 , 26.1 , 69.3 , 52.5 , 27.3 , 80.3,
        25.3 , 43.1 , 27.4 , 80.1 , 68.4 , 63.3 , 13.4 , 55.4 , 39.5 , 33.1,
        38.4 , 19.7 , 63.8 , 40.4 , 80.8 , 56.4 , 66.1 , 27.5 , 79.1 , 24.6,
        28.6 , 47.9 , 78.4 , 57.4 , 66.5 , 37.3 , 23.4 , 67.6 , 11.1 , 64.3,
        22.7 , 64.8 , 36.2 , 58.7 , 10.8 , 47.7 , 58.4 , 29.2 , 46.7 , 77.2,
        51.9 , 31.3 , 44.7 , 66.3 , 20.1 , 65.3 , 45.5 , 76.3 , 67.8 , 35.1,
        66.9 , 18.9 , 42.9 , 50.7 , 34.9 , 43.5 , 32.5 , 48.4 , 53.1 , 65.8,
        ]
# ==== Вариационный ряд ====
# var_row = sorted(data)
# for i in range(len(data)):
#     print(var_row[i], end="")
#     if i % 10 == 9:
#         print(" \\\\ \n", end="")
#         print("\\hline")
#     else:
#         print(" & ", end="")
#
# mn = min(data)
# mx = max(data)
# omega = mx - mn
# l = 9
# h = omega / l
# for i in range(l):
#     lo = mn + i * h
#     hi = lo + h
#     mid = (lo + hi) / 2
#     freq = len([i  for i in data if lo <= i <= hi])
#     w = freq / len(data)
#     p = w / h
#     print(f"{i+1} & {lo:.4f} & {hi:.4f} & {mid:.4f} & {freq:} & {w:.4f} & {p:.4f}")
#

def print_variation_series(data):
    print("### Вариационный ряд")
    s = sorted(data)
    for i in s[:-1]:
        print(i, end = " \leq ")
    print(s[-1])

def print_stat_series(data):
    print("### Статистический ряд")
    cnt = Counter(data)
    keys = sorted(list(set(data)))
    for k in keys:
        print(f"{k} & {cnt[k]} & {cnt[k]/len(data)} \\\\")

def print_extreme_values(data):
    print("### Экстремальные значения")
    s = sorted(data)
    print("x_(1) = ", s[0])
    print("x_(n) = ", s[-1])

def print_sampling_range(data):
    s = sorted(data)
    print(f"### Размах выборки: {s[-1] - s[0]:.4f}")

def empirical_distribution_function(data):
    return lambda x: len([y for y in data if y < x])/len(data)

def print_emp_func(data):
    f = empirical_distribution_function(data)
    for x in sorted(list(set(data + [1.32]))) :
        print(f"{x} & {f(x)} \\\\")

def print_emp_func_cases(data):
    s = sorted(list(set(data)))
    f = empirical_distribution_function(data)
    for i in range(1, len(s)) :
        print(f"{f(s[i])}, & {s[i-1]} < x \leq {s[i]} \\\\")

def print_estimate_expected_value(data):
    avg = sum(data) / len(data)
    print(f"Оценка среднего значения: {avg:.4f}")

def compute_dispersia(data):
    n = len(data)
    average = sum(data) / n
    return 1 / n * sum([ (xi - average)**2 for xi in data ])


def print_dispersia(data):
    disp = compute_dispersia(data)
    print(f"Дисперсия выборки: {disp:.4f}")

def print_standard_deviation(data):
    standard_deviation = compute_dispersia(data)**0.5
    print(f"Стандартное отклонение {standard_deviation:.4f}")


def print_most_common_value(data):
    most_common = max(set(data), key=data.count)
    print("Мода выборки:", most_common)

def print_median(data):
    s = sorted(data)
    k = int(len(data) // 2)
    median = (s[k-1] + s[k]) / 2
    print(f"Медиана: {median:.4f}")


def draw_emp_dist_function(data):
    f = empirical_distribution_function(data)
    bins_cnt = 9
    sampling_range = max(data) - min(data)
    dx = sampling_range / bins_cnt
    x_space = [ min(data) + dx * i for i in range(0, bins_cnt + 1) ]
    y_space = [f(x) for x in x_space]
    plt.cla()
    plt.grid()
    plt.plot(x_space, y_space, "-o")
    plt.xticks(x_space)
    plt.xlabel(r"$x$")
    plt.ylabel(r"$F^*(x)$")
    plt.savefig("emp_func.pdf")

def draw_polygon(data):
    bins_cnt = 9
    sampling_range = max(data) - min(data)
    dx = sampling_range / bins_cnt
    bins = [ min(data) + dx * i for i in range(0, bins_cnt + 1) ]
    cnt = [ len(list(filter(lambda x: bins[i-1] <= x <= bins[i], data))) for i in range(1, bins_cnt + 1) ]
    x_space = [ (bins[i] + bins[i+1]) / 2 for i in range(0, len(bins) - 1) ]

    plt.cla()
    plt.xticks(x_space, rotation=30)
    plt.grid()
    plt.xlabel(r"$x$")
    plt.ylabel(r"$n_i$")
    plt.plot(x_space, cnt, '-o')
    plt.savefig("polygon.pdf")


def draw_hist(data):
    bins_cnt = 9
    sampling_range = max(data) - min(data)
    h = sampling_range / bins_cnt
    print(h)
    bins = [ min(data) + h * i for i in range(0, bins_cnt + 1) ]
    cnt = [ len(list(filter(lambda x: bins[i-1] <= x <= bins[i], data))) / h / len(data) for i in range(1, bins_cnt + 1) ]

    plt.cla()
    plt.xticks(bins, rotation=30)
    plt.grid()
    plt.xlabel(r"$x$")
    plt.ylabel(r"$\frac{W_i}{h}$")
    plt.hist(bins[:-1], bins, weights=cnt)
    plt.savefig("hist.pdf")

def compute_fixed_dispersia(data):
    n = len(data)
    dispersia = compute_dispersia(data)
    fd = n / (n-1) * dispersia;
    return fd

def phi(x):
    g

print_dispersia(data)
fd = compute_fixed_dispersia(data)
print(f"Исправленная дисперсия: {fd}")
fstd = (fd)**0.5
print(f"Исправленное стандартное отклонение: {fstd}")
avg = sum(data)/len(data)
print(f"Среднее значение: {avg:.4f}")
draw_emp_dist_function(data)
draw_polygon(data)
draw_hist(data)

mn = min(data)
mx = max(data)
omega = mx - mn
l = 9
h = omega / l
for i in range(l):
    lo = mn + i * h
    hi = lo + h
    mid = (lo + hi) / 2
    sigma = compute_dispersia(data)**0.5
    bar_x = sum(data) / len(data)
    a_lo = lo - bar_x
    a_hi = hi - bar_x
    z_lo = a_lo / sigma
    z_hi = a_hi / sigma
    phi_lo = sp.stats.laplace(z_lo)
    # print(f"{i+1} & {lo:.4f} & {hi:.4f} & {a_lo:.4f} & {a_hi:.4f} & {z_lo:.4f} & {z_hi:.4f} \\\\\n\\hline")
    print(f"{i+1} & {z_lo:.4f} & {z_hi:.4f} \\\\")

