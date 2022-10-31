from typing import List
import re

import xml.etree.ElementTree as xml_element_tree

from parsers.interface import ParserInterface
from schedule.schedule import Schedule, Lesson, LessonTime, lesson_format_from_str


class XMLRegexParser(ParserInterface):
    def parse_str(self, xml_str: str) -> Schedule:
        open_lesson_pos = [i.end(0) for i in re.finditer(r"<lesson>", xml_str)]
        close_lesson_pos = [i.start(0) for i in re.finditer(r"<\/lesson>", xml_str)]
        assert len(open_lesson_pos) == len(close_lesson_pos)
        lessons: List[Lesson] = []
        for i in range(len(open_lesson_pos)):
            lesson_str = xml_str[open_lesson_pos[i]:close_lesson_pos[i]]
            week_day = re.search(r"<week-day>([\s\S]*?)<\/week-day>", lesson_str)[1]
            name = re.search(r"<name>([\s\S]*?)<\/name>", lesson_str)[1]
            teacher = re.search(r"<teacher>([\s\S]*?)<\/teacher>", lesson_str)[1]
            lesson_format = re.search(r"<lesson-format>([\s\S]*?)<\/lesson-format>", lesson_str)[1]
            room = re.search(r"<room>([\s\S]*?)<\/room>", lesson_str)
            room = room[1] if room else None
            time = re.search(r"<time>([\s\S]*?)<\/time>", lesson_str)[1]
            weeks = re.search(r"<weeks>([\s\S]*?)<\/weeks>", lesson_str)[1]
            lessons += [
                Lesson(
                    teacher, room, name, LessonTime(time), week_day, lesson_format_from_str(lesson_format), [int(x) for x in weeks.split(", ")]
                )
            ]
        return Schedule(lessons)

    def parse_file(self, full_file_name: str) -> Schedule:
        with open(full_file_name) as file:
            xml_str = file.read()
        return self.parse_str(xml_str)
