IF NOT EXISTS(SELECT *
              FROM sys.objects
              WHERE object_id = OBJECT_ID(N'users') AND type IN (N'U'))
  CREATE TABLE users
  (
    id                  BIGINT IDENTITY (1, 1) PRIMARY KEY,
    age					INTEGER NOT NULL,
	first_name			VARCHAR(40),
	last_name			VARCHAR(40),
    email               VARCHAR(70)
  )
GO

GRANT SELECT, INSERT, UPDATE, DELETE ON users TO stored_procedures_demo_app
GO

