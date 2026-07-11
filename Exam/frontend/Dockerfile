FROM node:26

WORKDIR ./frontend
COPY ./gigsync/package*.json ./
RUN npm install
COPY gigsync/ .
EXPOSE 3000
CMD npm run dev