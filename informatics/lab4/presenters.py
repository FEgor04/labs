import datetime
import multiprocessing
from typing import List

from itertools import starmap
import matplotlib.pyplot as plt

from presenters.interface import Presenter
from presenters.lib_yaml import YAMLLibPresenter
from presenters.yaml import YAMLPresenter
from schedule.schedule import Schedule
from tests.xml.test_xml import get_schedule

LESSONS_CNT = sorted(list(set([int(1.1 ** x) for x in range(1, 50)])))


def benchmark_presenter(pool, presenter: Presenter, runs_number: int, schedule: Schedule):
    timingsDict = {"name": type(presenter).__name__,
                   "timings": pool.starmap(benchmark_one_time,
                                           map(lambda x: (presenter, runs_number, schedule, x), LESSONS_CNT))}
    print(timingsDict)
    timingsDict["timings"] = list(map(lambda x: x["time"], timingsDict["timings"]))
    return timingsDict


def benchmark_one_time(presenter: Presenter, runs_number, schedule: Schedule, lessons_cnt):
    schedule.lessons = [schedule.lessons[0]] * lessons_cnt
    timeStart = datetime.datetime.now()
    for i in range(runs_number):
        presenter.present(schedule)
    timeEnd = datetime.datetime.now()
    return {"cnt": len(schedule.lessons), "time": (timeEnd - timeStart).total_seconds()}


def benchmark_all(runs_number: int):
    schedule = get_schedule()
    parsers: List[Presenter] = [YAMLPresenter(), YAMLLibPresenter()]
    pool = multiprocessing.Pool(8)
    timings = list(starmap(benchmark_presenter, map(lambda x: (pool, x, runs_number, schedule), parsers)))
    print(timings)
    return timings


if __name__ == "__main__":
    runs_number = 1000
    timings = benchmark_all(runs_number)
    plt.title("Бенчмарк презентеров")
    plt.xlabel("Количество уроков")
    plt.ylabel(f"Время на {runs_number} запусков (с)")
    for i in timings:
        plt.plot(LESSONS_CNT, [i["timings"][j] for j in range(len(LESSONS_CNT))], marker="o", label=i["name"])
    plt.legend()
    plt.savefig("benchmark/benchmark_p.pdf")
