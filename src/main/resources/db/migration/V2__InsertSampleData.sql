-- insert 10 sample license plates
INSERT INTO license_plates (number) VALUES
        ('0000'),
        ('0001'),
        ('0002'),
        ('0003'),
        ('0004'),
        ('0005'),
        ('0006'),
        ('0007'),
        ('0008'),
        ('0009');

-- insert 5 sample cars
INSERT INTO cars (vin, color, model, year_of_manufacture, license_plate_id) VALUES
        ('03FGZ85PDMWE5G9', 'Red', 'Model X', 2020, 1),
        ('T7U9KPNJVHLSHRDB', 'Blue', 'Model S', 2019, 2),
        ('D78EL8D7WEUGT34H3', 'Black', 'Model 3', 2021, 3),
        ('3NC9ACMW2J76D5A6', 'White', 'Model Y', 2022, 4),
        ('A0NKC6TA18AKJ21V', 'Grey', 'Cybertruck', 2023, null);
