FROM node:latest
WORKDIR /Users/Oleg/IdeaProjects/DZ_Mockito/dip_neto
COPY package*.json ./
EXPOSE 8080
RUN npm install
COPY . .
CMD ["npm",  "run", "serve"]


