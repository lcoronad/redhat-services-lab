FROM redhat-openjdk18-openshift:1.2
# Add a volume pointing to /tmp
VOLUME /tmp
# The application's jar file
ARG JAR_FILE=lib/consulta-saldo-0.0.1-SNAPSHOT.jar
# Add the application's jar to the container
ADD ${JAR_FILE} consulta-saldo-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","consulta-saldo-0.0.1-SNAPSHOT.jar"]
