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