```
docker build --rm -f docker/Dockerfile -t docker-mecab:latest .
docker build --rm -t docker-java-mecab:latest -f docker/Dockerfile.java-build .
./gradlew jibDockerBuild
docker run --rm -it -p 8080:8080 --name mecab -d mecab-java-api-server
```
