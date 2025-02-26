#!/bin/bash

# 启用错误时立即退出
set -e

# 获取脚本所在文件夹目录,先执行cd然后在pwd赋值
# $0 表示当前目录+文件名称
DIR="$(cd "$(dirname "$0")" && pwd)"
source $DIR/common.sh

# 取消对文件名扩展的限制，允许在脚本中使用 * 等通配符
set +o noglob

item=0

# flag to using docker compose v1 or v2, default would using v1 docker-compose
DOCKER_COMPOSE=docker-compose

# ${BASH_SOURCE[0]}表示./install.sh
workdir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $workdir

# 测试docker安装环境
h2 "[Step $item]: checking if docker is installed ..."; let item+=1
check_docker

# 测试docker-compose安装环境
h2 "[Step $item]: checking docker-compose is installed ..."; let item+=1
check_dockercompose

# 解压mysql、nacos、redis容器环境
# -f 文件测试的条件操作符,文件存在返回true，反之false
if [ -f tool*.tar.gz ]
then
    h2 "[Step $item]: loading Harbor images ..."; let item+=1
    docker load -i ./harbor*.tar.gz
fi
echo ""

if [ -n "$DOCKER_COMPOSE ps -q"  ]
    then
        note "stopping existing Xiaoxipeng instance ..."
        $DOCKER_COMPOSE down -v
fi
echo ""

h2 "[Step $item]: starting Harbor ..."
$DOCKER_COMPOSE up -d

success $"----YuYu has been installed and started successfully.----"

























