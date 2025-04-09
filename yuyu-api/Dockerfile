FROM 192.168.18.124:80/xiaoxipeng/centos8-jdk17:latest

ARG JAR_NAME
# 设置维护者信息
LABEL maintainer="xiaoxipeng 523711167@qq.com"

RUN mkdir -p /xiaoxipeng/api

# 设置容器内的工作目录，后续COPY RUN 命令的target目录都是/usr/src/app
WORKDIR /xiaoxipeng/api

ADD ./target/${JAR_NAME} ./yuyu-api.jar

# 定义容器启动时执行的命令
CMD ["java", "-jar","yuyu-api.jar", "--spring.profiles.active=test"]
