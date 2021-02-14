USE master
GO

IF NOT EXISTS(SELECT
                name
              FROM sysdatabases
              WHERE name = N'stored_procedures_demo')
  BEGIN
    CREATE DATABASE stored_procedures_demo
  END
GO

USE master
GO

IF NOT EXISTS(SELECT
                *
              FROM sys.server_principals
              WHERE name = N'stored_procedures_demo')
  BEGIN
    CREATE LOGIN stored_procedures_demo WITH PASSWORD = N'vikkidchamp', DEFAULT_DATABASE = stored_procedures_demo, DEFAULT_LANGUAGE =[us_english], CHECK_EXPIRATION = OFF, CHECK_POLICY = OFF
  END
GO

USE stored_procedures_demo
GO

IF NOT EXISTS(SELECT
                *
              FROM sys.database_principals
              WHERE (name = N'stored_procedures_demo_app') AND (type = N'R'))
  BEGIN
    CREATE ROLE stored_procedures_demo_app
    CREATE USER stored_procedures_demo FOR LOGIN stored_procedures_demo
    ALTER ROLE stored_procedures_demo_app ADD MEMBER stored_procedures_demo
  END
GO

USE stored_procedures_demo
GO

IF NOT EXISTS(SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'schema_migrations') AND type IN (N'U'))
  CREATE TABLE schema_migrations
  (
    id INT IDENTITY(1, 1) PRIMARY KEY,
    migration VARCHAR(250) NULL
  )
GO