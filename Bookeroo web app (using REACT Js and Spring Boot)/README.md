# RMIT SEPT 2021 Major Project

# Group 6 - Friday 3:30-5:30

## Members
* Dhruv Bachani     - s3808392
* Aditya Vadgama    - s3845898
* Linda Vu          - s3842580
* Harshita Kumar    - s3845841 
* David Nguyen      - s3843540

## Records

* Github repository: https://github.com/DhruvBachani/Bookeroo/
* Jira Board: https://bookeroogroup6.atlassian.net/jira/software/projects/BG6/boards/1/backlog
* Google Docs: https://drive.google.com/drive/folders/1_-937tvipAGywIdPHBh7YxhbDMPe62FV?usp=sharing
	
## Code documentation - Release 0.1.0 - 21/09/2021
* Users can register and login with the neccessary data needed to run the book website.
* Admins can approve or decline publishers and shop owners.
* Books with the all neccessary data can now be added/deleted/updated.
* Sellers can now be added and post an ad to sell their books.
* Reviews can be added from the backend. 
  
To run the application locally:
First ensure that you have:
- An IDE preferably IntelliJ to re-enact this readme as close as possible, 
- NodeJS (https://nodejs.org/en/download/)
- Configure JAVA_HOME onto your local machine (Java 8 or 11) - to check, go to command prompt and type echo %JAVA_HOME% and javac -version. 
- 	In the case of Windows 10, to do this, we go to Windows searchbar -> Advanced System Setting -> System Properties Window -> Environment Variables -> New System Variable
- 	From there you enter the name JAVA_HOME and the variable value is your JDK Directory.
- 	Once done, edit your System Path Variable to add new "%JAVA_HOME%\bin" and check again on the command prompt.

Once this is all downloaded:
1. Open the project on IntelliJ and enter in the terminal, for each and every microservice:
	- cd Backend/[microservice]
	- ./mvnw package
	- java -jar target/[jar file e.g. loginmicroservices-0.0.1.jar]
2. In a second terminal, type:
	- cd FrontEnd/myfirstapp
	- npm install
	- npm start

Testing:
- All tests are conducted on the testing branch - which is exactly the same as the development/master branch (aside from the tests).
- We believe it's best to leave the testing to this branch to avoid having tests in our production code. 
- All microservices have unit tests that are configured to run at a './mvnw package' build (or './mvnw test' to just test the code).
- For frontend testings, all tests are configured to run at 'npm test' (might need to 'npm install again), once the test terminal appears, press 'a' to run all tests and then 'u' to update the snapshots.
- All tests are successful.
