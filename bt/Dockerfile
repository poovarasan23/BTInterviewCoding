FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y git maven curl netcat

WORKDIR /app

RUN git clone https://github.com/poovarasan23/BTInterviewCoding.git .

WORKDIR /app/bt

RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/bt-0.0.1-SNAPSHOT.jar"]
