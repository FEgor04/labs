import matplotlib.pyplot as plt
import numpy as np

x = np.array([0,
     10,
     20,
     30,
     40,
     50,
     60,
     70,
     80,
     90,
     100,
     200,
     300,
     400 ])

y = [
        0.34195097812856,
        0.424321891937629,
        0.447911582233698,
        0.486724572857052,
        0.526574957295676,
        0.614332708458154,
        0.667829372575655,
        0.664976303593249,
        0.723918839226699,
        0.86903784702361,
        0.916290731874155,
        1.16315080980568,
        1.70474809223843,
        1.23214368129263
        ]

plt.xlabel(r"$R_м$, Ом")
plt.ylabel(r"$\lambda$")
plt.scatter(x, y, label='Эксп. данные')

k = 0.005433038
b = 0.335972606

x1 = np.linspace(-65, 110)

plt.plot(x1, k * x1 + b, color='orange', label='МНК')

plt.xlim([-70, 120])
plt.ylim([-0.1, 1])

plt.grid()
plt.legend()
plt.savefig("./img/lambda_r_plot.pdf")
