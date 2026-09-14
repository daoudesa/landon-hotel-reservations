# Landon Hotel — Reservation Application

A Spring Boot and Angular web application for a hotel in London, Ontario.
Guests search for rooms by check-in and check-out date, see nightly rates, and
reserve a room. The site is localized in English and French, shows the time of a
live presentation across three time zones, and ships as a containerized image.

## What it does

- **Room search and reservation** — a date-range search returns the rooms
  available for that window; reserving one posts a reservation back to the API.
- **Multithreaded localization** — the English and French welcome messages are
  loaded from Java `ResourceBundle` files on two separate named threads, which
  run concurrently and join before the results are returned. Results collect into
  a `CopyOnWriteArrayList` so the concurrent writes are safe.
- **Time zone conversion** — the live presentation time is converted with
  `java.time.ZonedDateTime` and rendered simultaneously in Eastern, Mountain, and
  UTC, so guests in any of the three zones read the correct local start time.
- **Multi-currency display** — room rates are presented in USD, CAD, and EUR
  alongside each available room.
- **Containerized build** — a multi-stage Dockerfile compiles the Angular front
  end with Node, packages the Spring back end with Maven, and copies only the
  resulting jar into a slim JRE runtime image.

## Stack

| | |
|---|---|
| Back end | Java 17, Spring Boot 2.7 (Web, Data JPA) |
| Front end | Angular 14, TypeScript, Bootstrap 5 |
| Database | H2, seeded at startup |
| Build | Maven (wrapper included), Angular CLI |
| Container | Docker, multi-stage build on Eclipse Temurin |

## Running it

### With Docker

```bash
docker build -t landon-hotel .
docker run -p 8080:8080 landon-hotel
```

### Locally

The Maven build compiles the Angular front end as part of its `validate` phase,
so the Angular CLI has to be installed before any Maven goal will run:

```bash
cd src/main/UI && npm install && cd -
export PATH="$PWD/src/main/UI/node_modules/.bin:$PATH"
```

Then start the back end on port 8080:

```bash
./mvnw spring-boot:run
```

To develop against the UI with live reload, serve it separately while the back
end is running — it calls the API at `http://localhost:8080`:

```bash
cd src/main/UI
npm start
```

### Tests

```bash
./mvnw test
```

Same prerequisite: the Angular CLI must be on `PATH`, or the `validate` phase
fails before the Java tests run.

## API

| Method | Endpoint | Purpose |
|---|---|---|
| `GET` | `/room/reservation/v1?checkin=&checkout=` | rooms available in a date range |
| `POST` | `/room/reservation/v1` | create a reservation |
| `GET` | `/landon/info/v1` | localized welcome messages and presentation times |

## Project layout

```
src/main/java/edu/wgu/d387_sample_code/
├── config/       # conversion service and web configuration
├── convertor/    # entity/response converters and service layer
├── entity/       # Room and Reservation JPA entities
├── model/        # request and response models
├── repository/   # Spring Data repositories
├── rest/         # reservation REST resource
├── WelcomeMessage.java    # threaded resource-bundle loading
└── TimeZoneConverter.java # ET/MT/UTC conversion

src/main/UI/      # Angular front end
```
