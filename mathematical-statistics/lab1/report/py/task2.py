import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
sns.set()

PRINT_LATEX_TABLE = True

df = pd.read_csv("iris.csv")
def calculate(data, species = "All"):
  area = data
  mean = np.mean(area)
  dispersia = np.var(area)
  median = np.median(area)
  quantile = np.quantile(area, 2/5)
  if PRINT_LATEX_TABLE:
    print(f"{species} & {len(data)} & {mean:.4f} & {dispersia:.4f} & {median:.4f} & {quantile:.4f} \\\\")
  else:
    print("Количество\t\t", len(data))
    print("Среднее\t\t\t", mean)
    print("Дисперсия\t\t", dispersia)
    print("Медиана\t\t\t", median)
    print("Квантиль 2/5\t\t", quantile)

if not PRINT_LATEX_TABLE:
  print("=======\nПлощадь чашелистика\n=======")
calculate(df["Sepal.Length"] * df["Sepal.Width"])
species = df["Species"].unique()
for s in species:
  print("Вид\t\t\t", s)
  data = df[df['Species'] == s]
  calculate(data["Sepal.Length"] * data["Sepal.Width"], s)
  print("\n\n")

if not PRINT_LATEX_TABLE:
  print("=======\nПлощадь лепестка\n=======")
calculate(df["Petal.Length"] * df["Petal.Width"])
for s in species:
  if not PRINT_LATEX_TABLE:
    print("Вид\t\t\t", s)
  data = df[df['Species'] == s]
  calculate(data["Petal.Length"] * data["Petal.Width"], s)

  print("=======\nСуммарная площадь чашелистика и лепестка\n=======")
calculate(df["Petal.Length"] * df["Petal.Width"] + df["Sepal.Length"] * df["Sepal.Width"])
for s in species:
  if not PRINT_LATEX_TABLE:
    print("Вид\t\t\t", s)
  data = df[df['Species'] == s]
  calculate(data["Petal.Length"] * data["Petal.Width"] + data["Sepal.Length"] * data["Sepal.Width"], s)

def plot_everything(data, ax):
  sns.kdeplot(data, ax=ax[0], cumulative=True)
  sns.histplot(data, ax=ax[1])
  sns.boxplot(np.array(data), ax=ax[2])

fig, ax = plt.subplots(3, 4, figsize=(10, 10), sharey='row', sharex='row')
plot_everything(df["Sepal.Width"] * df["Sepal.Length"], [ax[0][0], ax[1][0], ax[2][0]])
ax[0][0].set_title("All, Sepal")
for i in range(len(species)):
  s = species[i]
  data = df[df["Species"] == s]
  ax[0][1 + i].set_title(f"{s}, Sepal")
  plot_everything(data["Sepal.Width"] * data["Sepal.Length"], [ax[0][1 + i], ax[1][1 + i], ax[2][1 + i]])

plt.savefig("figures/2_sepal.pdf")
plt.clf()

fig, ax = plt.subplots(3, 4, figsize=(10, 10), sharey='row', sharex='row')
plot_everything(df["Petal.Width"] * df["Petal.Length"], [ax[0][0], ax[1][0], ax[2][0]])
ax[0][0].set_title("All, Petal")
for i in range(len(species)):
  s = species[i]
  data = df[df["Species"] == s]
  ax[0][1 + i].set_title(f"{s}, Petal")
  plot_everything(data["Petal.Width"] * data["Petal.Length"], [ax[0][1 + i], ax[1][1 + i], ax[2][1 + i]])

plt.savefig("figures/2_petal.pdf")
plt.clf()


fig, ax = plt.subplots(3, 4, figsize=(10, 10), sharey='row', sharex='row')
plot_everything(df["Petal.Width"] * df["Petal.Length"] + df["Sepal.Width"] * df["Sepal.Length"], [ax[0][0], ax[1][0], ax[2][0]])
ax[0][0].set_title("All, Sepal + Petal")
for i in range(len(species)):
  s = species[i]
  data = df[df["Species"] == s]
  ax[0][1 + i].set_title(f"{s}, Sepal + Petal")
  plot_everything(data["Petal.Width"] * data["Petal.Length"] + data["Sepal.Width"] * data["Sepal.Length"], [ax[0][1 + i], ax[1][1 + i], ax[2][1 + i]])

plt.savefig("figures/2_sepal_petal.pdf")
plt.clf()
