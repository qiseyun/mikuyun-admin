FROM openjdk:11-jdk-slim

MAINTAINER qiseyun

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD target/mikuyun-admin-*.jar /app.jar

# 定义 JVM 参数
ENV JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /app.jar"]