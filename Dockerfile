FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/*.jar vta-back-end-0.0.1-SNAPSHOT.jar
ENV JWT_SECRET_KEY=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
ENV JWT_EXPIRATION=86400000
ENV JWT_REFRESH_EXPIRATION=604800000
ENV MONGODB_URI=mongodb+srv://virtualtravelassistance:6PaQ97vi0uYkV7TG@vta.i7fwmes.mongodb.net/vtaDatabase?retryWrites=true&w=majority&appName=VTA
ENV SERVER_PORT=5000
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "vta-back-end-0.0.1-SNAPSHOT.jar"]