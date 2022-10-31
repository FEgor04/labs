from functools import reduce
from typing import List

from presenters.interface import Presenter
from schedule.schedule import Schedule, Lesson, LessonTime, LessonFormat

indent = "  "


def present_weeks(weeks: List[int]) -> str:
    return str(weeks)[1:-1]


def present_lesson_time(lesson_time: LessonTime) -> str:
    return f"{lesson_time.start.strftime('%H:%M')}-{lesson_time.end.strftime('%H:%M')}"


def present_lesson_format(lesson_format: LessonFormat):
    if lesson_format == LessonFormat.REMOTELY:
        return "Дистанционный"
    if lesson_format == LessonFormat.BOTH:
        return "Очно - дистанционный"
    return "Очный"


def present_room(room: str):
    if room is None:
        return ""
    return room


def present_lesson(lesson: Lesson) -> str:
    return f'week-day: "{lesson.week_day}"\n{indent * 3}name: "{lesson.name}"\n{indent * 3}teacher: "{lesson.teacher}"\n{indent * 3}lesson-format: "{present_lesson_format(lesson.format)}"\n{indent * 3}room: "{present_room(lesson.room)}"\n{indent * 3}time: "{present_lesson_time(lesson.time)}"\n{indent * 3}weeks: "{present_weeks(lesson.weeks)}"'


class YAMLPresenter(Presenter):
    def present(self, schedule: Schedule) -> str:
        presented = "day:\n" + 1 * indent + "lessons:\n"
        if len(schedule.lessons) == 1:
            return presented + 3 * indent + present_lesson(schedule.lessons[0])
        for i in schedule.lessons:
            presented += 2 * indent + "- " + present_lesson(i) + "\n"
        return presented
