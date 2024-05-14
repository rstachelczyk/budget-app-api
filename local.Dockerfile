#FROM maven:3.8-openjdk-17 AS budget-api-development
FROM maven:3.9.6-ibm-semeru-17-focal as budget-api-development

ENV LANG=C.UTF-8 \
    APP_HOME=/usr/src/app \
    MAVEN_CONFIG=/root/.m2 \
    MAVEN_BUILD_REPO=/usr/share/maven/ref/repository
#    JAVA_HOME_SECURITY=$JAVA_HOME/lib/security

WORKDIR $APP_HOME

# Register Node 18 from Nodesource repository
RUN curl -fsSL https://deb.nodesource.com/setup_20.x | bash - && \
  apt-get install -y nodejs

COPY pom.xml ./

RUN mvn -Dmaven.main.skip \
  -Dmaven.test.skip=true \
  -Dmaven.repo.local=${MAVEN_BUILD_REPO} \
  clean \
  dependency:resolve

RUN mvn -Dmaven.main.skip \
  -Dmaven.test.skip=true \
  -Dmaven.repo.local=${MAVEN_BUILD_REPO} \
  dependency:resolve-plugins

COPY package*.json ./

RUN npm ci

RUN mvn -Dmaven.main.skip \
  -Dmaven.test.skip=true \
  -Dspring-boot.repackage.skip \
  -Dmaven.repo.local=${MAVEN_BUILD_REPO} \
  package

COPY . ./

CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=\"-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5001\""]
