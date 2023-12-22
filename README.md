# singabsence

Application de gestion des demandes d'absences de la SING SA.

## Dépendences

* maven          : version ``3.8.7 || latest lts``
* java           : version ``17 || latest lts``
* node           : version ``v20.10.0 || latest lts`` 
* npm            : version ``10.2.5 || latest lts``
* angular        : version ```17 || latest lts```
* docker         : version ``24.0.7 || latest lts``
* docker-compose : version ``v2.23.3 || latest lts``

## Structure du projet

Le projet a été généré avec jhipster voir la pull request 1 : [https://github.com/lmlouis/singabsence/pull/1](https://github.com/lmlouis/singabsence/pull/1)
Installer jhipster :
````
npm install -g generator-jhipster
npm ls -g generator-jhipster
````
## Exécuter le projet en mode développement

installer les dépendances du projet

```
npm install
```
Exécuter ./mvnw dans un terminale et npm start  dans un autre
```
./mvnw
npm start
```

## Building for production

### Packaging as jar

Construire l'application en mode production:

```
./mvnw -Pprod clean verify
```

Ensuite exécuter :

```
java -jar target/*.jar
```

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.


### Packaging as war

Packaging en fichier ``.war``:

```
./mvnw -Pprod,war clean verify
```

## Testing

### Spring Boot tests

Exécuter l'application spring boot en mode test:

```
./mvnw verify
```

### Utiliser Docker

La ressource se trouve dans le repertoire [src/main/docker](src/main/docker) 

Exécuter postgresql avec docker :

```
docker compose -f src/main/docker/postgresql.yml up -d
```

Arrêter le conteneur postgresql :

```
docker compose -f src/main/docker/postgresql.yml down
```

Conteneuriser le projet :

```
npm run java:docker
```

Construire le projet avec docker sous l'architecture arm64:

```
npm run java:docker:arm64
```

ensuite exécuter:

```
docker compose -f src/main/docker/app.yml up -d
```

## Réference

[JHipster Homepage and latest documentation]: https://www.jhipster.tech
[JHipster 8.0.0 archive]: https://www.jhipster.tech/documentation-archive/v8.0.0
[Using JHipster in development]: https://www.jhipster.tech/documentation-archive/v8.0.0/development/
[Using Docker and Docker-Compose]: https://www.jhipster.tech/documentation-archive/v8.0.0/docker-compose
[Using JHipster in production]: https://www.jhipster.tech/documentation-archive/v8.0.0/production/
[Running tests page]: https://www.jhipster.tech/documentation-archive/v8.0.0/running-tests/
[Code quality page]: https://www.jhipster.tech/documentation-archive/v8.0.0/code-quality/
[Setting up Continuous Integration]: https://www.jhipster.tech/documentation-archive/v8.0.0/setting-up-ci/
[Node.js]: https://nodejs.org/
[NPM]: https://www.npmjs.com/
[Webpack]: https://webpack.github.io/
[BrowserSync]: https://www.browsersync.io/
[Jest]: https://facebook.github.io/jest/
[Leaflet]: https://leafletjs.com/
[DefinitelyTyped]: https://definitelytyped.org/
[Angular CLI]: https://cli.angular.io/
