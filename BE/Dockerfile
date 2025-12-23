# 1️⃣ Dùng Java 17 (phù hợp Spring Boot)
FROM eclipse-temurin:17-jdk

# 2️⃣ Thư mục làm việc trong container
WORKDIR /app

# 3️⃣ Copy file jar từ máy vào container
COPY target/react-0.0.1-SNAPSHOT.jar app.jar

# 4️⃣ Chạy app khi container start
ENTRYPOINT ["java", "-jar", "app.jar"]