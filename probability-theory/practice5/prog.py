from collections import Counter
import matplotlib.pyplot as plt
from math import log, ceil
import numpy as np

# Var 19
data = [
            0.34,  -1.38,
            -1.14,   0.8,
            0.73,   0.38,
            1.31,   0.52,
            -1.55, -0.90,

            0.90,  -1.00,
            0.24,   0.62,
            0.55,  -1.45,
            -1.45,  0.52,
            0.17,  -1.31,
        ]
assert(len(data) == 20)


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
    x_space = np.linspace(-2.5, 2.5, 1000)
    y_space = [f(x) for x in x_space]
    plt.cla()
    plt.grid()
    plt.plot(x_space, y_space)
    plt.xlabel(r"$x$")
    plt.ylabel(r"$F_{20}^{*}(x)$")
    plt.savefig("emp_func.pdf")

def draw_polygon(data):
    bins_cnt = ceil(1 + log(len(data))/log(2))
    sampling_range = max(data) - min(data)
    dx = sampling_range / bins_cnt
    bins = [ min(data) + dx * i for i in range(0, bins_cnt + 1) ]
    cnt = [ len(list(filter(lambda x: bins[i-1] <= x <= bins[i], data))) / dx for i in range(1, bins_cnt + 1) ]
    x_space = [ (bins[i] + bins[i+1]) / 2 for i in range(0, len(bins) - 1) ]

    plt.cla()
    plt.xticks(bins, rotation=30)
    plt.grid()
    plt.xlabel(r"$x$")
    plt.ylabel(r"$\frac{n_i}{h}$")
    plt.plot(x_space, cnt)
    plt.savefig("polygon.pdf")


def draw_hist(data):
    bins_cnt = ceil(1 + log(len(data))/log(2))
    sampling_range = max(data) - min(data)
    h = sampling_range / bins_cnt
    bins = [ min(data) + h * i for i in range(0, bins_cnt + 1) ]
    cnt = [ len(list(filter(lambda x: bins[i-1] <= x <= bins[i], data))) / h for i in range(1, bins_cnt + 1) ]

    plt.cla()
    plt.xticks(bins, rotation=30)
    plt.grid()
    plt.xlabel(r"$x$")
    plt.ylabel(r"$\frac{n_i}{h}$")
    plt.hist(bins[:-1], bins, weights=cnt)
    plt.savefig("hist.pdf")

def compute_fixed_dispersia(data):
    n = len(data)
    dispersia = compute_dispersia(data)
    fd = n / (n-1) * dispersia;
    return fd


print_variation_series(data)
print("")
print_stat_series(data)
print("")
print_extreme_values(data)
print("")
print_sampling_range(data)
print("")
print_estimate_expected_value(data)
print("")
print_dispersia(data)
print("")
print_standard_deviation(data)
print("")
print_most_common_value(data)
print("")
print_median(data)
print("")
print_emp_func(data)
print("")
fd = compute_fixed_dispersia(data)
print(f"Исправленная дисперсия: {fd}")
fstd = (fd)**0.5
print(f"Исправленное стандартное отклонение: {fstd}")
print_emp_func_cases(data)
draw_emp_dist_function(data)
draw_polygon(data)
draw_hist(data)
