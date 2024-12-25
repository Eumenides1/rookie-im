# 基础镜像
FROM openjdk:17-jdk-alpine

# 设置工作目录
WORKDIR /app

# 将 service 模块的 JAR 文件复制到镜像中
COPY target/rookie-im-service-1.0-SNAPSHOT.jar app.jar

# 暴露服务端口
EXPOSE 8080

# 设置默认的 JVM 参数（可通过 docker run 时覆盖）
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:MaxGCPauseMillis=200"

# 启动 service 模块
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]