# 基础镜像
FROM adoptopenjdk:17-jdk-hotspot

# 设置工作目录
WORKDIR /app

# 将 jar 文件复制到镜像中
COPY rookie-im-service/target/rookie-im-service-1.0-SNAPSHOT.jar app.jar

# 暴露服务端口
EXPOSE 8080

# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]