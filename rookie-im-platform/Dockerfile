# 基础镜像
FROM openjdk:17-jdk-alpine

# 设置工作目录
WORKDIR /app

# 将 platform 模块的 JAR 文件复制到镜像中
COPY target/rookie-im-platform-1.0-SNAPSHOT.jar platform.jar

# 暴露服务端口
EXPOSE 8081

# 设置默认的 JVM 参数（可通过 docker run 时覆盖）
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC -XX:MaxGCPauseMillis=200"

# 启动 platform 模块
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar platform.jar"]