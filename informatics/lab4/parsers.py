import datetime
import multiprocessing
from typing import List

from itertools import starmap
import matplotlib.pyplot as plt

from parsers.interface import ParserInterface
from parsers.xml_default import XMLDefaultParser
from parsers.xml_lib import XMLParser
from parsers.xml_regex import XMLRegexParser

LESSONS_CNT = sorted(list(set([int(1.1 ** x) for x in range(1, 75)])))


def benchmark_parser(pool, parser: ParserInterface, runs_number: int, xml_str: str):
    timingsDict = {"name": type(parser).__name__,
        "timings": pool.starmap(benchmark_one_time, map(lambda x: (parser, runs_number, xml_str, x), LESSONS_CNT))}
    print(timingsDict)
    timingsDict["timings"] = list(map(lambda x: x["time"], timingsDict["timings"]))
    return timingsDict


def benchmark_one_time(parser, runs_number, xml_str, lessons_cnt):
    my_xml_str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<day>\n" + (xml_str + "\n") * lessons_cnt + "\n</day>"
    timeStart = datetime.datetime.now()
    for i in range(runs_number):
        parsed = parser.parse_str(my_xml_str)
    timeEnd = datetime.datetime.now()
    return {"cnt": lessons_cnt, "time": (timeEnd - timeStart).total_seconds()}


def benchmark_all(parsers, runs_number: int):
    with open("benchmark.xml") as f:
        xml_str = f.read()
        f.close()
    pool = multiprocessing.Pool(8)
    timings = list(starmap(benchmark_parser, map(lambda x: (pool, x, runs_number, xml_str), parsers)))
    print(timings)
    return timings

def get_color(parser: ParserInterface):
    if isinstance(parser, XMLParser):
        return "red"
    if isinstance(parser, XMLRegexParser):
        return "blue"
    return "green"

if __name__ == "__main__":
    runs_number = 100
    parsers: List[ParserInterface] = [
        XMLDefaultParser(),
        XMLRegexParser(),
        XMLParser()
    ]
    timings = benchmark_all(parsers, runs_number)
    plt.title("Бенчмарк парсеров")
    plt.xlabel("Количество уроков")
    plt.ylabel(f"Время на {runs_number} запусков (с)")
    for i in range(len(timings)):
        t = timings[i]
        plt.plot(LESSONS_CNT, [t["timings"][j] for j in range(len(LESSONS_CNT))],
                 marker="o", label=t["name"], color=get_color(parsers[i]))
    plt.legend()
    plt.savefig("report/img/benchmark_all.pdf")

