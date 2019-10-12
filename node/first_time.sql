CREATE TABLE appointments (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL
);

INSERT INTO appointments VALUES (
    0, 'void', '2019-10-20', '20:40:28'
);

INSERT INTO appointments 
    (name, date, time) 
VALUES
    ('void', '2087-3-5', '2:5:3');