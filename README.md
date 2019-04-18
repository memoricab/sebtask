### Pelicanapp
* Listens port 8080
* Is also a spring boot application but treats as a proxy.
* Uses netflix zuul library.
* Routes `/country/{country}/{word}` requests to related  country micro-service.
* Manipulates some responses from micro-services.

### Estonia Micro-Service
* Listens port 8081
* Responds `/translate/{word}` 
### Lithuania Micro-Service
* Listens port 8082
* Responds `/translate/{word}`
### Belgium Micro-Service  
* Listens port 8083
* Responds `/translate/{word}`

### Translation Core
* Just a module in order to not duplicate `TranslationPojo` and  `ErrorDetails`(also a pojo) classes.


### Tests
**If you want to run pelicanapp tests other micro-services need to be running already.** 
