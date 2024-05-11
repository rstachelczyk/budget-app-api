# Install

[//]: # (1. Copy the following files into `ssl/`:)

[//]: # (  - nelnet-ca.cer)

[//]: # (  - paymentspring.test.chain.pem)

[//]: # (  - paymentspring.test.crt)

[//]: # (  - paymentspring.test.key)

[//]: # ()
[//]: # (   **This is only possible if you have previously installed the [nps repo]&#40;https://github.com/paymentspring/nps&#41; correctly**)

[//]: # (1. Add the following to your `/etc/hosts`:)

[//]: # (  - `budget.bucks.test`)

[//]: # ()
[//]: # (   On windows I typically add it to the WSL file system as well as the Windows file system found)

[//]: # (   at in this path: `C:\Windows\System32\drivers\etc`. *Note,* you will need to run the text editor)

[//]: # (   as an adminstrator for the changes to take effect.)
1. Build the image:
    ```shell
    docker compose build budget-api
    ```

   **Warning:** you should check your user ID (if not on mac) before running the build incase
   your user is 1001 while mine is 1000 or other such cases. If your user is not 1000, you will
   need to supply the `UID` build argument.

[//]: # (1. Add environment variables to Shell RC file:)

[//]: # (   ```shell)

[//]: # (    export NPS_AWS_ACCESS_KEY=)

[//]: # (    export NPS_AWS_SECRET_KEY=)

[//]: # (    export GITHUB_TOKEN=)

[//]: # (    export GITHUB_USERNAME=)

[//]: # (    export LOCAL_DB_PASSWORD=Password123)

[//]: # (    export LOCAL_JDBC_URL=jdbc:sqlserver://mssql:1433;multiSubnetFailover=true;encrypt=false;)

[//]: # (    export LOCAL_DB_USERNAME=sa)

[//]: # (   ```)

2. Run the stack:
    ```shell
    docker compose down; docker compose up -d
    ```

   *Bonus tip:* You can alias this to: `dcd` and `dcu` respectively.

# Usage
Once your stack is running you can access the [docs here.](https://budget.bucks.test/swagger-ui/index.html)
The root API path is: `https://budget.bucks.test/api/v1`.
You should be able to check status of the running API with either of the two cURL commands:

```shell
curl https://budget.bucks.test/actuator/health \
  -H "Content-Type: application/json" | jq
```

```shell
curl https://budget.bucks.test/actuator/info \
  -H "Content-Type: application/json" | jq
```

## Playwright
In order to ensure these tests run successfully, we need to ensure the db is seeded with test data
& we have installed the required packages via npm.

The DB will be seeded when the `liquibase` container is brought up. As long as you have done
that via the `docker compose up -d` step above, you should be good. You can confirm there is test
data by connecting to the running postgres container with the following config:

```
Host/Socket = 127.0.0.1
Port = 5432
User = postgres
Password = examplepassword
```

Npm packages can be installed by running the following command in the root directory of the repo.
```shell
npm ci
```

*Note:* This step assumes you have node installed on your machine already. You should now see
a `node_modules` folder present in the root directory.

Playwright tests can be run with the following command:
```shell
docker compose exec service npx playwright test
```

I have aliased `docker compose exec` to `dce`.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.0/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Liquibase Migration](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#howto.data-initialization.migration-tool.liquibase)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#actuator)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

