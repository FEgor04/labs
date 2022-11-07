from parsers.protobuf import ProtobufParser
from parsers.xml_default import XMLDefaultParser
from presenters.lib_yaml import YAMLLibPresenter
from presenters.protobuf import ProtobufPresenter
from presenters.yaml import YAMLPresenter

schedule = ProtobufParser().parse_file("output1.bin")
print(schedule.to_dict())
# proto_str = ProtobufPresenter().present(schedule)
# with open("output1.bin", "wb") as f:
#     f.write(proto_str)