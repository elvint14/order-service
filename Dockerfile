FROM openjdk:11
MAINTAINER "elvintaghiyev184@gmail.com"
ADD build/libs/order-service-1.jar order-service-1.jar
ENTRYPOINT ["java", "-jar","order-service-1.jar"]
EXPOSE 8086
