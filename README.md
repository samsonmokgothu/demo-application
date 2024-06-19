
# Code Structure

## bank-common
   This is the library that contains domain models that cuts accross multiple projects
## account-api
   Spring boot restful service that exposes withdrawal endpoint
## sns-api
   Spring boot restful service that exposes publish endpoint. 

# Approach
*  Problem with current solution is that it is following monolithic architecture.
   There is no seperation of concerns as both account balance, withrawal and sending of notification happens in one method.
   No transactions defined and can cause the system to be incosistent should one of the steps fail.
   
 * I have decided to use domain driven design architecture with three defined contexts ( account, sns, and bank-common context).
   Account context is responsible for account balance and withrawal services. Sns context is responsible for sending messages to customers.
   Both account and sns contexts share common bank-common context, which is responsible for defining common data models.

 * The two contexts of account and sns are implemented using microservice architecture. The two are independent of each other.
   This gives flexibility in terms of number of instances that can be spawned. This allows for scalability.
   
 * I have also used 3-tier pattern in account-api project. Controller, Service and Persistence layers. This makes the code readable, maintainable and testable.
   Sns-api uses 2-tier, controller and service.
   
 * Using Spring Configuration functionality, I have created SnsClient bean in sns-api instead of creating new instance whenever a message is send. This should result in improved throughput as the SnsClient 
   instanceis always available to be used whenever needed.
   
 * In account-api, I ensured that balance check and withdrawal are part of one transaction and sending of notification is happening outside of transaction.
   This means should anything fail during notification the overall transaction will still be successful (this needs to be confirmed with business).
   To ensure the customer is eventually notified, the event can be persisted somewhere and resend later when the root cause of notification failure is resolved.
   I have also implemented retry logic in the account-api to retry sending notification 3 times. 

# Building code
   In project root directory 'mvn clean install'.
   For the purpose of testing, the sns-api can switch to test-mode by setting property aws.sns.test-mode=true.
   This setting disables connection to aws sns as this is failing on local. This is false by default.

# Running projects

## Requirements:
This code was developed and tested using below tools and versions.

### Maven
   Apache Maven 3.6.3
### Java
   Java version: 17.0.6
### Spring-Boot
   3.1.1
### Database H2
   2.1.214

## Running account-api
   mvn spring-boot:run

## Running sns-api
### Start in test-mode (aws sns connection off)
   mvn spring-boot:run -Daws.sns.test-mode=true
### Start in live mode (with sns connection on)
   mvn spring-boot:run

# Testing project: account-api
   URL: http://localhost:8081//bank/account/withdraw
   METHOD: POST
   BODY: {
      "accountId": 10000,
      "amount": -1.00
   }

# DATA
   Below data is inserted in H2 database and can be used for testing purposes
   INSERT INTO accounts(id, balance) VALUES(10000, 200.00);
   INSERT INTO accounts(id, balance) VALUES(10001, 12000.00);
   INSERT INTO accounts(id, balance) VALUES(10002, 2000000.00);
   INSERT INTO accounts(id, balance) VALUES(10003, 814500.00);

