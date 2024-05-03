import matplotlib.pyplot as plt
import numpy as np

frequency = np.array([4500,
                      4550,
                      4600,
                      4650,
                      4700,
                      4750,
                      4800,
                      4850,
                      4900,
                      4950,
                      5000,
                      5050,
                      5100,
                      5150,
                      5200,
                      5250,
                      5300,
                      5350,
                      5400,
                      5450,
                      5500])

ampl = np.array([
    8.4,
    8.6,
    8.6,
    8.8,
    9.2,
    9.2,
    9.2,
    8.8,
    8.6,
    8.6,
    8.6,
    8.4,
    8,
    8,
    7.8,
    7.6,
    7.6,
    7.2,
    7,
    7,
    6.6
    ])

u_max = max(ampl)
delta_u = 1/(2**0.5) * u_max

plt.plot(frequency, ampl)
plt.xlabel(r"$f$, Гц")
plt.ylabel(r"$U$, В")
plt.grid()
plt.xticks(frequency, rotation=60)

plt.plot([min(frequency), max(frequency)], [delta_u, delta_u], '-.')

plt.savefig("./figures/ampl_frequency_plot.pdf")
