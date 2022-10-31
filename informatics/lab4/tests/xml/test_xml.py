import unittest

from parsers.xml import XMLParser
from parsers.xml_regex import XMLRegexParser
from schedule.schedule import Lesson, Schedule, LessonTime, LessonFormat


class XMLParserTestCase(unittest.TestCase):
    def test_parse_file(self):
        expectedSchedule = Schedule(
            [
                Lesson(
                    "Балакшин Павел Валерьевич",
                    None,
                    "Информатика (Лек): Zoom",
                    LessonTime(
                        "08:20-09:50"
                    ),
                    "Вторник",
                    LessonFormat.REMOTELY,
                    [5]
                ),
                Lesson(
                    "Балакшин Павел Валерьевич",
                    "ул. Ломоносова, д.9, лит. М",
                    "Информатика (Лек): Актовый зал ",
                    LessonTime(
                        "08:20-09:50"
                    ),
                    "Вторник",
                    LessonFormat.BOTH,
                    [3, 7, 9, 11, 13, 15, 17]
                ),
                Lesson(
                    "Клименков Сергей Викторович",
                    "ул. Ломоносова, д.9, лит. М",
                    "ОСНОВЫ ПРОФЕССИОНАЛЬНОЙ ДЕЯТЕЛЬНОСТИ(ЛЕК): АКТОВЫЙ ЗАЛ",
                    LessonTime(
                        "10:00-11:30"
                    ),
                    "Вторник",
                    LessonFormat.BOTH,
                    [3, 7, 9, 11, 13, 15, 17]
                ),
                Lesson(
                    "Клименков Сергей Викторович",
                    None,
                    "ОСНОВЫ ПРОФЕССИОНАЛЬНОЙ ДЕЯТЕЛЬНОСТИ(ЛЕК): ZOOM",
                    LessonTime(
                        "10:00-11:30"
                    ),
                    "Вторник",
                    LessonFormat.REMOTELY,
                    [5]
                ),
            ]
        )
        actualSchedule = XMLParser().parse_file("xml/input.xml")
        for i in range(4):
            self.assertEqual(expectedSchedule.lessons[i], actualSchedule.lessons[i])


class XMLRegexParserTestCase(unittest.TestCase):
    def test_parse_file(self):
        expectedSchedule = Schedule(
            [
                Lesson(
                    "Балакшин Павел Валерьевич",
                    None,
                    "Информатика (Лек): Zoom",
                    LessonTime(
                        "08:20-09:50"
                    ),
                    "Вторник",
                    LessonFormat.REMOTELY,
                    [5]
                ),
                Lesson(
                    "Балакшин Павел Валерьевич",
                    "ул. Ломоносова, д.9, лит. М",
                    "Информатика (Лек): Актовый зал",
                    LessonTime(
                        "08:20-09:50"
                    ),
                    "Вторник",
                    LessonFormat.BOTH,
                    [3, 7, 9, 11, 13, 15, 17]
                ),
                Lesson(
                    "Клименков Сергей Викторович",
                    "ул. Ломоносова, д.9, лит. М",
                    "ОСНОВЫ ПРОФЕССИОНАЛЬНОЙ ДЕЯТЕЛЬНОСТИ(ЛЕК): АКТОВЫЙ ЗАЛ",
                    LessonTime(
                        "10:00-11:30"
                    ),
                    "Вторник",
                    LessonFormat.BOTH,
                    [3, 7, 9, 11, 13, 15, 17]
                ),
                Lesson(
                    "Клименков Сергей Викторович",
                    None,
                    "ОСНОВЫ ПРОФЕССИОНАЛЬНОЙ ДЕЯТЕЛЬНОСТИ(ЛЕК): ZOOM",
                    LessonTime(
                        "10:00-11:30"
                    ),
                    "Вторник",
                    LessonFormat.REMOTELY,
                    [5]
                ),
            ]
        )
        actualSchedule = XMLRegexParser().parse_file("xml/input.xml")
        for i in range(4):
            self.assertEqual(str(expectedSchedule.lessons[i]), str(actualSchedule.lessons[i]))



if __name__ == '__main__':
    unittest.main()
