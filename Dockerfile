FROM adoptopenjdk/openjdk11

WORKDIR /mmgn

COPY . .

RUN ./gradlew clean bootjar

EXPOSE 8080

CMD [ "java", "-jar", "-Dspring.profiles.active=prod", "build/libs/MakMuGaNeTalk-0.0.1-SNAPSHOT.jar" ]