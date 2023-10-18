INSERT INTO users(id, username)
    VALUES(1, 'John.Smith');
INSERT INTO trainee(id, user_id)
    VALUES(nextval('trainee_seq'), 1);
