#FROM node:26
#
#WORKDIR ./frontend
#COPY ./gigsync/package*.json ./
#RUN npm install
#COPY gigsync/ .
#EXPOSE 3000
#CMD npm run dev

FROM node:26

WORKDIR ./frontend
COPY ./gigsync/package*.json ./
RUN npm install
COPY gigsync/ .

ARG API_GATEWAY_URL
ARG API_GATEWAY_PORT
ENV API_GATEWAY_URL=$API_GATEWAY_URL
ENV API_GATEWAY_PORT=$API_GATEWAY_PORT

RUN npm run build

EXPOSE 3000

CMD npm run start