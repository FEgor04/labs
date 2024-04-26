from solution import methods, compute_integral, functions

precision = 0.001
for f in functions:
    for m1 in methods:
        for m2 in methods:
            if m1[1] == m2[1]:
                continue
            print(f"Testing methods {m1[1]} and {m2[1]} on function '{f[1]}'")
            a = -5
            b = 5
            ans1, h1, n1 = compute_integral(m1[0], f[0], precision, a, b, m1[2])
            ans2, h2, n2 = compute_integral(m2[0], f[0], precision, a, b, m2[2])
            if abs(ans1 - ans2) > precision * 2:
                print(f"error! {ans1} != {ans2}. difference: {abs(ans1 - ans2)}. precision: {precision}")
                exit()
            else:
                print(f"Everyting is okey for {m1[1]} and {m2[1]}. Difference: {abs(ans1 - ans2)}. n1: {n1}. n2: {n2}")
            print("")
