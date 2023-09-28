FROM openjdk:17
LABEL maintainer="nitheesh"
ADD target/graphql-demo-0.0.1-SNAPSHOT.jar actionsdemo.jar
ENTRYPOINT ["java","-jar","actionsdemo.jar"]