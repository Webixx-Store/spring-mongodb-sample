# Sử dụng image cơ sở là OpenJDK
FROM openjdk:17-jdk-slim

# Set thư mục làm việc trong container
WORKDIR /app

# Sao chép file jar vào container
COPY build/bot-binnance-0.0.1-SNAPSHOT.jar /app/bot-binnance-0.0.1-SNAPSHOT.jar

# Expose cổng mà ứng dụng Spring Boot chạy trên
EXPOSE 8888

# Command để chạy ứng dụng Spring Boot khi container được khởi chạy
CMD ["java", "-jar", "bot-binnance-0.0.1-SNAPSHOT.jar"]
