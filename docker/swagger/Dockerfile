FROM swaggerapi/swagger-ui

#COPY data.json /usr/share/nginx/html/data.json
WORKDIR /usr/share/nginx/html
RUN sed -i '/petstore/c\        url = "./data.json";' index.html
RUN cat index.html