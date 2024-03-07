USE master;
GO


DROP DATABASE thebeanindex;
GO

CREATE DATABASE thebeanindex;
GO


USE 
thebeanindex
GO

CREATE TABLE [countries] (
  [id] int PRIMARY KEY,
  [name] nvarchar(255)
)
GO

CREATE TABLE [beans] (
  [id] int PRIMARY KEY,
  [name] nvarchar(255),
  [beanPrice] money
)
GO

CREATE TABLE [YearEnum] (
  [id] int PRIMARY KEY,
  [year] int
)
GO

CREATE TABLE [GDP] (
  [id] int PRIMARY KEY,
  [country_id] int,
  [year_id] int,
  [gdp_amount] money
)
GO

ALTER TABLE [GDP] ADD FOREIGN KEY ([country_id]) REFERENCES [countries] ([id])
GO

ALTER TABLE [GDP] ADD FOREIGN KEY ([year_id]) REFERENCES [YearEnum] ([id])
GO
