import matplotlib.pyplot as plt
import numpy as np
from numpy import sin, cos


# f(x), name, f'(x)
equations = [
        (lambda x: x ** 3 + 3 * x ** 2 + 3 * x + 1, "x^3 + 3x^2 + 3x + 1", lambda x: 3 * x**2 + 6 * x + 3),
        (lambda x: x**2 + sin(x), "x^2 + sin(x)", lambda x: 2 * x + cos(x)),
        (lambda x: x**3 - x + 4, "x^3 -x + 4", lambda x: 3 * x **2 - 1)
        ]


def method_chord(f, fp, a, b, eps):
    iter_number = 0
    while True:
        iter_number += 1
        x_cur = a - (b-a)/(f(b) - f(a)) * f(a)
        if abs(f(x_cur)) < eps:
            return x_cur, iter_number
        if f(a) * f(x_cur) < 0: # разные знаки
            b = x_cur
        else:
            a = x_cur

def newton_method(f, fp, a, b, eps):
    prev_second_der_sign = 0
    for x in np.linspace(a, b, 1000):
        if fp(x) == 0:
            print("Производная принимает нулевое значение. Метод Ньютона нельзя применять.")
            exit()
        dx = 0.00001
        second_der = (fp(x + dx) - fp(x)) / dx
        second_der_sign = 1 if second_der> 0 else -1
        if second_der_sign * prev_second_der_sign < 0:
            print("Вторая производная меняет знак на отрезке. Метод Ньютона нельзя применять")
            exit()
        prev_second_der_sign = second_der_sign


    x_cur = float(input("Введите начальное приближение: "))
    iter_number = 0
    while True:
        iter_number += 1
        x_prev = x_cur
        x_cur = x_cur - f(x_cur) / fp(x_cur)
        if abs(x_cur - x_prev) < eps:
            return x_cur, iter_number
        if f(a) * f(x_cur) < 0: # разные знаки
            b = x_cur
        else:
            a = x_cur

def iterations_method(f, fp, a, b, eps):
    n = 100
    max_derivative_abs = fp(a)
    for i in range(n):
        val = a + (b - a) / n * i
        derivative = fp(val)
        if abs(derivative) > abs(max_derivative_abs):
            max_derivative_abs = derivative
    print(f"Максимальное значение f' по модулю: {max_derivative_abs}")
    lambda_value = - 1 / max_derivative_abs
    phi = lambda x : x + lambda_value * f(x)
    phi_der = lambda x : 1 + lambda_value * fp(x)
    print(f"Значение phi' на концах отрезка: {phi_der(a)} {phi_der(b)}")
    max_phi_der = max([abs(phi_der(x)) for x in np.linspace(a, b)])
    print(f"Максимальное абсолютное значение phi': {max_phi_der}")
    if max_phi_der > 1:
        print("Значение max phi' > 1. Сходимости не будет!")
    x_cur = a
    iter_number = 0
    while True:
        iter_number += 1
        x_prev = x_cur
        x_cur = phi(x_cur)
        if abs(f(x_cur)) < eps:
            return x_cur, iter_number
        if iter_number > 1000:
            return -1, iter_number

methods = [
        (method_chord, "Метод хорд"),
        (newton_method, "Метод Ньютона"),
        (iterations_method, "Метод простых итераций"),
        ]

print("Уравнения:")
for i in range(len(equations)):
    print(f"{i+1}. {equations[i][1]}")
equation_number = int(input("Введите номер уравнения\n"))
if not (1 <= equation_number <= len(equations)):
    print(f"Введите номер уравнения от 1 до {len(equations)}")
    exit()
equation_number -= 1
print(f"Выбрано уравнение: {equations[equation_number][1]}")

print("Методы")
for i in range(len(methods)):
    print(f"{i+1}. {methods[i][1]}")
method_number = int(input("Выберите метод\n"))
if not (1 <= method_number <= len(methods)):
    print(f"Введите номер метода от 1 до {len(methods)}")
    exit()
method_number -= 1
print(f"Выбран {methods[method_number][1]}")

f = equations[equation_number][0]
fp = equations[equation_number][2]

x = np.linspace(-10, 10, 10000)
plt.plot(x, f(x))
plt.grid()
# plt.show()

a = float(input("Введите левую границу отрезка\n"))
b = float(input("Введите правую границу отрезка\n"))

if f(a) * f(b) > 0:
    print("Корень не изолирован на данном отрезке")
    exit()

eps = float(input("Введите точность\n"))


ans, iter_number = methods[method_number][0](f, fp, a, b, eps)
if iter_number == -1:
    exit()
print(f"Корень: {ans}")
print(f"Значение функции в корне: {f(ans)}")
print(f"Количество итераций: {iter_number}")

