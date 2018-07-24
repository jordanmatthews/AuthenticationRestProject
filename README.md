# AuthenticationRestProject
A Outh2 starting point for Java Spring REST API.

I wanted to make it easy to pull down this project, make a few small changes and then not have to do any extra work to start building an API as the authentication piece is already implemented. All you need to do is add your own controllers/services etc. to the project.

Spring Boot Version: 1.5.11

Spring Security OAuth2 Version: 2.2.0

## Getting Started

1. Pull down the project
2. Open file in IDE
3. Goto project structure and rename project
4. Edit configurations and rename
5. Rename package under com/server
6. Rename main class
7. Setup new mysql db / or use alternative
8. Run the following against the db:

   ```sql
   CREATE TABLE SPRING_SESSION (
   	PRIMARY_ID INT NOT NULL AUTO_INCREMENT,
   	SESSION_ID CHAR(36) NOT NULL,
   	CREATION_TIME BIGINT NOT NULL,
   	LAST_ACCESS_TIME BIGINT NOT NULL,
   	MAX_INACTIVE_INTERVAL INT NOT NULL,
   	EXPIRY_TIME BIGINT NOT NULL,
   	PRINCIPAL_NAME VARCHAR(100),
   	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
   ) ENGINE=InnoDB;

   CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
   CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
   CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

   CREATE TABLE SPRING_SESSION_ATTRIBUTES (
   	SESSION_PRIMARY_ID INT NOT NULL,
   	ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
   	ATTRIBUTE_BYTES BLOB NOT NULL,
   	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
   	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
   ) ENGINE=InnoDB;

   CREATE INDEX SPRING_SESSION_ATTRIBUTES_IX1 ON SPRING_SESSION_ATTRIBUTES (SESSION_PRIMARY_ID);

   commit;
   ```
9. Update application.properties

