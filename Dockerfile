# 基于哪个镜像
#FROM java:8
FROM openjdk:8-jdk-alpine
# 将本地文件夹挂载到当前容器
VOLUME /tmp
WORKDIR /wuwei/tuya2capi


# 拷贝文件到容器
ADD target/tuya2cApi-0.0.1-SNAPSHOT.jar /wuwei/tuya2capi/tuya2cApi.jar

EXPOSE 8022
# 配置容器启动后执行的命令
#ENTRYPOINT  ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/wuwei/anticovid19/anticovid19.jar"]
ENTRYPOINT  ["java","-jar","/wuwei/tuya2capi/tuya2cApi.jar"]
