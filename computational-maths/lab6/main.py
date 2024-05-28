from prettytable import PrettyTable
import numpy as np
import matplotlib.pyplot as plt

functions = [
    # Name            , y'(x, y) = f(x,y) , constants                                     , precise solution
    ("f(x, y) = y+(1+x)y^2", lambda x, y: y + (1+x)*y**2, [lambda x0, y0: - (np.exp(x0) * (x0 * y0 + 1)) / y0], lambda x, constants: - np.exp(x) / (constants[0] + np.exp(x) * x) ),
    ("f(x, y) = x - y"     , lambda x, y: x - y         , [lambda x0, y0: np.exp(x0) * (- x0 + y0 + 1)       ], lambda x, c: c[0] * np.exp(-x) + x - 1),
    ("f(x, y) = sin(x) y"  , lambda x, y: np.sin(x) * y , [lambda x0, y0: y0 * np.exp(np.cos(x0))            ], lambda x, c: c[0] * np.exp(-np.cos(x)))
]

def filter_xs(xs, ys, h):
    h_actual = xs[1] - xs[0]
    di = int(h / h_actual)
    return np.array([ xs[i] for i in range(0, len(xs), di) ]), np.array([ ys[i] for i in range(0, len(ys), di) ])

def float_eq(a, b):
    return np.abs(a - b) <= 1e-9

# Метод Эйлера
def euler_method(f, x0, y0, h, n):
    x = np.zeros(n+1)
    y = np.zeros(n+1)
    x[0], y[0] = x0, y0
    for i in range(1, n+1):
        y[i] = y[i-1] + h * f(x[i-1], y[i-1])
        x[i] = x[i-1] + h
    return x, y

# Метод Рунге-Кутта 4-го порядка
def runge_kutta_4(f, x0, y0, h, n):
    x = np.zeros(n+1)
    y = np.zeros(n+1)
    x[0], y[0] = x0, y0
    for i in range(1, n+1):
        k1 = h * f(x[i-1], y[i-1])
        k2 = h * f(x[i-1] + h/2, y[i-1] + k1/2)
        k3 = h * f(x[i-1] + h/2, y[i-1] + k2/2)
        k4 = h * f(x[i-1] + h, y[i-1] + k3)
        y[i] = y[i-1] + (k1 + 2*k2 + 2*k3 + k4) / 6
        x[i] = x[i-1] + h
    return x, y

def compute_one_step_method(method, f, x0, y0, h_initial, n, eps, p):
    x, y = method(f, x0, y0, h_initial, n)
    x2, y2 = method(f, x0, y0, h_initial / 2, n * 2)
    if np.abs(y[-1] - y2[-1]) / (2 ** p - 1) <= eps:
        return x, y
    x1, y1 = compute_one_step_method(method, f, x0, y0, h_initial / 2, n * 2, eps, p)
    return filter_xs(x1, y1, h_initial)



# Метод Милна (явный многошаговый метод)
def milne_method(f, x0, y0, h, n, eps, y_precise):
    x = np.zeros(n+1)
    y = np.zeros(n+1)
    x[0], y[0] = x0, y0

    # Используем метод Рунге-Кутта для первых трех шагов
    for i in range(max(3, n)):
        k1 = h * f(x[i], y[i])
        k2 = h * f(x[i] + h/2, y[i] + k1/2)
        k3 = h * f(x[i] + h/2, y[i] + k2/2)
        k4 = h * f(x[i] + h, y[i] + k3)
        y[i+1] = y[i] + (k1 + 2*k2 + 2*k3 + k4) / 6
        x[i+1] = x[i] + h

    for i in range(3, n):
        y_pred = y[i-3] + 4 * h * (2 * f(x[i-2], y[i-2]) - f(x[i-1], y[i-1]) + 2 * f(x[i], y[i])) / 3
        y_corr = y[i-1] + h * (f(x[i-1], y[i-1]) + 4 * f(x[i], y[i]) + f(x[i+1], y_pred)) / 3
        y[i+1] = y_corr
        x[i+1] = x[i] + h

    eps_actual = max(np.abs(y_precise(x) - y))
    if  eps_actual > eps:
        x1, y1 = milne_method(f, x0, y0, h / 2, n * 2, eps, y_precise)
        return filter_xs(x1, y1, h)
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
    return f_index, x0, y0, xn, h, eps

def precise_lambda(index, x0, y0):
    function = functions[index]
    constants = function[2]
    constants_values = [c(x0, y0) for c in constants]
    return lambda x: function[-1](x, constants_values)

def precise_solution(index, x0, y0, h, n):
    x = np.zeros(n+1)
    y = np.zeros(n+1)
    x[0], y[0] = x0, y0

    f = precise_lambda(index, x0, y0)
    for i in range(1, len(x)):
        x[i] = x[i-1] + h
        y[i] = f(x[i])
    return x, y

def main():
    f_index, x0, y0, xn, h, eps = get_initial_conditions()
    print("\n");
    n = int((xn - x0) / h)
    f = functions[f_index-1][1]

    precise = precise_lambda(f_index - 1, x0, y0)
    x_precise, y_precise = precise_solution(f_index - 1, x0, y0, h, n)
    x_euler, y_euler = compute_one_step_method(euler_method, f, x0, y0, h, n, eps, 1)
    x_rk4, y_rk4 = compute_one_step_method(runge_kutta_4, f, x0, y0, h, n, eps, 4)
    x_milne, y_milne = milne_method(f, x0, y0, h, n, eps, precise)

    print(x_euler)
    print(x_rk4)
    assert( np.all(x_euler - x_rk4 <= 1e-9) )
    assert( np.all(x_euler - x_precise <= 1e-9) )
    assert( np.all(x_euler - x_milne <= 1e-9) )

    plt.plot(x_euler, y_euler, label="Euler Method")
    plt.plot(x_rk4, y_rk4, label="Runge-Kutta 4 Method")
    plt.plot(x_milne, y_milne, label="Milne Method")
    plt.plot(x_precise, y_precise, label="Точное решение")
    plt.xlabel("x")
    plt.ylabel("y")
    plt.legend()
    plt.title("Численное решение ОДУ")

    table = PrettyTable()
    table.field_names = ["X", "Euler", "RK4", "Milne", "Precise", "Euler Error", "RK4 Error", "Milne Error"]
    for i in range(len(x_precise)):
        table.add_row([ x_precise[i], y_euler[i], y_rk4[i], y_milne[i], y_precise[i], np.abs(y_euler[i] - y_precise[i]), np.abs(y_rk4[i] - y_precise[i]), np.abs(y_milne[i] - y_precise[i])])
    table.align = "r"
    table.float_format = ".4"
    print(table)
    euler_std = (precise(x_euler) - y_euler).std()
    rk4_std = (precise(x_rk4) - y_rk4).std()
    milne_std = (precise(x_milne) - y_milne).std()
    print(f"Среднеквадратичное отклонение для метода Эйлера: {euler_std:.4f}")
    print(f"Среднеквадратичное отклонение для метода РК4: {rk4_std:.4f}")
    print(f"Среднеквадратичное отклонение для метода Милне: {milne_std:.4f}")
    plt.show()

if __name__ == "__main__":
    main()

