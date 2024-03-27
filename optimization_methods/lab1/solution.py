import numpy as np
from numpy import log as ln

def f(x):
    return x**2 - 3 * x + x * ln(x)
def f_der(x):
    return 2 * x + ln(x) - 2
def f_second_der(x):
    return 1 / x + 2

eps = 10**(-10)

print("a = 1, b = 2, epsilon = 10^(-10)\n")
print_iters = True

def half_del_method(f, f_der, a, b, eps, iter_count):
    x1 = (a + b - eps) / 2
    x2 = (a + b + eps) / 2
    y1 = f(x1)
    y2 = f(x2)
    if print_iters:
        print(f"{iter_count:2d} & {a:.11f} & {b:.11f}")
    if abs(b - a) < eps or iter_count >= 25:
        return (a + b) / 2
    if y1 >= y2:
        return half_del_method(f, f_der, x1, b, eps, iter_count + 1)
    return half_del_method(f, f_der, a, x2, eps, iter_count + 1)


if print_iters:
    print(f"No & a & b & x1 & x2 & f(a) & f(b) & f(x1) & f(x2)")
ans_half = half_del_method(f, f_der, 1, 2, eps, 1)
print(f"Answer from half division method: {ans_half:.11f}")
print(f"f(x_min) = {f(ans_half):.11f}")

phi = (1 + 5 ** 0.5) / 2
def golder_ratio_method(f, f_der, a, b, y1, y2, eps, iter_count):
    x1 = b - (b - a) / phi
    x2 = a + (b - a) / phi
    if y1 is None:
        y1 = f(x1)
    if y2 is None:
        y2 = f(x2)
    if print_iters:
        print(f"{iter_count:2d} & {a:.11f} & {b:.11f} & {x1:.11f} & {x2:.11f} & {f(a):.11f} & {f(b):.11f} & {f(x1):.11f} & {f(x2):.11f} \\\\")
    if abs(b - a) < eps or iter_count >= 25:
        return (a + b) / 2
    if y1 >= y2:
        return golder_ratio_method(f, f_der, x1, b, y2, None, eps, iter_count + 1)
    return golder_ratio_method(f, f_der, a, x2, None, y1, eps, iter_count + 1)

def newton_method(f, f_der, x, eps, iter_count):
    if print_iters:
        print(f"{iter_count:2d} & {x:.11f}  & {f_der(x):.4E} ")
    if abs(f_der(x)) <= eps or iter_count >= 25:
        return x
    return newton_method(f, f_der, x - f_der(x) / f_second_der(x), eps, iter_count + 1)

print("\n")
if print_iters:
    print(f"No & a & b & x1 & x2 & f(a) & f(b) & f(x1) & f(x2)")
ans_golden = golder_ratio_method(f, f_der, 1, 2, None, None, eps, 1)
print(f"Answer from golden ratio method: {ans_golden:.11f}")
print(f"f(x_min) = {f(ans_golden):.11f}")


print("\n")
ans_newton = newton_method(f, f_der, 1.5, eps, 1)
print(f"Answer from Newton method: {ans_newton:.11f}")
print(f"f(x_min) = {f(ans_newton):.11f}")
print(f"f'(x_min) = {f_der(ans_newton):.11f}")
