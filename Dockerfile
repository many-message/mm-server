FROM adoptopenjdk:11-jre-hotspot

VOLUME /upload

COPY target/*.jar ./

ENV JVM_ARGS="-Xms256m -Xmx1024m"
ENV CMD_LINE_ARGS="--spring.profiles.active=dev"

EXPOSE 8080

ENTRYPOINT java -server -Dfile.encoding=UTF-8 -Djava.security.egd=file:/dev/./urandom $JVM_ARGS -jar *.jar $CMD_LINE_ARGS