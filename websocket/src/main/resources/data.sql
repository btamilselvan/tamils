DROP TABLE IF EXISTS users;
 
CREATE TABLE users (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250),
  last_name VARCHAR(250),
  email VARCHAR(50) NOT NULL,
  password VARCHAR(250)
);
 
INSERT INTO users (id, first_name, last_name, email, password) VALUES
  (10001, 'T1', 'T1', 't1@tamils.rocks', 'T1Password'),
  (10002, 'T2', 'T2', 't2@tamils.rocks', 'T2Password'),
  (10003, 'T3', '3', 't3@tamils.rocks', 'T3Password');
