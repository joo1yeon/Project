# 과제

Spring Boot 기반 회원 프로필, 포인트 충전 기능 구현 과제

## 📦 프로젝트 개요

- 회원 프로필 목록/상세 조회, 포인트 충전, 쿠폰 할인 적용 기능 구현

- **QueryDSL, JPA, Docker, MySQL, Gradle**

- **Dockerfile, docker-compose.yml**만으로 실행 가능 (별도 설치 불필요)

## ⚡️ 실행 방법

### 1. 프로젝트 클론

```bash
git clone https://github.com/joo1yeon/Project.git
cd Project
```

### 2. Docker 빌드 & 실행

```bash
./gradlew build -x test
docker-compose up --build
```

- 서버: http://localhost:8080

- MySQL: localhost:3306

## 📝 주요 기능

### 1. 회원 프로필 조회

- 목록 조회: 이름/조회수/최신순 정렬, 페이지네이션

- 상세 조회수 증가: 프로필 상세 조회시 조회수 +1

### 2. 포인트 충전 서비스

- 결제 요청 시 포인트 적립 (원화 1:1)

## 🛠️ 기술스택 & 라이브러리

- Java 17, Spring Boot 3.5.3, Gradle

- JPA, QueryDSL

- MySQL 8

- Docker, Docker Compose

- Lombok

- (테스트: JUnit5, AssertJ)

## 📝 오픈소스/외부 라이브러리 사용내역

- Lombok: 코드 생성/간소화 목적

- QueryDSL: JPA 동적 쿼리 구현

- MySQL Docker Image: 로컬 개발/테스트용 DB

- Swagger: API 문서화
  > http://localhost:8080/swagger-ui.html
