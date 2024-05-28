def is_divisable(x0, xn, h):
    as_float = (x0 - xn) / h
    as_integer = int((x0 - xn) // h)
    if as_float - as_integer <= 1e-9:
        return True
    return True

