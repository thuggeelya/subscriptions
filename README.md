# Subscription App

---

## Dockerize:

* clone project
* run
```bash
./gradlew clean build --no-daemon
cd .ci
docker-compose up --build
```
* API will be at [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)