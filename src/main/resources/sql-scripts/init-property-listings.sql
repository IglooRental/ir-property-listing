--DROP TABLE IF EXISTS "propertylistings";
--CREATE TABLE IF NOT EXISTS "propertylistings" ("id" VARCHAR(128) PRIMARY KEY, "property_id" VARCHAR(256) NOT NULL, "lister_id" VARCHAR(128) NOT NULL, "date" VARCHAR(128) NOT NULL, "duration" INTEGER NOT NULL);
INSERT INTO "propertylistings" ("id", "property_id", "lister_id", "date", "duration") VALUES ('0', '1', '1', '1999-03-30', 21);
INSERT INTO "propertylistings" ("id", "property_id", "lister_id", "date", "duration") VALUES ('1', '2', '2', '2017-01-24', 2);
