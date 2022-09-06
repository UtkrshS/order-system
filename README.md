# order-system

Retail Order System

Design and implement an order processing API using Spring boot with any NoSQL data source of your choice.

• Push the order details to a message queue and also create a record in the data source with the order status PLACED.
• Use a separate consumer which will process the order message and update the data source with its order status as PROCESSED.
• Use Get API to fetch the order status. Secure the GET API.

This project is composed of below:

Programming Lang: Java, SpringBoot
Testing tool: JUnit & Mockito
NoSQL Database: Mongo DB
Messaging Queue: Apache Kafka
Security: Basic Authetication Mechanism using Spring-Security
Postman: To organize the API requests
Version Control: GIT
