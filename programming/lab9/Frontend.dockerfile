FROM node:19.9.0-alpine3.16 AS build
WORKDIR /build
COPY frontend/*.json /build
RUN npm i
COPY frontend /build/
RUN ls
RUN npm run build

FROM nginx AS nginx
COPY --from=build /build/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf