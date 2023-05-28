WITH RECURSIVE r AS (SELECT "ИД", "ОТД_ИД", "КОРОТКОЕ_ИМЯ", CAST("КОРОТКОЕ_ИМЯ" AS TEXT) as path, 1 as level
                     from "Н_ОТДЕЛЫ"
                     WHERE "ОТД_ИД" IS NULL

                     UNION

                     SELECT o."ИД",
                            o."ОТД_ИД",
                            o."КОРОТКОЕ_ИМЯ",
                            CONCAT(r.path, '->', CAST(o."КОРОТКОЕ_ИМЯ" AS TEXT)) as path,
                            r.level + 1                                          as level
                     FROM "Н_ОТДЕЛЫ" as o
                              JOIN r
                                   ON o."ОТД_ИД" = r."ИД")
SELECT level, "КОРОТКОЕ_ИМЯ", path
from r
order by path