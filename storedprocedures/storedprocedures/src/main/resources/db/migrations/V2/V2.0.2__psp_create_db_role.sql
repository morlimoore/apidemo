USE stored_procedures_demo
GO

IF NOT EXISTS(SELECT
                *
              FROM sys.database_principals
              WHERE (name = N'stored_procedures_demo_app') AND (type = N'R'))
  BEGIN
    CREATE ROLE stored_procedures_demo_app
    CREATE USER stored_procedure FOR LOGIN stored_procedure
    ALTER ROLE stored_procedures_demo_app ADD MEMBER stored_procedure
  END
GO