FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN ./mvnw package -DskipTests

CMD ["java", "-jar", "target/Home_Spring_Gwangju_04___-0.0.1-SNAPSHOT.jar"]
