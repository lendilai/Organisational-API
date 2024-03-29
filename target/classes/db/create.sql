CREATE DATABASE api_dev;
\c api_dev;
 CREATE TABLE IF NOT EXISTS departments(id int PRIMARY KEY auto_increment, name VARCHAR, description VARCHAR, user_no int);
 CREATE TABLE IF NOT EXISTS users(id int PRIMARY KEY auto_increment, name VARCHAR, position VARCHAR, url VARCHAR);
 CREATE TABLE IF NOT EXISTS news(id int PRIMARY KEY auto_increment, title VARCHAR, content VARCHAR, importance VARCHAR, type VARCHAR, departmentId int);
 CREATE TABLE IF NOT EXISTS departments_users(id int PRIMARY KEY auto_increment, department_id INTEGER, user_id INTEGER);
CREATE DATABASE api_dev_test WITH TEMPLATE api_dev;