# Java-Service-Azure-SMS-Email

### What is it?
This is an application to provide PIN validation to the user, it will allow controll the PIN expire time, the PIN log and it is very flexible to adapt on your application, you just make sure that you will use the [Azure Communication Services](https://azure.microsoft.com/en/products/communication-services/), because the application logic was created involving these Azure Services for SMS and EMAIL.

_The application uses a Software Architecture based on Clean Architecture with layers like controllers, services, dtos, repositories (dao) and entities (domains)._

### How to run
This API consumes the Azure Communication Service to send Email and SMS for users with a validation PIN storage in a database. You could test this code with Java, Maven, Azure Account and Maria DB. You will need:

1. Set-up your database configuration on application.properties file
2. Add the service Azure Communication and get a email and phone number. After it just add the service key and contacts create before on application.properties file
3. Open the code in a IDE like Eclipse/IntelliJ or other that you prefrer and download the maven dependencies (it is listed in pom.xml)
4. Just run the application, it will use the flyway (db/migration) sqls to create the database tables.

