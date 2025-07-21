# 1. 자바 17 기반 이미지 사용
FROM eclipse-temurin:17-jdk-alpine

# 2. 타임존 세팅 (한국 기준, 필요시)
ENV TZ=Asia/Seoul

# 3. jar 파일 복사 (build/libs/your-app.jar 기준, 실제 파일명 확인!)
COPY build/libs/*.jar app.jar

# 4. 앱 실행
ENTRYPOINT ["java","-jar","/app.jar"]