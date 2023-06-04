# Github Api

Simple project to get data from github api about user repositories.

## Features

There is `GET` endpoint under `http://localhost:8080/api/github/repositories`.

* To get list of repositories for specific user you need to send request on

```
http://localhost:8080/api/github/repositories?username={username}
```

* If you will send in header `Accept: application/xml` api will respond with:

```json
{
  "status": 406,
  "message": "Application/xml header is not supported!"
}
```

* If you will try to find the user that does not exist api will respond with:

```json
{
  "status": 404,
  "message": "User does not exist"
}
```

* In proper request api will respond with json of repositories which are not forks with this format:

```json
{
  "repositories": [
    {
      "repositoryName": "repositoryName",
      "ownerLogin": "ownerLogin",
      "branches": [
        {
          "branchName": "branchName",
            "lastCommitSha": "lastCommitSha"
        }
      ]
    }
  ]
}
```

## Technology stack

* Docker,
* makefile,
* Java 17,
* Spring Boot 3.0.4,
* Spring Security,
* Logback

## How to run it

Use makefile to run this project. Just type `make` in the terminal and it will run the project. Api will be available at:

```
http://localhost:8080/api/github/
```

Otherwise you run it manually by typing:

```maven
mvn clean spring-boot:run
```
