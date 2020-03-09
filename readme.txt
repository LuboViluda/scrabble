spring boot and hibernate test playground:

how to run:
in scrabble folder (pom.xml level)
1. build:
mvn clean install
2. run mvn spring-boot:run
mvn spring-boot:run

h2 in memory db login:
http://localhost:8080/h2-console/
jdbc URL: jdbc:h2:mem:testdb
name: sa
password:  

notes:
endpoints -
http://localhost:8080/players
http://localhost:8080/players/{playerId}
http://localhost:8080/players/leaderboard
