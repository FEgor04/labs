from typing import List

from parsers.interface import ParserInterface
from schedule.schedule import Schedule, Lesson, LessonTime, lesson_format_from_str


class XMLDefaultParser(ParserInterface):
    def parse_str(self, xml_str: str) -> Schedule:
        lessons: List[Lesson] = []
        lesson_tags = self.__find_tag_contains(xml_str, "lesson")
        for lesson_str in lesson_tags:
            name = self.__find_tag_contains(lesson_str, "name")[0]
            week_day = self.__find_tag_contains(lesson_str, "week-day")[0]
            teacher = self.__find_tag_contains(lesson_str, "teacher")[0]
            lesson_format = self.__find_tag_contains(lesson_str, "lesson-format")[0]
            room = self.__find_tag_contains(lesson_str, "room")
            room = room[0] if len(room) > 0 else None
            time = self.__find_tag_contains(lesson_str, "time")[0]
            weeks = self.__find_tag_contains(lesson_str, "weeks")[0]
            lessons += [
                Lesson(
                    teacher, room, name, LessonTime(time), week_day, lesson_format_from_str(lesson_format), [int(x) for x in weeks.split(", ")]
                )
            ]
        return Schedule(lessons)


    def __find_tag_contains(self, xml_str: str, tag: str):
        start, end = self.__find_substr(xml_str, f"<{tag}>", True), self.__find_substr(xml_str, f"</{tag}>")
        assert len(start) == len(end)
        ans = [xml_str[start[i]:end[i]] for i in range(len(start))]
        return ans

    def __find_substr(self, xml_str: str, substr: str, get_end = False):
        pos = []
        for i in range(len(xml_str) - len(substr)):
            if xml_str[i:i+len(substr)] == substr:
                pos += [i + (len(substr) if get_end else 0)]
        return pos

    def parse_file(self, full_file_name: str) -> Schedule:
        with open(full_file_name) as file:
            xml_str = file.read()
        return self.parse_str(xml_str)
