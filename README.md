# Two-Phase-Commit(2PC)

분산 트랜잭션(Distribute Transaction)을 이용하여, 복수의 데이터베이스(Database)에서 발생되는 트랜잭션의 흐름을  
하나로 묶어 처리하는 기술을 구성해보는 프로젝트입니다.

## 스팩
| 스팩명           | 버전     | 기타       |
|---------------|--------|----------|
| [Java]        | 21.0.6 | Open JDK |
| [Spring Boot] | 3.5.3  |          |

## 설정

[Java] 환경변수를 설정합니다.

- Linux | MacOS
  ```text
  export JAVA_HOME={21버전 이상의 자바 설치 경로}
  ```

  Windows
  ```text
  setx /m JAVA_HOME "{21버전 이상의 자바 설치 경로}"
  ```
- [jenv](https://github.com/jenv/jenv)
  ```text
  jenv local {21버전 이상의 자바 버전}
  ```
  
[Java]: https://openjdk.org/ 'Open JDK main page'
[Spring Boot]: https://docs.spring.io/ 'Spring Boot Framework main page'