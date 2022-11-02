import xml.etree.ElementTree as xml_element_tree
from typing import List

from parsers.interface import ParserInterface
from schedule.schedule import Schedule, Lesson, LessonTime, lesson_format_from_str


class XMLParser(ParserInterface):
    def parse_str(self, xml_str: str) -> Schedule:
        lessons = xml_element_tree.fromstring(xml_str)
        assert (lessons.tag == "day")
        schedule_lessons: List[Lesson] = []
        for lesson in lessons.findall("lesson"):
            schedule_lessons += [
                Lesson(
                    lesson.find("teacher").text,
                    lesson.find("room").text,
                    lesson.find("name").text,
                    LessonTime(
                        lesson.find("time").text,
                    ),
                    lesson.find("week-day").text,
                    lesson_format_from_str(lesson.find("lesson-format").text),
                    [int(x) for x in lesson.find("weeks").text.split(", ")]
                )
            ]

        return Schedule(schedule_lessons)

    def parse_file(self, full_file_name: str) -> Schedule:
        with open(full_file_name) as file:
            xml_str = file.read()
        return self.parse_str(xml_str)
