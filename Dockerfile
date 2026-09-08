# ---- Stage 1: build the Angular front end and package the Spring back end ----
FROM maven:3.8-eclipse-temurin-17 AS build

# Node is required because the Maven build runs "ng build" for the Angular UI
RUN apt-get update \
    && apt-get install -y --no-install-recommends curl ca-certificates gnupg \
    && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y --no-install-recommends nodejs \
    && npm install -g @angular/cli@14.1.3 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /build

# Install the front-end dependencies first so Docker can cache this layer
COPY src/main/UI/package.json src/main/UI/package-lock.json ./src/main/UI/
RUN cd src/main/UI && npm install

# Copy the rest of the source and build the executable jar
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

# ---- Stage 2: run the packaged application ----
FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=build /build/target/D387_sample_code-0.0.2-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]