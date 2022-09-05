# coding-day

un petit projet sur l'événement de la journée du codage en 2021 à mon université.

Spring boot et Hibernate ont été utilisé.

# description du projet

Il s'agit d'un projet simple pour gérer des comptes (utilisateurs),
on affecte à chaque utilisateur une ou plusieurs addresses, voila c'est tout :)

# préinstallation

il faut les éléments suivants:

* java 11
* maven
* mysql

# éxcecution

il faut modifier le nom d'utilisateur et le mot de passe
dans le fichier `src/main/resources/application.properties`:

```
spring.datasource.username=<ton-nom-d-utilisateur-mysql>
spring.datasource.password=<ton-mot-de-passe-mysql>
```

pour compiler le projet:

```bash
mvn clean install
```

et pour excecuter le projet:

```bash
java -jar target/GestionDesComptes-0.0.1-SNAPSHOT.jar
```