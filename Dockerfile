FROM adoptopenjdk/openjdk11
LABEL maintainer="Rafael <rafael.whatsthestory@gmail.com>"

WORKDIR /app
EXPOSE 8090

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -XX:+UnlockExperimentalVMOptions -Djava.security.egd=file:/dev/./urandom -jar vote-management.jar" ]

ADD build/libs/vote-management.jar .