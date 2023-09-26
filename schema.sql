\c gymDb

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    is_active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS trainee (
    id SERIAL PRIMARY KEY,
    date_of_birth DATE,
    address VARCHAR,
	user_id INTEGER NOT NULL,
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS training_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS trainer (
    id SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL,
	specialization_id INTEGER NOT NULL,
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(specialization_id) REFERENCES training_type(id)
);

CREATE TABLE IF NOT EXISTS training (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    training_date DATE,
    duration INTEGER NOT NULL,
	trainee_id INTEGER NOT NULL,
	trainer_ID INTEGER NOT NULL,
	training_type_id INTEGER NOT NULL,
    FOREIGN KEY(trainee_id) REFERENCES trainee(id),
    FOREIGN KEY(trainer_id) REFERENCES trainer(id),
	FOREIGN KEY(training_type_id) REFERENCES training_type(id)
)