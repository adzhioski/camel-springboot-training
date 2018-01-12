This project consists of several examples of Apache Camel routes in order to get 
familiar with building camel routes working in Spring Boot environment,
using Java DSL, get familiar with EIPs, etc.  

Used Endpoints:
timer://
file://
jpa://
activemq://
direct://

Used EIPs:
Splitter,
Aggregator,
Content enricher

Prerequisites:
Running Apache ActiveMQ broker on the local machine.

Running the project:
mvn spring-boot:run
If you are using STS IDE, Run as -> Spring boot app