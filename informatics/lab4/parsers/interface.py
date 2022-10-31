from schedule.schedule import Schedule


class ParserInterface:
    def __init__(self):
        pass

    def parse_file(self, full_file_name: str) -> Schedule:
        pass
