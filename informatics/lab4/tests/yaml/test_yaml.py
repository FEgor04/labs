import unittest

from presenters.yaml import YAMLPresenter
from schedule.schedule import Schedule, Lesson, LessonTime, LessonFormat


class YAMLPresenterTestCase(unittest.TestCase):
    def test_presenter_many_lessons(self):
        schedule = Schedule(
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
                )
            ]
        )
        with open("yaml/input.yaml") as f:
            expected = f.read()
        actual = YAMLPresenter().present(schedule)
        self.assertEqual(expected, actual)

    def test_presenter_one_lesson(self):
        schedule = Schedule(
            [
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
                )
            ]
        )

        with open("yaml/input1.yaml") as f:
            expected = f.read()
        actual = YAMLPresenter().present(schedule)
        self.assertEqual(expected, actual)


if __name__ == '__main__':
    unittest.main()
