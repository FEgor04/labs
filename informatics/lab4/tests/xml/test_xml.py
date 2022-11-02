import unittest

from parsers.xml_default import XMLDefaultParser
from parsers.xml_lib import XMLParser
from parsers.xml_regex import XMLRegexParser
from schedule.schedule import Lesson, Schedule, LessonTime, LessonFormat


def get_schedule() -> Schedule:
    return Schedule(
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


class XMLParserTestCase(unittest.TestCase):
    def test_parse_file(self):
        expected_schedule = get_schedule()
        actual_schedule = XMLParser().parse_file("xml/input.xml")
        for i in range(4):
            self.assertEqual(expected_schedule.lessons[i], actual_schedule.lessons[i])

    def test_parse_bad_str(self):
        wasError = False
        try:
            schedule = XMLParser().parse_str("not an xml")
        except:
            wasError = True
        self.assertTrue(wasError)


class XMLRegexParserTestCase(unittest.TestCase):
    def test_parse_file(self):
        expected_schedule = get_schedule()
        actual_schedule = XMLRegexParser().parse_file("xml/input.xml")
        for i in range(4):
            self.assertEqual(str(expected_schedule.lessons[i]), str(actual_schedule.lessons[i]))

    def test_parse_bad_str(self):
        schedule = XMLRegexParser().parse_str("not an xml")
        self.assertEqual(0, len(schedule.lessons))


class XMLDefaultParserTestCase(unittest.TestCase):
    def test_parse_file(self):
        expected_schedule = get_schedule()
        actual_schedule = XMLDefaultParser().parse_file("xml/input.xml")
        for i in range(4):
            self.assertEqual(str(expected_schedule.lessons[i]), str(actual_schedule.lessons[i]))

    def test_parse_bad_str(self):
        schedule = XMLDefaultParser().parse_str("not an xml")
        self.assertEqual(0, len(schedule.lessons))


if __name__ == '__main__':
    unittest.main()
