FROM openjdk:8u181-jre-alpine
WORKDIR /app
COPY build/libs/vote-management .
CMD ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom", "-jar", "vote-management.jar" ]