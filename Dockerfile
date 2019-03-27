FROM node:8

COPY . /home/ubuntu/Spark

WORKDIR /home/ubuntu/Spark/findyournextmovie

COPY findyournextmovie/package*.json ./

EXPOSE 3000

CMD [ "npm", "start" ]
