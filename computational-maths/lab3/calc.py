from solution import rectangles_method, trapeze_method, simpson_method

def f(x):
    return 3 * x**3 - 4 * x ** 2 + 5 * x - 16

a1 = f(2) * 41 * 2 / 840
a2 = f(7/3) * 216 * 2 / 840
a3 = f(8/3) * 27 * 2 / 840
a4 = f(3) * 272 * 2 / 840
a5 = f(10/3) * 27 * 2 / 840
a6 = f(11/3) * 216 * 2 / 840
a7 = f(4) * 41 * 2 / 840
a = [a1,a2,a3,a4,a5,a6,a7]

print(simpson_method(f, 2, 4, 0.01))
