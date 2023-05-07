
# AWS Lambda Scheduled Event Sample

* Spring Boot 2.7
* Spring Cloud v2021
* Spring Cloud Function Adapter AWS (from Spring Cloud v2021)

## Build

```sh
mvn -U clean install
```

## Build and Deploy

* Please, set up your AWS CLI with Account before deployment
* After, update **env.sh** file with your account id and AWS Region

```sh
./build-and-deploy.sh
```
