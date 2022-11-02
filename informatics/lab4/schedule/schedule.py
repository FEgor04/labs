from enum import Enum, IntEnum
from datetime import time

from typing import List


class LessonFormat(IntEnum):
    REMOTELY = 0
    ON_PLACE = 1
    BOTH = 2

    @classmethod
    def __repr__(self):
        if self == LessonFormat.REMOTELY:
            return "Дистанционный"
        elif self == LessonFormat.BOTH:
            return "Очно - дистанционный"
        return "Очный"

def present_lesson_format(lesson_format: LessonFormat):
    if lesson_format == LessonFormat.REMOTELY:
        return "Дистанционный"
    if lesson_format == LessonFormat.BOTH:
        return "Очно - дистанционный"
    return "Очный"

def lesson_format_from_str(lesson_format: str) -> LessonFormat:
    if lesson_format.lower() == "дистанционный":
        return LessonFormat.REMOTELY
    elif lesson_format.lower() == "очный":
        return LessonFormat.ON_PLACE
    return LessonFormat.BOTH


class LessonTime:
    def __init__(self, date_str: str):
        self.start = time.fromisoformat(date_str[:date_str.find("-")])
        self.end = time.fromisoformat(date_str[date_str.find("-") + 1:])

    def __eq__(self, other: 'LessonTime'):
        return self.start == other.start and self.end == other.end

    def __repr__(self):
        return f"{self.start.strftime('%H:%M')}-{self.end.strftime('%H:%M')}"


class Lesson:
    def __init__(self, teacher: str, room: str, name: str, lesson_time: LessonTime, week_day: str, lesson_format: LessonFormat,
                 weeks: List[int]):
        self.teacher = teacher
        self.name = name
        self.room = room
        self.time = lesson_time
        self.week_day = week_day
        self.format = lesson_format
        self.weeks = weeks

    def __eq__(self, other: 'Lesson'):
        return self.teacher == other.teacher and self.room == other.room and self.time == other.time and self.week_day == other.week_day and self.format == other.format and self.weeks == other.weeks

    def __str__(self):
        return f"{self.teacher=} {self.room=} {self.name=} {self.format=} {self.weeks=} {self.time=} {self.week_day=}"

    def to_dict(self):
        return {
            "name": self.name,
            "teacher": self.teacher,
            "room": self.room,
            "time": str(self.time),
            "week_day": self.week_day,
            "format": present_lesson_format(self.format),
            "weeks": str(self.weeks)[1:-1]
        }


class Schedule:
    def __init__(self, lessons: List[Lesson]):
        self.lessons = lessons

    def __eq__(self, other: 'Schedule'):
        return self.lessons == other.lessons

    def __repr__(self):
        return map(lambda x: x.__repr__, self.lessons).__repr__()

    def to_dict(self):
        return {
            "lessons": [x.to_dict() for x in self.lessons]
        }