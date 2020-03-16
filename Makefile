
NAME=jjson-api
BUILD_NUMBER=dev
APP_VERSION=1.1
LOG_LEVEL=DEBUG
DATE:=`date '+%Y.%m.%d-%H:%M:%S'`

install: build run

build: build.info
	mvn clean install

build.container:
	mvn clean install

build.deps:
	mvn dependency:tree 

build.info:
	echo 'java.version=$${java.version}'>src/main/resources/build.properties
	echo "app.version=$(APP_VERSION)">>src/main/resources/build.properties
	echo "app.title=$(NAME)">>src/main/resources/build.properties
	echo "build.number=$(BUILD_NUMBER)">>src/main/resources/build.properties
	echo "build.date=$(DATE)">>src/main/resources/build.properties

run:
	DATA_DIR=./test/data java -jar target/$(NAME).jar --logging.level.de.mgeo=$(LOG_LEVEL)

#DATA_FILE=test-entries.json DATA_DIR=./test/data
