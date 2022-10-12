import re

# 367581
# 314
# 8<\

def count_smiles(s):
    return len(re.findall(r"8<\\", s))

def test_count_smiles():
    tests = [
            ("hello there 8<\\", 1),
            ("8<\\ 8<\\", 2),
            ("no smiles", 0),
            ("8<\\", 1),
            ("8<\\8<\\", 2)
            ]
    for s, expected in tests:
        assert count_smiles(s) == expected

