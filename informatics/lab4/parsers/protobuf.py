from typing import List

from parsers.interface import ParserInterface
import schedule.schedule_pb2
import schedule.schedule


class ProtobufParser(ParserInterface):
    def parse_str(self, proto_str: str) -> schedule.schedule.Schedule:
        schedule_proto = schedule.schedule_pb2.Schedule()
        schedule_proto.ParseFromString(proto_str)
        # print(schedule_proto)
        lessons: List[schedule.schedule.Lesson] = []
        for lesson in schedule_proto.lessons:
            lessons += [
                schedule.schedule.Lesson(
                    lesson.teacher,
                    lesson.room,
                    lesson.name,
                    schedule.schedule.LessonTime(
                        f"{str(lesson.time.start.hour).zfill(2)}:{str(lesson.time.start.minute).zfill(2)}-{str(lesson.time.end.hour).zfill(2)}:{str(lesson.time.end.minute).zfill(2)}"
                    ),
                    lesson.week_day,
                    lesson.format,
                    lesson.weeks
                )
            ]
        sch = schedule.schedule.Schedule(lessons)
        print(sch)
        return sch

    def parse_file(self, full_file_name: str) -> schedule.schedule.Schedule:
        with open(full_file_name, "rb") as file:
            proto_str = file.read()
        return self.parse_str(proto_str)
