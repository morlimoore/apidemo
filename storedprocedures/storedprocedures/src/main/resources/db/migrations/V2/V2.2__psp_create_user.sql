IF EXISTS(SELECT *
          FROM sys.objects
          WHERE object_id = OBJECT_ID(N'create_user') AND type IN (N'P', N'PC'))
  DROP PROCEDURE create_user
GO

CREATE PROCEDURE create_user
    @id                       BIGINT OUTPUT,
    @age		              INT,
    @email	                  VARCHAR(255),
    @first_name	              VARCHAR(255)  = NULL,
    @last_name	              VARCHAR(255)  = NULL
AS
  SET NOCOUNT ON

INSERT INTO users	(age, email,
                    first_name, last_name)
VALUES              (@age, @email,
                    @first_name, @last_name)

  SELECT @id = SCOPE_IDENTITY();

  RETURN @@Error
GO

GRANT EXECUTE ON create_user TO stored_procedures_demo_app
GO