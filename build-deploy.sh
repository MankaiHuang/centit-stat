#!/bin/sh
#--------------------------------------------
#参数说明:sh build-deploy.sh ${DEPLOYMENT} ${MODULE}
#本例执行:sh build-deploy.sh centit-stat centit-stat-web
#--------------------------------------------
DEPLOYMENT=$1
MOUDLE=$2
TIME=$(date +%Y%m%d%H%M)
GIT_REVISION=$(git log -1 --pretty=format:"%h")
IMAGE_NAME=172.29.0.13:8082/${DEPLOYMENT}:${TIME}_${GIT_REVISION}
########编译########
mvn -U -pl $2 -am clean package -DskipTests=true
########写入dockerfile文件########
cat >./$2/dockerfile <<EOF
FROM tomcat
MAINTAINER hzf "hzf@centit.com"
ADD target/*.war /usr/local/tomcat/webapps/$1.war
EXPOSE 8080
CMD /usr/local/tomcat/bin/startup.sh && tail -f /usr/local/tomcat/logs/catalina.out
EOF
########构建镜像########
cd ./$2/
docker build -t ${IMAGE_NAME} .
########上传nexus########
docker login -u developer -p centit 172.29.0.13:8082
docker push ${IMAGE_NAME}
########删除容器########
docker rm -f $1 |true
########删除镜像########
docker image rm ${IMAGE_NAME} |true
docker images|grep none|awk '{print $3}'|xargs docker rmi |true
########连接nexus私服########
docker login -u developer -p centit 172.29.0.13:8082
########运行########
docker run -d -p 13000:8080 --name $1 ${IMAGE_NAME}

