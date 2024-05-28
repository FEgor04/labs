from prettytable import PrettyTable
import numpy as np
import matplotlib.pyplot as plt
from utils import *

functions = [
    # Name            , y'(x, y) = f(x,y) , constants                                     , precise solution
    (
        "f(x, y) = y+(1+x)y^2",
        lambda x, y: y + (1 + x) * y**2,
        [lambda x0, y0: -(np.exp(x0) * (x0 * y0 + 1)) / y0],
        lambda x, constants: -np.exp(x) / (constants[0] + np.exp(x) * x),
    ),
    (
        "f(x, y) = x - y",
        lambda x, y: x - y,
        [lambda x0, y0: np.exp(x0) * (-x0 + y0 + 1)],
        lambda x, c: c[0] * np.exp(-x) + x - 1,
    ),
    (
        "f(x, y) = sin(x) y",
        lambda x, y: np.sin(x) * y,
        [lambda x0, y0: y0 * np.exp(np.cos(x0))],
        lambda x, c: c[0] * np.exp(-np.cos(x)),
    ),
]


def filter_xs(xs, ys, h, max_x=None):
    h_actual = xs[1] - xs[0]
    di = int(h / h_actual)
    return np.array(
        [xs[i] for i in range(0, len(xs), di) if max_x is None or xs[i] <= max_x]
    ), np.array(
        [ys[i] for i in range(0, len(ys), di) if max_x is None or xs[i] <= max_x]
    )


def float_eq(a, b):
    return np.abs(a - b) <= 1e-9


# Метод Эйлера
def euler_method(f, x0, y0, h, n):
    x = np.zeros(n + 1)
    y = np.zeros(n + 1)
    x[0], y[0] = x0, y0
    for i in range(1, n + 1):
        y[i] = y[i - 1] + h * f(x[i - 1], y[i - 1])
        x[i] = x[i - 1] + h
    return x, y


# Метод Рунге-Кутта 4-го порядка
def runge_kutta_4(f, x0, y0, h, n):
    x = np.zeros(n + 1)
    y = np.zeros(n + 1)
    x[0], y[0] = x0, y0
    for i in range(1, n + 1):
        k1 = h * f(x[i - 1], y[i - 1])
        k2 = h * f(x[i - 1] + h / 2, y[i - 1] + k1 / 2)
        k3 = h * f(x[i - 1] + h / 2, y[i - 1] + k2 / 2)
        k4 = h * f(x[i - 1] + h, y[i - 1] + k3)
        y[i] = y[i - 1] + (k1 + 2 * k2 + 2 * k3 + k4) / 6
        x[i] = x[i - 1] + h
    return x, y


def compute_one_step_method(method, f, x0, y0, h_initial, n, eps, p):
    x, y = method(f, x0, y0, h_initial, n)
    xn = x0 + (n) * h_initial
    h2 = h_initial / 2
    n2 = compute_n_for_half_h(n)
    x2, y2 = method(f, x0, y0, h_initial / 2, n2)
    if np.abs(y[-1] - y2[-1]) / (2**p - 1) <= eps:
        return x, y
    x1, y1 = compute_one_step_method(method, f, x0, y0, h2, n2, eps, p)
    return filter_xs(x1, y1, h_initial, x[-1])


# Метод Милна 
def milne_method(f, x0, y0, h, n, eps, y_precise):
    x, y = runge_kutta_4(f, x0, y0, h, n)
    h2 = h / 2
    n2 = compute_n_for_half_h(n)

    for i in range(3, n):
        # Этап предсказания
        y_predict = y[i-3] + 4 * h / 3 * (2 * f(x[i-2], y[i-2]) - f(x[i-1], y[i-1]) + 2 * f(x[i], y[i]))
        x_next = x[i] + h
        
        # Этап коррекции
        y[i + 1] = y[i-1] + h / 3 * (f(x[i-1], y[i-1]) + 4 * f(x[i], y[i]) + f(x_next, y_predict))
        x[i + 1] = x_next

    eps_actual = max(np.abs(y_precise(x) - y))
    if eps_actual > eps:
        x1, y1 = milne_method(f, x0, y0, h2, n2, eps, y_precise)
        return filter_xs(x1, y1, h, x[-1])
    return np.array(x), np.array(y)


