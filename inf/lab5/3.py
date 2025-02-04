import csv
import matplotlib.pyplot as plt
import matplotlib
from functools import reduce
import operator

# 18, 16, 16, 18

f = open('data.csv')
c = csv.reader(f)

days = [[[], [], [], []] for _ in range(4)]
days_dates = [
    '18/09/18',
    '16/10/18',
    '16/11/18',
    '18/12/18'
]

for row in c:
    date = row[2]
    if date not in days_dates:
        continue

    day = days[days_dates.index(date)]

    day[0].append(float(row[4]))
    day[1].append(float(row[5]))
    day[2].append(float(row[6]))
    day[3].append(float(row[7]))

matplotlib.use('TkAgg')

fig = plt.figure(figsize=(10, 7))

labels = [
    [day_name + ' - ' + l for l in ['Открытие', 'Макс', 'Мин', 'Закрытие']]
    for day_name in days_dates
]

plt.boxplot(
    reduce(operator.add, days, []),
    tick_labels=reduce(operator.add, labels, []),
    showmeans=True
)

plt.xticks(rotation=80)

plt.title('Ящик с усами')

plt.show()
