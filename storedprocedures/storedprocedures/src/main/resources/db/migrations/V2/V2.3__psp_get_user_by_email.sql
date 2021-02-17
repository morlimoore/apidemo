IF EXISTS(SELECT *
          FROM sys.objects
          WHERE object_id = OBJECT_ID(N'get_user_by_email') AND type IN (N'P', N'PC'))
    DROP PROCEDURE get_user_by_email
GO

CREATE PROCEDURE get_user_by_email
    @email VARCHAR(255)
AS
    SET NOCOUNT ON

    SELECT * FROM users WHERE LTRIM(RTRIM(email)) = @email

    RETURN @@Error
GO

GRANT EXECUTE ON get_user_by_email TO stored_procedures_demo_app
GO