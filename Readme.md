# Organisational Api
## By **Nathan Ng'ethe** Version: 1.0.

## Description
This is an api that primarily offers access to news to a department and general news in an organisation. To make things better, it gives you the ability to query for all the users and the departments associated with them.

## Features
* Add general as well as departmental news.
* Add a department.
* Add a user to a department.


## Behaviour Driven Development(BDD) using json
| Project should handle : | Input example :     | Output example : |
| :------------- | :------------- | :-------------         |
| Adding news       | Enter data in json form for the news       | News is added to the news table    |
| Add a department       | Enter the department data in the required route in json form      | The department info is added to the table of departments  |
| Add a user       | Enter user data in json form to the required route       | User added to users table    |
| Retrieve all general news   | Request for the news info on the route `/generalNews`        | All general news is displayed    |
| Retrieve all department news   | Make a request on the route `/departments/1/depNews` where '1' is the department id | All news for that department is displayed    |
| Retrieve all users  | Make a get request on the route `/users`   | All users are displayed   |
| Retrieve all departments a user belongs to | Make a get request on `users/1/departments` where '1' is the user id | All departments that a user belongs to are displayed |


## Database Setup
### Installation
1. Clone the repo `https://github.com/lendilai/Organisational-API.git`
2. CD into the folder `cd api_dev`

### Setup instructions for the database
1. First make sure that you have postgres installed.
   If not, run:
   ```bash

   $ sudo apt update
   $ sudo apt install postgresql postgresql-contrib

    ```
2. Run the following commands in the Organisational-Api folder-

```bash

$ psql //Launches Postgres
# CREATE DATABASE api_dev;
# \c api_dev; 
# CREATE TABLE IF NOT EXISTS departments(id int PRIMARY KEY auto_increment, name VARCHAR, description VARCHAR, user_no int);
# CREATE TABLE IF NOT EXISTS users(id int PRIMARY KEY auto_increment, name VARCHAR, position VARCHAR, url VARCHAR);
# CREATE TABLE IF NOT EXISTS news(id int PRIMARY KEY auto_increment, title VARCHAR, content VARCHAR, importance VARCHAR, type VARCHAR, departmentId int);
# CREATE TABLE IF NOT EXISTS departments_users(id int PRIMARY KEY auto_increment, department_id INTEGER, user_id INTEGER);
# CREATE DATABASE api_dev_test WITH TEMPLATE api_dev;

```

## Technologies used
- [Java](https://www.java.com/) - To make sure the method behind the functionality work ad planned and that the the api can be successfully used.


## Known bugs
As of now there are no known bugs. If you come across any bugs feel free to contact me.

## Contact Information
You can contact me via my gmail account at ngethenan768@gmail.com.

## License
The application is under an [MIT license](https://github.com/lendilai/Organisational-API/blob/master/License).