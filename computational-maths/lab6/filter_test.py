import numpy as np
from main import filter_xs

def test_filter_1():
    n1 = 5
    h = 1
    x = [0 + float(i * h) for i in range(n1)]
    x1 = [0 + float(i * h / 2) for i in range(n1 * 2)]
    actual, actual1 = filter_xs(x1, x1, h)
    assert(x == actual)
    assert(x == actual1)

def test_filter_1():
    x = [1, 2, 3, 4]
    x1 = [1, 1.25, 1.5, 1.75, 2, 2.25, 2.5, 2.75, 3, 3.25, 3.5, 3.75, 4]
    actual, actual1 = filter_xs(x1, x1, 1)
    assert(np.all(actual == x))

