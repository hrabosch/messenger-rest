# Messenger REST API
REST API for sending messages to others users example with JWT auth.
## MongoDB
This tiny app is using MongoDB to persist data (messages, users, tokens...).
Easy start of MongoDB in Docker container, check docker folder.
Docker compose for easier start `docker-compose up -d`. It is using init script which is added simply via volumes.

## JWT
With **JWTConfigAdapter**, we are attaching our custom filter into chain. Filter itself is **JwtTokenFilter** where we authenticated is configured into context in case of valid token. Token with username and password are provided and handled in **JwtTokenProvider**.

## TODO
- [x] Jwt handling
- [x] Registration controller
- [x] Login
- [ ] Logout
- [x] Send message
- [ ] List messages
- [ ] Delete messages
- [x] List users
- [ ] Controller advice
- [ ] Test coverage
- [ ] Request examples