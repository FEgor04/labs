from datetime import datetime

from presenters.interface import Presenter
import schedule.schedule_pb2
from schedule.schedule import Schedule, LessonTime


class ProtobufPresenter(Presenter):
    def present(self, sch: Schedule):
        schedule_proto = schedule.schedule_pb2.Schedule()
        schedule_proto.lessons.append(self.lessonToProto(sch.lessons[0]))
        return (schedule_proto.SerializeToString())

    def lessonToProto(self, lesson: schedule.schedule.Lesson) -> schedule.schedule_pb2.Lesson:
        lesson_proto = schedule.schedule_pb2.Lesson()
        lesson_proto.name = lesson.name
        lesson_proto.teacher = lesson.teacher
        lesson_proto.week_day = lesson.week_day
        for i in lesson.weeks:
            lesson_proto.weeks.append(i)
        lesson_proto.format = int(lesson.format)
        lesson_proto.time.start.hour = lesson.time.start.hour
        lesson_proto.time.start.minute = lesson.time.start.minute
        lesson_proto.time.end.hour = lesson.time.end.hour
        lesson_proto.time.end.minute = lesson.time.end.minute
        return lesson_proto

    def lesson_time_to_proto(self, lesson_time: LessonTime):
        lesson_time_proto = schedule.schedule_pb2.LessonTime()

        return lesson_time_proto

    def time_to_proto(self, lesson_time: datetime):
        time_proto = schedule.schedule_pb2.Time()
        time_proto.hour = lesson_time.hour
        time_proto.minute = lesson_time.minute
        return time_proto
