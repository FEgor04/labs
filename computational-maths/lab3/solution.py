from functools import lru_cache
import numpy as np


def f(x):
    return x


def print_sum(xs: list[float]):
    for x in xs:
        print(f"{x:.2f}", end=" + ")
    print("")


functions = [
    (lambda x: x, "x"),
    (lambda x: 3 * x**3 - 4 * x**2 + 5 * x - 16, "3 x^3 - 4 x^2 + 5 x - 16"),
    (lambda x: np.sin(x) * x**2, "sin(x) * x^2"),
]

RECTANGLES_LEFT = -1
RECTANGLES_MID = 0
RECTANGLES_RIGHT = 1


@lru_cache
def rectangles_method(f, a, b, h, t):
    # h = (b-a)/ n => 1/n = h / (b-a) => n = (b-a)/h
    n = int(np.ceil((b - a) / h))
    if t == RECTANGLES_LEFT:
        xs = [a + i * h for i in range(n)]
    if t == RECTANGLES_MID:
        xs = [a + h / 2 + i * h for i in range(n)]
    if t == RECTANGLES_RIGHT:
        xs = [a + h + i * h for i in range(n)]
    xs = np.array(list(filter(lambda x: a <= x <= b, xs)))
    ys = f(xs)
    s = (ys * h).sum()
    return s


@lru_cache
def trapeze_method(f, a, b, h):
    n = int((b - a) / h)
    result = 0.5 * (f(a) + f(b))
    for i in range(1, n):
        x = a + i * h
        result += f(x)
    result *= h
    return result


@lru_cache
def simpson_method(f, a, b, h):
    n = int((b - a) / (2 * h))
    result = f(a) + f(b)
    for i in range(1, 2 * n):
        x = a + i * h
        result += 4 * f(x) if i % 2 != 0 else 2 * f(x)
    result *= h / 3

    return result

methods = [
    (
        lambda f, a, b, h: rectangles_method(f, a, b, h, -1),
        "Метод левых прямоугольников",
        1,
    ),
    (
        lambda f, a, b, h: rectangles_method(f, a, b, h, 0),
        "Метод средних прямоугольников",
        2,
    ),
    (
        lambda f, a, b, h: rectangles_method(f, a, b, h, 1),
        "Метод правых прямоугольников",
        1,
    ),
    (trapeze_method, "Метод трапеций", 2),
    (simpson_method, "Метод Симпсона", 4),
]


def compute_integral(method, f, precision, a, b, k):
    method_h = lambda h: method(f, a, b, h)
    n_initial = 4
    h_previous = (b - a) / n_initial
    h = h_previous / 2
    iters = 1
    while iters < 1000:
        current_precision = np.abs((method_h(h) - method_h(h_previous)) / (2**k - 1))
        if current_precision <= precision:
            return (method_h(h), h, iters)
        h_previous = h
        h = h / 2
        iters += 1
    return -1, -1, int(iters)


if __name__ == "__main__":
    print("Выберите функцию:")
    for i in range(len(functions)):
        print(f"{i+1}. {functions[i][1]}")
    function_index = int(input()) - 1
    selected = functions[function_index]
    print(f"Выбрана функция {selected[1]}")

    print("Выберите метод:")
    for i in range(len(methods)):
        print(f"{i+1}. {methods[i][1]}")
    method_index = int(input()) - 1
    selected_method = methods[method_index]
    print(f"Выбран {selected_method[1]}")

    a = float(input("Введите левую границу\n"))
    b = float(input("Введите правую границу\n"))
    precision = float(input("Введите точность\n"))

    if 0 <= method_index <= 2:
        for i in range(0, 3):
            print(methods[i][1])
            answer, h_answer, iters = compute_integral(
                    methods[i][0], selected[0], precision, a, b, selected_method[2]
                )
            print(f"Найденное значние интеграла: {answer:.4f}")
            print(f"Значение найдено при h = {h_answer:.4f} (n = {int((b-a)/h_answer)})")
            print(f"Количество итераций: {iters}")
            print("")
    else:
        answer, h_answer, iters = compute_integral(
            selected_method[0], selected[0], precision, a, b, selected_method[2]
        )
        print(f"Найденное значние интеграла: {answer:.4f}")
        print(f"Значение найдено при h = {h_answer:.4f} (n = {int((b-a)/h_answer)})")
        print(f"Количество итераций: {iters}")
