# Vote Management  Application
	This application is a REST API to take control assembly voting sessions.

Functional requirements
 ```bash
    Management a Assembly voting sessiong with votes just with "SIM" and "NÃO" values.
 ```   
## Technologies(Frameworks and Plugins)
This project I have developed using Intellij IDE and these technologies and frameworks:

	-Java 11
    -Springboot,
    -Gradle,
    -H2 (memory database),
    -Swagger,
    -Lombok,
    -Actuator,
    -Docker,
	-PMD Gradle plugin,
	-Checkstyle Gradle plugin,
    -Spring rest.
    -Spring Kafka
    -Mysql database

## About project	
	This project is formed per one SpringBoot Application.
        Notes about application:
            -This application has two spring profiles:
                * mysql => To integrate with a Mysql database, you need to configure the conncection in application-mysql.properties file;
                * test  => This files just should be used during the end to end test, not use it to run the application outside tests context.
             If you run the application using none profile, the application will use the H2 database, I mean a memory database;     
            -It is configured to listen 8090 port (you can change it in application.properties file);
            -In this Swagger url: http://localhost:8090/vote-management/swagger-ui.html you can check all documentation about rest endpoints using an internet browser;
			-Basic the API has these endpoints:
				
				Post - http://localhost:8090/vote-management/v1/associate - Create associate on database;
				Get - http://localhost:8090/vote-management/v1/associate - Retrieve all associetes registries on database;
				
				Post - http://localhost:8090/vote-management/v1/assembly - Create new assembly on database;
				Post - http://localhost:8090/vote-management/v1/assembly/openVotingSession - Open a voting session for an assembly;
				Post - http://localhost:8090/vote-management/v1/assembly/vote - Vote in assembly voting session;
				
				Get - http://localhost:8090/vote-management/v1/assembly - Retrieve all assemblies registries on database; (This endpoint is just to develping process, I mean it would be cutted in production environment)
				Get - http://localhost:8090/vote-management/v1/assembly/{associateCpf}/{assemblyName} - Retrieve an specific assembly registry by name on database.
				
            -You can check if the application is online using this actuator url in internet browser:
                Get - http://localhost:8090/vote-management/health
			-There are unit tests for service layer;
			-I have used the URI Versioning strategy to versioning the api endpoints;
			-There are end to end tests for API that simulate the complete flows:
				These tests are configured to use H2 database.
			-This project is using PMD (https://maven.apache.org/plugins/maven-pmd-plugin/) and Checkstyle (https://maven.apache.org/plugins/maven-checkstyle-plugin/) plugins to keep a good quality in -the code.
			-During every build process, these process are executed:
				Execute Checkstyle verification
				Execute PMD verification
				Execute integration tests
				Execute unit tests for service layer	
				build jar file

## Run 
To run application without an IDE you need to follow these steps:
```bash
-Execute the Gradle build;
-To running this application you can running it without 
	 a profile, in this way the application will use H2 database,
	 if you want use Mysql database you just need to choose Mysql profile
	 (application-mysql.properties), to do that you need to configure the
	 application to use this profile (spring.profiles.active=mysql)
	 and configure the database connection in application-mysql.properties file;
	 -The application can create the database table structure every time that the application are started (spring.jpa.hibernate.ddl-auto=update), it is only configured in mysql profile.
```
Inside Intellij IDE:
```bash
-Import the project;
-Execute Gradle import;
-Check Enable annotation processing field in Intellij options
-To use Mysql database you need to configure the connection in application-mysql.properties file and run the application using mysql profile (set an environment variable with name spring.profiles.active=mysql inside Intellij configuration). If you prefer use  H2 you can pass to next step;
-Start application using the Intellij IDE.
```

Docker, you need follow these steps:
 ```bash
	-Build the application,
	-Build docker image with this command: docker build -t vote-management . or docker build -t vote-management . (you need to run this command in root project that you want to *create the docker image);
    -Execute the docker-compose file with this command: docker-compose up (you need to run this command in root project). You can -check if applications are running using the actuator feature, to do do that you need to access this url: http://localhost:8090/vote-management/health;
    -If you want to use H2 database instead of Mysql usind docker you need to remove SPRING_PROFILES_ACTIVE configuration in docker-compose file.
```

## Important notes about the project
1) If you want use kafka integration, you need to configure the connection in application.properties file
    
    Example:
    
        spring.kafka.producer.bootstrap-servers=localhost:9092
        assembly.voting.topic=assemblyVoting
        publishMessageOnKafka=true
        
    And you need to create a topic in your kafka with name:
        
        assemblyVoting    
        
2) To connect with verify cpf service in Heroku ("https://user-info.herokuapp.com/users/), you need to change this this property to true:
    
        validateCpf=true
   
   PS: Currently this service is offline in Heroku and I could not tested it, but the integration with that is implemented.       
 
3) Some services of this API you need a valid cpf to use, I mean, you need to have a associate with this CPF inside database (just do an insert direct in Associate table :P) 

If you have questions, please feel free to contact me.