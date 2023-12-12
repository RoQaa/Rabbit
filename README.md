# RabbitGame.Rabbit

RabbitGame.Rabbit is a 2D game developed using Java Swing and Open GL as part of the CS_304 project.

## Database Setup

The game utilizes Oracle  11g Express Edittion as its database.

### Instructions:

1. **Database Configuration:**
   - Make sure you have Oracle  11g Express Edittion installed.
   - Create a table named `hr.score` with the following columns: `USER_NAME` and `SCORE`.

## Build Game

Follow these steps to set up and build the game:

### Prerequisites:

- Clone the repository:
  ```bash
  git clone https://github.com/your-username/RabbitGame.Rabbit.git

### Configuration:
1.Open `InterFace Constant.java`
2.Update the database connection details:
    .Set your Oracle 11g Express Edittion database username.
    .Set your Oracle 11g Express Edittion password.
### Database Table:
Create a table named hr.score in your Oracle  11g Express Edittion.
Insert initial values into the table with the columns `USER_NAME` and `SCORE`.

### Example SQL:
   
   CREATE TABLE hr.score (
       USER_NAME VARCHAR2(255),
       SCORE NUMBER
   );

INSERT INTO hr.score (USER_NAME, SCORE) VALUES ('player1', 0);
INSERT INTO hr.score (USER_NAME, SCORE) VALUES ('player2', 0);
-- Add more entries as needed
!---Now, you're ready to run and enjoy RabbitGame.Rabbit!---!
### Contributors:
   - Team Members
     ```bash
      1-Farouk Adel
      2-Manar Adel
      3-Mahmoud Wafdy
      4- AbdellFattah Hesham

### License:
This project is licensed under the MIT License.
Feel free to customize the instructions and add more details as needed for your project.

