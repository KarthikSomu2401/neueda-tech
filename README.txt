## Docker commands
## step1 - Pulling redis image from docker hub
docker pull redis
 
## step2 - Running the container
docker run -d -p 6379:6379 --name my-redis redis







## build spring application
docker build -t tinyutl-generator-app .

## start the application
docker run -p 8080:8080 -t tinyutl-generator-app