ALTER TABLE [GDP] ADD FOREIGN KEY ([country_id]) REFERENCES [countries] ([id])
GO

ALTER TABLE [GDP] ADD FOREIGN KEY ([year_id]) REFERENCES [YearEnum] ([id])
GO