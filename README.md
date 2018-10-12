# testWebService

How to start the testWebService application
---

1. Run `mvn package` to build your application
1. Start application with `java -jar target/webservice-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080/contact/1`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
