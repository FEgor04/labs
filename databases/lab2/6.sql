SELECT "ГРУППА", people."ИД", "ИМЯ", "ФАМИЛИЯ", "ОТЧЕСТВО", "П_ПРКОК_ИД"
from "Н_ЛЮДИ" as people
         INNER JOIN "Н_УЧЕНИКИ" НУ on people."ИД" = НУ."ИД"
WHERE exists(SELECT *
             from "Н_УЧЕНИКИ"
                      INNER JOIN "Н_ПЛАНЫ" ON "Н_ПЛАНЫ"."ИД" = "Н_УЧЕНИКИ"."ПЛАН_ИД"
             WHERE "ПРИЗНАК" = 'отчисл'
               AND "ЧЛВК_ИД" = people."ИД"
               AND "КОНЕЦ" > '01.09.2012'::date
               AND "Н_ПЛАНЫ"."ФО_ИД" IN (1, 3))