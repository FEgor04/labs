import numpy as np
from main import filter_xs

def test_filter_1():
    n1 = 5
    h = 1
    x = [0 + float(i * h) for i in range(n1)]
    x1 = [0 + float(i * h / 2) for i in range(n1 * 2)]
    actual, actual1 = filter_xs(x1, x1, h)
    assert(np.all(x == actual))
    assert(np.all(x == actual1))

def test_filter_2():
    x = [1, 2, 3, 4]
    x1 = [1, 1.25, 1.5, 1.75, 2, 2.25, 2.5, 2.75, 3, 3.25, 3.5, 3.75, 4]
    actual, actual1 = filter_xs(x1, x1, 1)
    assert(np.all(actual == x))

def test_filter_3():
    x = [1, 2, 3, 4]
    x1 = [1, 1.25, 1.5, 1.75, 2, 2.25, 2.5, 2.75, 3, 3.25, 3.5, 3.75, 4, 4.25, 4.5, 4.75, 5]
    actual, actual1 = filter_xs(x1, x1, 1, 4)
    assert(np.all(actual == x))

def test_filter_odd():
    x = [1, 2, 3]
    x1 = [1, 1.5, 2, 2.5, 3, 3.5]
    actual, actual1 = filter_xs(x1, x1, 1, 3)
    assert(np.all(actual == x))

