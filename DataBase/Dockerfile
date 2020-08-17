FROM postgres
ENV POSTGRES_USER chatdata
ENV POSTGRES_PASSWORD chatdata
ADD schema.sql /docker-entrypoint-initdb.d
EXPOSE 5432