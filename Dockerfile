FROM dockerfile/java:openjdk-7-jre

MAINTAINER Mark McGuire <mark.b.mcg@gmail.com>

ADD target/uberjar/http-puglj-0.1.0-SNAPSHOT-standalone.jar /srv/http-puglj.jar

EXPOSE 8080

CMD ["java", "-jar", "/srv/http-puglj.jar"]
