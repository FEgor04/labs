SELECT 
  g."ПЛАН_ИД", 
  count(g."ГРУППА") as "cnt" 
FROM 
  "Н_ГРУППЫ_ПЛАНОВ" as g 
  LEFT JOIN "Н_ПЛАНЫ" as p ON g."ПЛАН_ИД" = p."ИД" 
WHERE 
  "ФО_ИД" = 3 
GROUP BY 
  g."ПЛАН_ИД" 
HAVING 
  count(g."ГРУППА") = 2;

