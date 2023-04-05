SELECT
  nv."ИД", ntv."ИД"
FROM
  "Н_ВЕДОМОСТИ" as nv
INNER JOIN
  "Н_ТИПЫ_ВЕДОМОСТЕЙ" as ntv
ON
  nv."ТВ_ИД" = ntv."ИД"
WHERE
  ntv."ИД" < 1
  AND
  nv."ЧЛВК_ИД" = 142390
  AND
  nv."ЧЛВК_ИД" = 105590;
