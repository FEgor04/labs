import matplotlib.pyplot as plt
import numpy as np

q = [
        12.6842,
        10.9845,
        10.6184,
        10.0980,
        9.6492 ,
        8.8831 ,
        8.5252 ,
        8.5426 ,
        8.2142 ,
        7.6239 ,
        7.4800 ,
        6.9632 ,
        6.4980 ,
        6.8674 
        ]

r = [
        0,
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
        400         ]

plt.xlabel(r"$R_м$, Ом")
plt.ylabel(r"$Q$")

plt.scatter(r, q, label='Эксп. данные')

plt.grid()
plt.legend()
plt.savefig("./img/q_r_plot.pdf")
