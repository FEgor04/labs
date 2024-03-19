import numpy as np

# Name, f, g, Jacoby matrix
systems = [
        (
            "x^2 + y^2 = 4, y = 3x^2", 
            lambda x, y: x ** 2 + y**2 - 4,
            lambda x, y: y - 3 * x ** 2,
            lambda x, y: np.matrix([[2*x, 2*y], [-6*x, 1]]),
            ),
        (
            "x^2 + cos y = 4, cos(y) + x = 2",
            lambda x, y: x**2 + np.cos(y) - 4,
            lambda x, y: np.cos(y) + x - 2,
            lambda x, y: np.matrix([[2 * x, - np.sin(y)], [1, - np.sin(y)]])
            )
        ]

print("Системы:")
for i in range(len(systems)):
    print(f"{i+1}. {systems[i][0]}")
system_i = int(input("Выберите систему: ")) - 1
if system_i < 0 or system_i >= len(systems):
    print(f"Введите число от 1 до {len(systems)}")
    exit()
system = systems[system_i]


x = float(input("Введите начальное приближение x_0: "))
y = float(input("Введите начальное приближение y_0: "))
eps = float(input("Введите погрешность: "))

iter_num = 0
while True:
    iter_num += 1
    x_prev = x
    y_prev = y
    A = system[3](x, y)
    b = - np.array([system[1](x, y), system[2](x,y)])
    solution = np.linalg.solve(A, b)
    x = x + solution[0]
    y = y + solution[1]
    print(x, y)
    if abs(x - x_prev) < eps and abs(y - y_prev) < eps:
        print(f"Вектор погрешностей: ({abs(x - x_prev)}, {abs(y - y_prev)})")
        break
    if iter_num > 1000:
        print("Слишком много итераций!")
        exit()
        break

print(f"Решение системы: ({x}, {y})")
f_val = system[1](x, y)
g_val = system[2](x, y)
print(f"Значения функций в решении: f = {f_val}, g = {g_val}")
print(f"Количество итераций", iter_num)
