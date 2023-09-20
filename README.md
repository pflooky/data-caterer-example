# data-caterer-example

Example Java and Scala API usage for Data Caterer

## How

Can follow detailed documentation found [here](https://pflooky.github.io/data-caterer-docs/setup/) for more details.

### Java

1. Create new Java class similar
   to [DocumentationJavaPlanRun.java](src/main/java/com/github/pflooky/plan/DocumentationJavaPlanRun.java)
   1. Needs to extend `com.github.pflooky.datacaterer.java.api.PlanRun`

### Scala

1. Create new Scala class similar
   to [DocumentationPlanRun.scala](src/main/scala/com/github/pflooky/plan/DocumentationPlanRun.scala)
   1. Needs to extend `com.github.pflooky.datacaterer.api.PlanRun`

## Run

Requires:

- Docker
- Access to Data Caterer library
  - `GITHUB_USER`, `GITHUB_TOKEN` environment variables

```shell
./run.sh
<input class name>
```

## Docker

Create your own Docker image via:
  
```shell
cd docker
./gradlew clean build
docker build -t <my_image_name>:<my_image_tag> .
docker run -e PLAN_CLASS=com.github.pflooky.plan.DocumentationPlanRun -v ${PWD}/docs/run:/opt/app/data <my_image_name>:<my_image_tag>
#check results under docs/run folder
```

## Docker Compose

Run with own class from either Java or Scala API:
  
```shell
./gradlew clean build
cd docker
PLAN_CLASS=com.github.pflooky.plan.DocumentationPlanRun DATA_SOURCE=postgres docker-compose up -d datacaterer
```

[Details from docs](https://pflooky.github.io/data-caterer-docs/get-started/docker/).  
Docker compose sample found under `docker` folder.

```shell
cd docker
docker-compose up -d datacaterer
```

Check result under [here](docker/data/custom).
  
Change to another data source via:
- postgres
- mysql
- cassandra
- solace
- kafka
- http
  
```shell
DATA_SOURCE=cassandra docker-compose up -d datacaterer
```

## Helm

```shell
helm install data-caterer ./data-caterer-example/helm/data-caterer
```
