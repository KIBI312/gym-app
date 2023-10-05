INSERT INTO users(id, first_name, last_name, username, password, is_active)
    VALUES(1, 'John', 'Doe', 'johndoe', 'password123', true);
INSERT INTO trainee(id, date_of_birth, address, user_id)
    VALUES(2, TO_DATE('20-04-2000', 'DD-MM-YYYY'), 'SomeStreet', 1);
INSERT INTO training_type(id, name)
    VALUES(10, 'fitness'),
    (11, 'yoga'),
    (12, 'zumba'),
    (13, 'stretching'),
    (14, 'resistance');
ALTER SEQUENCE hibernate_sequence RESTART WITH 100;
