#Build and Run Swager from Dockerfile
docker build --tag=apidoc:1.0

docker run -d --name=apidoc -p 4001:8080 -v "./core.json:/usr/share/nginx/html/data.json" apidoc:1.0

#Swagger Editor
docker pull swaggerapi/swagger-editor

docker run -p 80:8080 swaggerapi/swagger-editor

#Swagger UI
docker pull swaggerapi/swagger-ui

docker run -p 80:8080 swaggerapi/swagger-ui