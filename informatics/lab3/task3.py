from collections import Counter
import re

def get_wovels_count(s):
    return Counter(re.findall(r"[а|я|у|ю|о|е|ё|э|и|ы]",s))

def get_words(s):
    return re.findall(r"[а-яА-Я]+", s)

def check_wovels_count(s):
    cnt = 0
    counter = get_wovels_count(s)
    for cnt_now in counter:
        cnt += counter[cnt_now] >= 1
    return cnt == 1
    
def get_ans(s):
    arr = [word for word in get_words(s) if check_wovels_count(word)]
    arr.sort(key=len)
    return arr



def test_get_ans():
    tests = [
            ("Классное слово – обороноспособность, которое должно идти после слов: трава и молоко.", [
                "и",
                "идти",
                "слов",
                "слово",
                "трава",
                "должно",
                "молоко",
                "обороноспособность"
                ]),
            ("слово Классное", ["слово"]),
            ("нету", []),
            ("а тут есть", ["а", "тут", "есть"]),
            ("", [])
            ]
    for s, expected in tests:
        assert get_ans(s) == expected
