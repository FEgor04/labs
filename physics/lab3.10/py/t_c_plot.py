import matplotlib.pyplot as plt
import numpy as np

t_exp = [
        0.09 ,
        0.108,
        0.131,
        0.39 
        ]

t_theor = [0.0907,
           0.1111,
           0.1327,
           0.4292
           ]

c = [
        0.022,
        0.033        ,
        0.047,
        0.47 
        ]

plt.plot(c, t_exp, label="Эксп. данные")
plt.plot(c, t_theor, label="Теор. данные")
plt.xlabel("$C$, мкФ")
plt.ylabel("$T$, мс")
plt.legend()
plt.savefig("./img/t_c_plot.pdf")

