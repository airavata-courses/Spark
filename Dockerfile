FROM node:8

COPY . /home/ubuntu/Spark

WORKDIR /home/ubuntu/Spark/findyournextmovie

COPY findyournextmovie/package*.json ./

EXPOSE 8080

CMD [ "npm", "start" ]
