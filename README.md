```
docker build --rm -t -f docker/Dockerfile docker-mecab:latest .
docker build --rm -t docker-java-mecab:latest -f docker/Dockerfile.java-build .
docker run --rm -v $(pwd)/libs:/work/libs -it docker-java-mecab:latest bash
```