# Функция для ввода начальных условий
def get_initial_conditions():
    print("Выберите функцию y' = f(x,y) из предложенных:")
    for i in range(len(functions)):
        print(f"{i+1}. {functions[i][0]}")
    f_index = int(input())
    x0 = float(input("Введите начальное значение x0: "))
    y0 = float(input("Введите начальное значение y0: "))
    xn = float(input("Введите конечное значение xn: "))
    h = float(input("Введите шаг h: "))
    eps = float(input("Введите погрешность eps: "))

    if not is_divisable(x0, xn, h):
        new_h = (xn - x0) / 10
        print(
            f"Отрезок [x0, xn] не делится на целочисленное количетсво отрезков длины h. В качестве h будет взято {new_h:.4f}"
        )
        h = new_h

    return f_index, x0, y0, xn, h, eps


def precise_lambda(index, x0, y0):
    function = functions[index]
    constants = function[2]
    constants_values = [c(x0, y0) for c in constants]
    return lambda x: function[-1](x, constants_values)


def precise_solution(index, x0, y0, h, n):
    x = np.zeros(n + 1)
    y = np.zeros(n + 1)
    x[0], y[0] = x0, y0

    f = precise_lambda(index, x0, y0)
    for i in range(1, len(x)):
        x[i] = x[i - 1] + h
        y[i] = f(x[i])
    return x, y


def main():
    f_index, x0, y0, xn, h, eps = get_initial_conditions()
    print("\n")
    n = int(np.abs(xn - x0) / h)
    f = functions[f_index - 1][1]

    precise = precise_lambda(f_index - 1, x0, y0)
    x_precise, y_precise = precise_solution(f_index - 1, x0, y0, h, n)
    x_euler, y_euler = compute_one_step_method(euler_method, f, x0, y0, h, n, eps, 1)
    x_rk4, y_rk4 = compute_one_step_method(runge_kutta_4, f, x0, y0, h, n, eps, 4)
    x_milne, y_milne = milne_method(f, x0, y0, h, n, eps, precise)

    precise_space = np.linspace(min(x_euler), max(x_euler), 1000)
    plt.scatter(x_precise, y_precise, label="Точное решение")
    plt.plot(precise_space, precise(precise_space), label="Точное решение")
    plt.plot(x_euler, y_euler, label="Метод Эйлера")
    plt.plot(x_rk4, y_rk4, label="Метод Рунге-Кутты")
    plt.plot(x_milne, y_milne, label="Метод Милна")
    plt.xlabel("x")
    plt.ylabel("y")
    plt.legend()

    table = PrettyTable()
    table.field_names = [
        "X",
        "Euler",
        "RK4",
        "Milne",
        "Precise",
        "Euler Error",
        "RK4 Error",
        "Milne Error",
    ]
    for i in range(len(x_precise)):
        table.add_row(
            [
                x_precise[i],
                y_euler[i],
                y_rk4[i],
                y_milne[i],
                y_precise[i],
                np.abs(y_euler[i] - y_precise[i]),
                np.abs(y_rk4[i] - y_precise[i]),
                np.abs(y_milne[i] - y_precise[i]),
            ]
        )
    table.align = "r"
    table.float_format = ".4"
    print(table)
    euler_std = (precise(x_euler) - y_euler).std()
    rk4_std = (precise(x_rk4) - y_rk4).std()
    milne_std = (precise(x_milne) - y_milne).std()
    print(f"Среднеквадратичное отклонение для метода Эйлера:\t{euler_std:.8f}")
    print(f"Среднеквадратичное отклонение для метода РК4:\t\t{rk4_std:.8f}")
    print(f"Среднеквадратичное отклонение для метода Милне:\t\t{milne_std:.8f}")
    plt.show()


if __name__ == "__main__":
    main()
