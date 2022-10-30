from parsers.interface import ParserInterface


class XMLParser(ParserInterface):
    def parse_file(self, full_file_name: str):
        file_str = ""
        with open(full_file_name) as file:
            file_str = file

