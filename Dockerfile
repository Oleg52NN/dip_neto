FROM openjdk:19-jdk-oracle
ADD src/main/resources/application.properties src/main/resources/application.properties
ADD target/dip_neto-0.0.1-SNAPSHOT.jar dip_neto.jar
CMD ["java", "-jar", "dip_neto.jar"]