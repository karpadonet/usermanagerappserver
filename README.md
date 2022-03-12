# usermanagerappserver
A spring boot java server for a user management web application.

Notes:
The application is using a MySQL5 (5.7) database. The assumption is that the database exists. I created it manually via the MySQL client. The database's name is userdatabase with the table titled "users".

Because I was not required to support registration of users in the web application, the users were not added by sending add requests from the Angular application, but rather I added the users semi-manually via POSTMAN. The server itself does support adding users via "GET" http://localhost:8080/user/add
