Step 1: logon to MySQL with the admin ID and run the script Authentication/MySQLData/create_mysql_script_security
This will setup Security DB and create the needed tables

Step 2: run GenerateUsers/AdminConsole from NetBeans (same as other apps in A2). This a very rudimentary console that creates users. It doesn't have much error checking.
Input username, password and user access type. Username, encrypted password and salt assocated with that encryption will be stored in the MySQL table. No clear text passwords are stored.
Example: create 'user1', password 'user1' and user access type 'CRUD'.
All the users are stored in MySQL DB security, table SecuredUsers

Step 3: run AuthenticationApp from NetBeans (same as other apps in A2). Logon with the user and password from Step 2. If successfully logged on, that session is considered authenticated. User logon/logon/failed logon will be logged into MySQL DB security, table LogonOff. If any other Class needs to reference Session object:
Session mySession = new Session();
Then simply reference mySession.isLoggedOn() to check whether that Session is authenticated or not or use any other method to obtain any other Session information (e.g. authorization level)