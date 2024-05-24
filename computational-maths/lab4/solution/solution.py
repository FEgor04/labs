from sys import stdin
import numpy as np
import numpy.typing as npt
import matplotlib.pyplot as plt
def linear_koef(xs, ys):
    assert len(ys) == len(xs)
    n = len(xs)
    
    sx = xs.sum()
    sxx = (xs**2).sum()
    sy = ys.sum()
    sxy = (xs * ys).sum()

    d = sxx * n - sx * sx
    d1 = sxy * n - sx * sy
    d2 = sxx * sy - sx * sxy

    a = d1 / d
    b = d2 / d
    return a, b


def linear(xs, ys):
    a, b = linear_koef(xs, ys)
    p_coefficient = np.corrcoef(xs, ys)[0,1]
    print(f"Коэффициенты линейной функции: {a:.4f} x + {b:.4f}")
    print(f"Коэффициент Пирсона для линейной аппроксимции: {p_coefficient:.4f}")
    return lambda x: a * x + b

def quadratic(xs: npt.NDArray[np.float64], ys: npt.NDArray[np.float64]):
    assert len(ys) == len(xs)
    n = len(xs)
    
    sx = xs.sum()
    sxx = (xs**2).sum()
    sxxx = (xs**3).sum()
    sxxxx = (xs**4).sum()
    sy = ys.sum()
    sxy = (xs * ys).sum()
    sxxy = (xs**2 * ys).sum()

    A = np.array([[n, sx, sxx], [sx, sxx, sxxx], [sxx, sxxx, sxxxx]])
    B = np.array([sy, sxy, sxxy])
    answ = np.linalg.solve(A, B)
    a0, a1, a2 = answ
    print(f"Коэффициенты для полинома второй степени: {a0:.4f} + {a1:.4f} x + {a2:.4f} x^2")
    return lambda x: a0 + a1 * x + a2 * x**2

def polynom_third(xs, ys):
    assert len(ys) == len(xs)
    n = len(xs)
    
    sx = xs.sum()
    sxx = (xs**2).sum()
    sxxx = (xs**3).sum()
    sxxxx = (xs**4).sum()
    sxxxxx = (xs**5).sum()
    sxxxxxx = (xs**6).sum()
    sy = ys.sum()
    sxy = (xs * ys).sum()
    sxxy = (xs**2 * ys).sum()
    sxxxy = (xs**3 * ys).sum()

    A = np.array([[n, sx, sxx, sxxx], [sx, sxx, sxxx, sxxxx], [sxx, sxxx, sxxxx, sxxxxx], [sxxx, sxxxx, sxxxxx, sxxxxxx]])
    B = np.array([sy, sxy, sxxy, sxxxy])
    answ = np.linalg.solve(A, B)
    a0, a1, a2, a3 = answ
    print(f"Коэффициенты для полинома третьей степени: {a0:.4f} + {a1:.4f} x + {a2:.4f} x^2 + {a3:.4f} x^3")
    return lambda x: a0 + a1 * x + a2 * x**2 + a3 * x **3

def has_zeroes(xs):
    for i in xs:
        if i <= 0:
            return True
    return False

def pow_func(xs, ys):
    for i in range(len(xs)):
        if xs[i] <= 0 or ys[i] <= 0:
            print("Ошибка: Для степенной функции значения X, Y должны быть больше 0.")
            return lambda x: x * 0
    b, a = linear_koef(np.log(xs), np.log(ys))
    print(f"Коэффициенты степенной функции: e^{a:.4f} * x^{b:.4f} = {np.exp(a):.4f} * x^{b:.4f}")
    return lambda x: np.exp(a) * x**b

def exponential(xs, ys):
    for i in range(len(xs)):
        if xs[i] <= 0 or ys[i] <= 0:
            print("Ошибка: Для степенной функции значения X, Y должны быть больше 0.")
            return lambda x: x * 0
    b, a = linear_koef(xs, np.log(ys))
    print(f"Коэффициенты экспоненциальной функции: e^{a:.4f} * e^({b:.4f} x) = {np.exp(a):.4f} * e^({b:.4f} x)")
    return lambda x: np.exp(a) * np.exp(b * x)

def logarithmic(xs, ys):
    for i in range(len(xs)):
        if xs[i] <= 0 or ys[i] <= 0:
            print("Ошибка: Для степенной функции значения X, Y должны быть больше 0.")
            return lambda x: x * 0
    a, b = linear_koef(np.log(xs), ys)
    print(f"Коэффициенты логарифмическйо функции: {a:.4f} ln(x) + {b:.4f}")
    return lambda x: (a) * np.log(x) + b

methods = [
    ("Линейная", linear),
    ("Квадратичная", quadratic),
    ("Полином 3-й степени", polynom_third),
    ("Степенная", pow_func),
    ("Экспоненциальная", exponential),
    ("Логарифмическая", logarithmic),
]

if __name__ == "__main__":
    xs = []
    ys = []
    for line in stdin:
        x, y = map(float, line.split())
        xs += [x]
        ys += [y]
    xs = np.array(xs)
    ys = np.array(ys)
    dx = 1
    x_space = np.linspace(min(xs) - dx, max(xs) + dx, 10000)

    plt.scatter(xs, ys, label="Входные данные")
    best = methods[0]
    best_mse = 1e9
    for i in range(len(methods)):
        if i > 2 and has_zeroes(xs):
            print("Ошибка: в X есть отрицательные значения или 0")
            continue;
        name, method = methods[i]
        approximation = method(xs, ys)
        phi = approximation(xs)
        plt.plot(x_space, approximation(x_space), label=name)
        epsilon = phi - ys
        mse = (epsilon**2).mean()
        print(f"Среднеквадратичное отклонение = {mse**0.5:.4f}")
        r_squared = np.abs(1 - ((ys - phi)**2).sum() / ((ys - phi.mean())**2).sum())
        print(f"Значение R^2 для метода {name}: {r_squared:.4f}\n")
        if mse < best_mse:
            best = (name, method)
            best_mse = mse
    print("\n\n")
    print(f"Самая точная модель по MSE: {best[0]}. Значение MSE: {best_mse:.4f}")
    best_method = best[1](xs, ys)

    plt.legend()
    plt.show()
        
