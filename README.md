# MeCab Java API Server

## How to build

```$sh
docker build --rm -f docker/Dockerfile -t docker-java-mecab:latest . && \
./gradlew jibDockerBuild && \
docker run --rm -it -p 8080:8080 --name mecab -d mecab-java-api-server
```
