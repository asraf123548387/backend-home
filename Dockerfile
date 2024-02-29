# Use OpenJDK 17 based on slim Debian as the parent image
FROM openjdk:17-alpine

# Set the working directory in the container
WORKDIR /app

# Install curl and tar using apk
RUN apk add --no-cache curl tar

# Copy the application's JAR file into the container
COPY target/Home-0.0.1-SNAPSHOT.jar /app/demo.jar

# Expose port 8080 for the application
EXPOSE 8080

# Download and extract dockerize
RUN curl -L https://github.com/jwilder/dockerize/releases/download/v0.6.1/dockerize-linux-amd64-v0.6.1.tar.gz -o dockerize.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize.tar.gz \
    && rm dockerize.tar.gz

# Run the application
CMD dockerize -wait tcp://db:3306 -timeout 1m java -jar /app/demo.jar

