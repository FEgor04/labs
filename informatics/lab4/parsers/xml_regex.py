import re
from typing import List

from parsers.interface import ParserInterface
from schedule.schedule import Schedule, Lesson, LessonTime, lesson_format_from_str

class XMLRegexParser(ParserInterface):
    def parse_str(self, xml_str: str) -> Schedule:
        open_lesson_pos = [i.end(0) for i in re.finditer(r"<lesson>", xml_str)]
        close_lesson_pos = [i.start(0) for i in re.finditer(r"<\/lesson>", xml_str)]
        assert len(open_lesson_pos) == len(close_lesson_pos)
        lessons: List[Lesson] = []
        week_day_re = re.compile(r"<week-day>([\s\S]*?)<\/week-day>")
        name_re = re.compile(r"<name>([\s\S]*?)<\/name>")
        teacher_re = re.compile(r"<teacher>([\s\S]*?)<\/teacher>")
        lesson_format_re = re.compile(r"<lesson-format>([\s\S]*?)<\/lesson-format>")
        room_re = re.compile(r"<room>([\s\S]*?)<\/room>")
        time_re = re.compile(r"<time>([\s\S]*?)<\/time>")
        weeks_re = re.compile(r"<weeks>([\s\S]*?)<\/weeks>")
        for i in range(len(open_lesson_pos)):
            lesson_str = xml_str[open_lesson_pos[i]:close_lesson_pos[i]]
            week_day = week_day_re.search(lesson_str)[1]
            name = name_re.search(lesson_str)[1]
            teacher = teacher_re.search(lesson_str)[1]
            lesson_format = lesson_format_re.search(lesson_str)[1]
            room = room_re.search(lesson_str)
            room = room[1] if room else None
            time = time_re.search(lesson_str)[1]
            weeks = weeks_re.search(lesson_str)[1]
            lessons += [
                Lesson(
                    teacher, room, name, LessonTime(time), week_day, lesson_format_from_str(lesson_format),
                    [int(x) for x in weeks.split(", ")]
                )
            ]
        return Schedule(lessons)

    def parse_file(self, full_file_name: str) -> Schedule:
        with open(full_file_name) as file:
            xml_str = file.read()
        return self.parse_str(xml_str)
