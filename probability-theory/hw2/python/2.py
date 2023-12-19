import matplotlib.pyplot as plt
import numpy as np
x_space = [120, 120, 130, 130, 130, 140, 140, 140, 150, 150, 150, 160, 160, 160, 170, 170]
y_space = [8.0, 8.8, 8.8, 9.6, 10.4, 9.6, 10.4, 11.2, 10.4, 11.2, 12, 12, 12.8, 13.6, 12.8, 13.6]


def y(x):
    return 0.096 * x - 2.971

plt.grid()
plt.xticks(list(set(x_space)))
plt.yticks(list(set(y_space)))

reg_space_x = np.linspace(min(x_space) - 10, max(x_space) + 10)
reg_space_y = y(reg_space_x)

plt.plot(reg_space_x, reg_space_y, label="Линия регрессии")
plt.scatter(x_space, y_space, label="Случайные точки", color='red')
plt.xlabel(r"$X$")
plt.ylabel(r"$Y$")
plt.legend()
plt.savefig('regression.pdf')
