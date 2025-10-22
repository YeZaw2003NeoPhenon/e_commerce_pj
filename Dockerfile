FROM openjdk:21-jdk-slim
COPY target/e_commerce_app.jar e_commerce_app.jar
CMD ["java", "-jar", "e_commerce_app.jar"]