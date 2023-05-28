select 
  "ГРУППА", 
  AVG(
    now() - p."ДАТА_РОЖДЕНИЯ"
  ) as age 
FROM 
  "Н_УЧЕНИКИ" as s 
  LEFT JOIN "Н_ЛЮДИ" as p ON s."ЧЛВК_ИД" = p."ИД" 
GROUP BY 
  s."ГРУППА" 
HAVING 
  AVG(
    NOW() - p."ДАТА_РОЖДЕНИЯ"
  ) > (
    select 
      AVG(
        now() - p."ДАТА_РОЖДЕНИЯ"
      ) as age 
    FROM 
      "Н_УЧЕНИКИ" as s 
      LEFT JOIN "Н_ЛЮДИ" as p ON s."ЧЛВК_ИД" = p."ИД" 
    WHERE 
      s."ГРУППА" = '1100' 
    GROUP BY 
      s."ГРУППА"
  );

