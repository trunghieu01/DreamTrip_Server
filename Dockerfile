FROM openjdk:17
EXPOSE 8080
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Copy the project source
COPY ./src ./src
COPY ./pom.xml ./pom.xml

ENTRYPOINT ["java","-jar","target/server-0.0.1-SNAPSHOT.jar"]
