# What is this?
A simple Spring Boot driven proof-of-concept implementation of RSS client.
It was made for educational and demonstration purposes. This app can manage RSS channels (CRUD), update them in asynchronously way and show articles entries data (feed).

Tested on RSS feed: https://lorem-rss.herokuapp.com/

The project uses:
- Spring MVC
- Spring Data
- Hibernate
- H2 embedded
- Scheduler
- Async
- Lombok
- Maven
- Javac target 1.8 (tested on jdk 17)


**REST end-points (arguments omitted)**:
- GET `/api/rss`: list of all added rss feeds
- POST `/api/rss`: create new rss feed
- DELETE `/api/rss/{id}`: remove rss feed
- POST `/api/rss/update`: update all rss in the background process
- POST `/api/rss/update/{id}`: update a specific rss in the background process
- GET `/api/feed`: fetch the feed page of all articles ordered chronologically

**application.properties**:
- `rssUpdateSeconds`: specify scheduler update rate 

## How to run
- `git clone https://github.com/haz00/spring-rss-client-example.git`
- `cd spring-rss-client-example`
- `./mvnw spring-boot:run`
