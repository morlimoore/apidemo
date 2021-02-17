USE master
GO

IF NOT EXISTS(SELECT
                *
              FROM sys.server_principals
              WHERE name = N'stored_procedure')
  BEGIN
    CREATE LOGIN stored_procedure WITH PASSWORD = N'vikkidchamp', DEFAULT_DATABASE = stored_procedures_demo, DEFAULT_LANGUAGE =[us_english], CHECK_EXPIRATION = OFF, CHECK_POLICY = OFF
  END
GO