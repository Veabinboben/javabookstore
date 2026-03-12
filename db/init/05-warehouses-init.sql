INSERT INTO warehouses (adress, city_id) 
VALUES
    ('1 Lenina St.', (SELECT id FROM cities WHERE name = 'Minsk')),
    ('2 Sovetskaya Ave.', (SELECT id FROM cities WHERE name = 'Minsk')),
    ('Parkovaya 5', (SELECT id FROM cities WHERE name = 'Zhlobin')),
    ('Industrialnaya 12', (SELECT id FROM cities WHERE name = 'Zhlobin')),
    ('Pridneprovskaya 3', (SELECT id FROM cities WHERE name = 'Ghomel')),
    ('Railway Sq. 7', (SELECT id FROM cities WHERE name = 'Ghomel')),
    ('Tverskaya 20', (SELECT id FROM cities WHERE name = 'Moscow')),
    ('Sadovaya 8', (SELECT id FROM cities WHERE name = 'Moscow')),
    ('Lermontova 14', (SELECT id FROM cities WHERE name = 'Vitebsk')),
    ('Sovetskaya 1', (SELECT id FROM cities WHERE name = 'Vitebsk'));