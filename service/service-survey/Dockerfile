FROM java:8
EXPOSE 8082

VOLUME /tmp
ADD service-survey.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
