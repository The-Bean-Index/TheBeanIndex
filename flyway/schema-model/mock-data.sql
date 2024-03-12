
-- Inserting the years
BEGIN TRY
	DECLARE @counter INT = 2015;

	BEGIN TRANSACTION

	WHILE @counter <= 2024
	BEGIN
		INSERT INTO YearEnum (year) VALUES (@counter)
		SET @counter = @counter + 1;
	END;

	COMMIT
END TRY
BEGIN CATCH
	ROLLBACK
END CATCH

-- Inserting the countries
BEGIN TRY
	BEGIN TRANSACTION

	INSERT INTO countries (name) VALUES ('United States')
	INSERT INTO countries (name) VALUES ('China')
	INSERT INTO countries (name) VALUES ('Japan')
	INSERT INTO countries (name) VALUES ('Germany')
	INSERT INTO countries (name) VALUES ('France')
	INSERT INTO countries (name) VALUES ('United Kingdom')
	INSERT INTO countries (name) VALUES ('Brazil')
	INSERT INTO countries (name) VALUES ('Italy')
	INSERT INTO countries (name) VALUES ('India')
	INSERT INTO countries (name) VALUES ('Russia')

	COMMIT
END TRY
BEGIN CATCH
	ROLLBACK
END CATCH

-- Inserting the bean names
BEGIN TRY
	BEGIN TRANSACTION

	INSERT INTO beans (name, beanPrice) VALUES ('Cannellini Beans', 1225.257416912456)
	INSERT INTO beans (name, beanPrice) VALUES ('Chickpeas (Garbanzo Beans)', 1259.7624585330327)
	INSERT INTO beans (name, beanPrice) VALUES ('Kidney Beans', 1020.1676694882135)
	INSERT INTO beans (name, beanPrice) VALUES ('Adzuki Beans', 941.1028440813956)
	INSERT INTO beans (name, beanPrice) VALUES ('Mung Beans', 607.403267617667)
	INSERT INTO beans (name, beanPrice) VALUES ('Navy Beans (Pea Beans)', 629.3441158453605)
	INSERT INTO beans (name, beanPrice) VALUES ('Pinto Beans', 1116.982926906133)
	INSERT INTO beans (name, beanPrice) VALUES ('Fava Beans', 1149.6384713697698)
	INSERT INTO beans (name, beanPrice) VALUES ('Soybeans', 959.361270412003)


	COMMIT
END TRY
BEGIN CATCH
	ROLLBACK
END CATCH

-- Inserting the D
BEGIN TRY
	BEGIN TRANSACTION

	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (1, 1, 15048975)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (1, 2, 15599725)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (1, 3, 16253950)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (1, 4, 16843225)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (1, 5, 17550675)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (1, 6, 18206025)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (1, 7, 18695100)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (1, 8, 19477350)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (1, 9, 20533075)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (1, 10, 2138095)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (2, 1, 6033830)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (2, 2, 7492212)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (2, 3, 8539584)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (2, 4, 9624928)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (2, 5, 10524241)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (2, 6, 11113508)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (2, 7, 11226897)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (2, 8, 12265327)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (2, 9, 13841812)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (2, 10, 1434060)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (3, 1, 5759072)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (3, 2, 6233148)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (3, 3, 6272362)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (3, 4, 5212328)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (3, 5, 4896995)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (3, 6, 4444931)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (3, 7, 5003678)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (3, 8, 4930837)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (3, 9, 5040881)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (3, 10, 5117995)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (4, 1, 3402444)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (4, 2, 3748655)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (4, 3, 3529377)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (4, 4, 3733859)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (4, 5, 3890095)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (4, 6, 3357926)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (4, 7, 3468896)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (4, 8, 3689547)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (4, 9, 3976246)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (4, 10, 3889607)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (5, 1, 2647348)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (5, 2, 2864653)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (5, 3, 2685371)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (5, 4, 2811919)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (5, 5, 2856701)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (5, 6, 2439436)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (5, 7, 2472282)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (5, 8, 2594235)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (5, 9, 2792223)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (5, 10, 2729171)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (6, 1, 2493844)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (6, 2, 2667317)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (6, 3, 2706822)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (6, 4, 2788128)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (6, 5, 3066819)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (6, 6, 2935506)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (6, 7, 2709678)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (6, 8, 2685637)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (6, 9, 2881845)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (6, 10, 2858728)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (7, 1, 2208704)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (7, 2, 2614027)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (7, 3, 2464053)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (7, 4, 2471718)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (7, 5, 2456055)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (7, 6, 1800046)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (7, 7, 1796622)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (7, 8, 2063519)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (7, 9, 1916934)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (7, 10, 1873286)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (8, 1, 2137845)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (8, 2, 2294591)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (8, 3, 2088280)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (8, 4, 2141954)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (8, 5, 2162567)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (8, 6, 1836824)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (8, 7, 1876554)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (8, 8, 1961104)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (8, 9, 2092881)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (8, 10, 2011525)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (9, 1, 1708460)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (9, 2, 1823052)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (9, 3, 1827637)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (9, 4, 1856721)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (9, 5, 2039127)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (9, 6, 2103588)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (9, 7, 2294797)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (9, 8, 2651474)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (9, 9, 2702930)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (9, 10, 2835606)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (10, 1, 1633111)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (10, 2, 2046621)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (10, 3, 2191484)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (10, 4, 2288428)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (10, 5, 2048836)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (10, 6, 1356704)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (10, 7, 1280648)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (10, 8, 1575140)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (10, 9, 1653006)
	INSERT INTO GDP (country_id, year_id, gdp_amount) VALUES (10, 10, 1695724)

	COMMIT
END TRY
BEGIN CATCH
	ROLLBACK
END CATCH


SELECT * FROM countries
SELECT * FROM beans
SELECT * FROM YearEnum
SELECT * FROM GDP
