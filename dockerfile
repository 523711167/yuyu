# 使用 redis 官方镜像作为基础镜像
FROM redis:latest

# 设置维护者信息
LABEL maintainer="xiaoxipeng 523711167@qq.com"

RUN mkdir -p /xiaoxipeng/api

# 设置容器内的工作目录，后续COPY RUN 命令的target目录都是/usr/src/app
WORKDIR /xiaoxipeng/api

ADD ./target/yuyu.jar ./app.jar

EXPOSE 8080

# 定义容器启动时执行的命令
ENTRYPOINT ["java", "-jar", "app.jar"]
