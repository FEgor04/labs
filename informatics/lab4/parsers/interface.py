from schedule.schedule import Schedule


class ParserInterface:
    def __init__(self):
        pass

    def parse_file(self, full_file_name: str) -> Schedule:
        with open(full_file_name) as file:
            xml_str = file.read()
        return self.parse_str(xml_str)

    def parse_str(self, xml_str: str) -> Schedule:
        pass
