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
