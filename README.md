# Two-Phase-Commit(2PC)
분산 트랜잭션(Distribute Transaction)을 이용하여, 복수의 데이터베이스(Database)에서 발생되는 트랜잭션의 흐름을 하나로 묶어 처리하는 기술을 구성해보는 프로젝트입니다.

## 스팩
| 스팩명           | 버전     | 기타       |
|---------------|--------|----------|
| [Java]        | 21.0.6 | Open JDK |
| [Spring Boot] | 3.5.3  |          |
 | [MySQL]       | 9.3.0  |          |

## 설정
### Java
컴퓨터에 설치한 [Java]의 위치를 시스템 환경변수로 등록하여, 인식할 수 있도록 합니다.
&nbsp;

`Linux` 또는 `MacOS` 계열의 OS는 `.bashrc`, `.zshrc` 등, 본인의 컴퓨터 `shell`에 맞는 파일에 아래의 내용을 작성합니다. 
```text
export JAVA_HOME={21버전 이상의 자바 설치 경로}
```

작성이 완료되었다면, 이를 시스템에 반영합니다.
```text
# bash shell
source ~/.bashrc

# zsh shell
source ~/.zshrc

# profile 사용
source ~/.profile
```
&nbsp;

`Windows` 계열에서는 다음과 같이 수행합니다.
```text
setx JAVA_HOME "{21버전 이상의 자바 설치 경로}"
```
&nbsp;

만약, [jenv]를 이용하고 있다면 다음과 같이 수행합니다.
```text
jenv local {21버전 이상의 자바 버전}
```

### Database
이 프로젝트에서 사용된 데이터베이스는 [MySQL]이며, [Docker]를 활용하여 [MySQL] 데이터베이스를 구축합니다.
&nbsp;

[Docker]에서 사용할 [MySQL] 최신 이미지를 받습니다.
```text
docker pull mysql
```
&nbsp;

위의 단계를 통해 받은 [MySQL] 이미지로 [Docker] 컨테이너를 생성하기 위해 `compose.yml` 또는 `docker-compose.yml` 파일을 구성합니다.
```yaml
services:
  two-phase-commit-mysql-1:
    container_name: two-phase-commit-mysql-1
    image: ${DOCKER_IMAGE}
    volumes:
      - ${VOLUME_HOME}/two-phase-commit-mysql-1/data:${MYSQL_DATA_DIR}
      - ${VOLUME_HOME}/two-phase-commit-mysql-1/conf.d:${MYSQL_CONF_DIR}
    ports:
      - '13306:3306'
    environment:
      MYSQL_DATABASE: ${MYSQL_DEFAULT_DATABASE}
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
  two-phase-commit-mysql-2:
    container_name: two-phase-commit-mysql-2
    image: ${DOCKER_IMAGE}
    volumes:
      - ${VOLUME_HOME}/two-phase-commit-mysql-2/data:${MYSQL_DATA_DIR}
      - ${VOLUME_HOME}/two-phase-commit-mysql-2/conf.d:${MYSQL_CONF_DIR}
    ports:
      - '13307:3306'
    environment:
      MYSQL_DATABASE: ${MYSQL_DEFAULT_DATABASE}
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
```
&nbsp;

컨테이너에 대한 정보를 구성할 때, 공통적인 요소는 `.env` 파일에서 변수로 선언하여 불러올 수 있도록 합니다.
```text
DOCKER_IMAGE=mysql:latest
MYSQL_CONF_DIR=/etc/mysql/conf.d
MYSQL_DATA_DIR=/var/lib/mysql
MYSQL_DEFAULT_DATABASE=store
MYSQL_ROOT_PASSWORD={MySQL 초기 구성 시, 설정할 root 계정 비밀번호}
VOLUME_HOME={Docker volume과 연결할 폴더의 경로를 작성}
```
&nbsp;

구성이 모두 완료되었다면, `compose.yml` 또는 `docker-compose.yml`에 작성한 컨테이너 정보를 기반으로 컨테이너를 생성합니다.
```text
docker compose up --detach
```
> ⚠️ `.env` 파일은 `compose.yml` 또는 `docker-compose.yml` 파일과 동일한 경로에 있어야 인식됩니다.

[Docker]: https://www.docker.com/ 'Docker main page'
[Java]: https://openjdk.org/ 'Open JDK main page'
[jenv]: https://github.com/jenv/jenv 'jevn main page'
[MySQL]: https://www.mysql.com 'MySQL main page'
[Spring Boot]: https://docs.spring.io/ 'Spring Boot Framework main page'