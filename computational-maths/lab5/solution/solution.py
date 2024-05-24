from sys import stdin
import numpy as np
import numpy.typing as npt
import matplotlib.pyplot as plt

functions = [
    ("sin(x)", lambda x: np.sin(x)),
    ("x^2", lambda x: x**2),
    ("ln(x)", lambda x: np.log(x)),
]

dx = 0.1
chosen_function = None

def print_arr(x):
    for i in x:
        print(f"{i:.4f}", end=" ")
    print("")

def input_xs_ys():
    global chosen_function;
    mode = int(input("Введите 1 чтобы ввести значения самостоятельно или 2 чтобы выбрать одну из предложенных\n"))
    if mode == 1:
        n = int(input("Введите количество точек\n"))
        xs = []
        ys = []
        for _ in range(n):
            x, y = map(float, input().split())
            xs += [x]
            ys += [y]
        xs = np.array(xs)
        ys = np.array(ys)
        return xs, ys
    else:
        for i in range(len(functions)):
            print(f"{i+1}. {functions[i][0]}")
        n = int(input("Введите номер функции\n"))
        function = functions[n - 1]
        chosen_function = functions[n-1]
        print("Выбрана функция: ", function[0])
        print("Введите левую границу отрезка")
        l = float(input())
        print("Введите правую границу отрезка")
        r = float(input())
        print("Введите количество точек")
        dots = int(input())
        xs = np.linspace(l, r, dots)
        ys = [function[1](x) for x in xs]
        print("Сгенерированные значения X, Y:")
        print("X:",end=" ")
        print_arr(xs)
        print("Y:",end=" ")
        print_arr(ys)
        x_space = np.linspace(l - dx, r + dx, 1000)
        plt.plot(x_space, function[1](x_space), label=function[0])
        return xs, ys
    
def lagrange(xs, ys):
    n = len(xs)
    def f(x):
        ans = 0
        for i in range(n):
            prod = 1
            for j in range(n):
                if i == j:
                    continue
                prod *= (x - xs[j])/(xs[i]-xs[j])
            ans += ys[i] * prod
        return ans
    return f

def newton(xs, ys):
    def divided_differences(x, y):
        """
        рекурсивный подсчет разделенных разностей
        """
        if len(y) == 1:
            return y[0]
        else:
            return (divided_differences(x[1:], y[1:]) - divided_differences(x[:-1], y[:-1])) / (x[-1] - x[0])

    def interpolating_polynomial(x):
        result = 0
        for i in range(len(xs)):
            term = divided_differences(xs[:i+1], ys[:i+1])
            for j in range(i):
                term *= (x - xs[j])
            result += term
        return result

    return interpolating_polynomial

def nodes_are_equal(xs):
    for i in range(1, len(xs) - 1):
        if (xs[i] - xs[i-1]) - (xs[i+1] - xs[i]) >= 1e-9:
            return False
    return True

def newton_finite(xs, ys):
    def delta(i, k):
        if(k == 0):
            return ys[i]
        return delta(i + 1, k - 1) - delta(i, k - 1)
    assert(nodes_are_equal(xs))
    n = len(xs)

    def find_base_i(x):
        for i in range(n - 1):
            if xs[i] <= x <= xs[i+1]:
                return i
        if x < xs[0]:
            return 0
        return n - 1

    h = xs[1] - xs[0]

    def is_in_first_half(x):
        mid = (xs[0] + xs[-1])
        return x < mid

    print("Значения Delta(i, j)")
    for i in range(0, n):
        for j in range(0, n - i):
            d = delta(i, j)
            print(f"{d:+04.4f}", end =" \t")
        print(" ")

    def f(x):
        if is_in_first_half(x):
            t = (x - xs[0]) / h
            sum = ys[0]
            prod = 1
            for j in range(1, n):
                prod *= (t - j + 1) / j
                sum += prod * delta(0, j)
            return sum
        else:
            t = (x - xs[-1]) / h
            sum = ys[-1]
            prod = 1
            for j in range(1, n):
                prod *= (t + j - 1) / j
                sum += prod * delta(n - j - 1, j)
            return sum
    return f

if __name__ == "__main__":
    guesses = []
    xs, ys = input_xs_ys()
    x = float(input("Введите интересуемую точку: \n"))
    lagrange = lagrange(xs,ys)
    newton = newton(xs,ys)
    x_space = np.linspace(min(xs) - dx, max(xs) + dx, 1000)
    plt.plot(x_space, lagrange(x_space), label="Метод Лагранжа")
    guesses += [("Метод Лагранжа", lagrange(x))]
    if nodes_are_equal(xs):
        f = newton_finite(xs, ys)
        plt.plot(x_space, [f(x) for x in x_space], label="Метод Ньютона (конечные разности)")
        guesses += [("Метод Ньютона (конечные разности)", f(x))]
    else:
        plt.plot(x_space, newton(x_space), label="Метод Ньютона (разделенные разности)")
        guesses += [("Метод Ньютона", newton(x))]
    plt.scatter(xs, ys)
    plt.legend()
    plt.show()

    actual_value = None
    if chosen_function:
        actual_value = chosen_function[1](x)
        print(f"Значение функции {chosen_function[0]} в точке {x:.4f}: {actual_value:.4f}")

    print("\nПолученные значения:")
    for (name, value) in guesses:
        actual_value_str = ""
        if actual_value:
            actual_value_str = f"Отклонение: {np.abs(value - actual_value):.4f} ({np.abs((value - actual_value)/actual_value * 100):.2f}%)"
            
        print(f"{name}: {value:.4f} {actual_value_str}")
 
