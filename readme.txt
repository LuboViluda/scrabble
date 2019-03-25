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
changed "const" leaderboard - returns top 3
							- at least 3 matches required

next steps:
-> crud for player test (should be trivial)
-> investigate speed of playerRepositoryTest ("units" take seconds...)
-> unit tests other funcitonality (correct exceptions, mapping to Dto,...)
-> maybe entity optimizing/query, there could be better way how to break many to many relationship and get results directly in hibernate entities 

