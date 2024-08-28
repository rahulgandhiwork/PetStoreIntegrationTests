# Petstore

Automation suite is for Swagger Petstore. 

Framework and Reporting
----------------------
Tests are automated with a modular framework for Rest Assured API and uses Maven for dependency management.
It uses TestNG for test case management and running test suite. 
It has Extent reports to generate HTML reports for test run. 
For Test data it uses java faker library to generate random fake data.
	
Automated Module:
----------------------
**Pet Endpoint**
- Create a new Pet
- Fetch List of Pet by status
- Fetch a Pet by Pet ID
- Updating Pet
- Updating Pet by form data
- Uploading Image of Pet
- Deleting a Pet

Instructions to run Petstore Test Suite:
----------------------
- Clone this project from feature branch (pet/Rahul-CBATest)
-  PreRequisite - Maven (mvn) installed in system.
Run In IDE
----------------------
- Import cloned project as Maven project in IntelliJ or Eclipse IDE:
- Navigate to project folder from terminal window 
- Run command -> ``` mvn clean test ```

Run through TestNG
----------------------
- In IDE look for testng.xml file at project root
- Right click on file and Click Run

Reporting
----------------------
1. In project root folder, navigate to 'reports' folder
2. Look for following Extent Report file -> ``` PetStoreApiAutomationReport.html```
3. Double click and open in browser to verify the Test Results