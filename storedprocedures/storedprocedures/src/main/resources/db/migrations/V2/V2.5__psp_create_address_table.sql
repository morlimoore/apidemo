USE [stored_procedures_demo]
GO

IF NOT EXISTS(SELECT *
              FROM sys.objects
              WHERE object_id = OBJECT_ID(N'address') AND type IN (N'U'))

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[address](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[address] [varchar](255) NULL,
	[type] [varchar](255) NULL,
	[owner_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[address]  WITH CHECK ADD  CONSTRAINT [FK939d96i1oxdju0ny41700u74q] FOREIGN KEY([owner_id])
REFERENCES [dbo].[users] ([id])
GO

ALTER TABLE [dbo].[address] CHECK CONSTRAINT [FK939d96i1oxdju0ny41700u74q]
GO

GRANT SELECT, INSERT, UPDATE, DELETE ON address TO stored_procedures_demo_app
GO