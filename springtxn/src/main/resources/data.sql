DROP TABLE IF EXISTS student;

CREATE TABLE student (
  sid INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250),
  address VARCHAR(250),
  class VARCHAR(50),
  active VARCHAR(1),
  title VARCHAR(50)
);
 