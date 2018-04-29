CREATE TABLE [dbo].[User] (
    [idUser]    INT            IDENTITY (1, 1) NOT NULL,
    [email]     NVARCHAR (50)  NULL,
    [password]  NVARCHAR (MAX) NULL,
    [phone]     INT            NULL,
    [TokenUser] NVARCHAR (MAX) NULL,
    CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED ([idUser] ASC)
);