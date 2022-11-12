FROM java:8
EXPOSE 8085

VOLUME /tmp
ADD service-statistics.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
