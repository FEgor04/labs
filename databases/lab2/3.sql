SELECT 
  * 
from 
  "Н_УЧЕНИКИ" as s 
  INNER JOIN "Н_ЛЮДИ" as p ON p."ИД" = s."ЧЛВК_ИД" 
WHERE 
  s."ГРУППА" = '3102' 
  AND p."ИНН" is NULL;

