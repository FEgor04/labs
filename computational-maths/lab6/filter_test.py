from main import filter_xs

def test_filter():
    n1 = 5
    h = 1
    x = [0 + float(i * h) for i in range(n1)]
    x1 = [0 + float(i * h / 2) for i in range(n1 * 2)]
    actual, actual1 = filter_xs(x1, x1, h)
    assert(x == actual)
    assert(x == actual1)

