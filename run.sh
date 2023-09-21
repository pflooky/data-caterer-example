#!/usr/bin/env bash

data_caterer_version=$(grep dataCatererVersion gradle.properties | cut -d= -f2)

read -p "Class name of plan to run [DocumentationPlanRun]: " class_name
class_name=com.github.pflooky.plan.${class_name:-DocumentationPlanRun}

image_suffix="-basic"
if [[ "$class_name" == *"Advanced"* ]]; then
  image_suffix=""
fi

echo "Building jar with plan run"
./gradlew clean build
if [[ $? -ne 0 ]]; then
  echo "Failed to build!"
  exit 1
fi

echo "Running Data Caterer via docker, version: $data_caterer_version"
docker run -p 4040:4040 \
  -v "$(pwd)/build/libs/data-caterer-example-0.1.0.jar:/opt/spark/jars/data-caterer.jar" \
  -v "$(pwd)/docker/sample:/opt/app/data" \
  -e "PLAN_CLASS=$class_name" \
  -e "DRIVER_MEMORY=2g" \
  -e "EXECUTOR_MEMORY=2g" \
  datacatering/data-caterer"$image_suffix":"$data_caterer_version"

echo "Finished!"
