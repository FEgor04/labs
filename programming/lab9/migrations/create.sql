CREATE TYPE VEHICLE_TYPE AS ENUM('PLANE', 'SUBMARINE', 'BOAT', 'BICYCLE');
CREATE TYPE FUEL_TYPE AS ENUM('GASOLINE', 'ELECTRICITY', 'MANPOWER', 'PLASMA', 'ANTIMATTER');

CREATE TABLE IF NOT EXISTS "user"(
  id SERIAL PRIMARY KEY,
  username VARCHAR(40) UNIQUE NOT NULL,
  password VARCHAR(64) NOT NULL
);
  

CREATE TABLE IF NOT EXISTS VEHICLES(
  ID SERIAL PRIMARY KEY,
  NAME TEXT NOT NULL,
  X INTEGER NOT NULL,
  Y BIGINT,
  CREATION_DATE DATE DEFAULT NOW() NOT NULL,
  ENGINE_POWER DOUBLE PRECISION NOT NULL CONSTRAINT positive_engine_power CHECK (ENGINE_POWER > 0),
  TYPE VEHICLE_TYPE,
  FUEL FUEL_TYPE NOT NULL,
  CREATOR_ID INTEGER REFERENCES USERS(ID) NOT NULL
);
