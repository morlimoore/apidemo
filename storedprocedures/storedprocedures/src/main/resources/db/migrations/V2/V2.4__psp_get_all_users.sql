IF EXISTS(SELECT *
          FROM sys.objects
          WHERE object_id = OBJECT_ID(N'get_all_users') AND type IN (N'P', N'PC'))
  DROP PROCEDURE get_all_users
GO

CREATE PROCEDURE [dbo].[get_all_users]
	@page_num   INT,
    @page_size  INT = NULL
AS
  SET NOCOUNT ON

  IF @page_size IS NULL
  BEGIN
	SELECT COUNT(*) AS count
	FROM dbo.users

	SELECT * FROM dbo.users
	ORDER BY email
  END
  ELSE
  BEGIN
	SELECT COUNT(*) AS count
	FROM dbo.users

	SET @page_num = ABS(@page_num)
    SET @page_size = ABS(@page_size)
    IF @page_num < 1 SET @page_num = 1
    IF @page_size < 1 SET @page_size = 1

    DECLARE @offset INT
    SET @offset = (@page_num - 1) * @page_size

	SELECT * FROM dbo.users
	ORDER BY email
	OFFSET @offset ROWS FETCH NEXT @page_size ROWS ONLY
    END

	RETURN @@Error
GO

GRANT EXECUTE ON get_all_users TO stored_procedures_demo_app
GO