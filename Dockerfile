# Use the official gradle image to create a build artifact.
FROM gradle:jdk17 AS builder

# set work directory
WORKDIR /app

# copy project files
COPY . .

# build application
RUN gradle build -x test
# Use OpenJDK 17 as the base image for the final image.
FROM openjdk:17-oracle
# set work directory
WORKDIR /app

# copy build artifact from the 'builder' stage
COPY --from=builder /app/build/libs/*.jar ./app.jar

# set the startup command to run your binary, sleep timer is needed to ensure db init script is executed before app starts
ENTRYPOINT ["sh", "-c", "sleep 5 && java -jar ./app.jar"]