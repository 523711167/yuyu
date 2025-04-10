FROM 192.168.18.124:80/xiaoxipeng/centos8-jdk17:latest

ARG JAR_NAME
# 设置维护者信息
LABEL maintainer="xiaoxipeng 523711167@qq.com"

# 设置环境变量
ENV NACOS_ADDRESS=172.20.1.0 \
    CONFIG_SERVER_ADDRESS=172.20.1.1 \
    MYSQL_ADDRESS=172.20.1.2 \
    REDIS_ADDRESS=172.20.1.3

RUN mkdir -p /xiaoxipeng/gateway

# 设置容器内的工作目录，后续COPY RUN 命令的target目录都是/usr/src/app
WORKDIR /xiaoxipeng/gateway

ADD ./target/${JAR_NAME}.jar ./yuyu-gateway.jar

# 定义容器启动时执行的命令
CMD ["java", "-jar","yuyu-gateway.jar", "--spring.profiles.active=test"]
