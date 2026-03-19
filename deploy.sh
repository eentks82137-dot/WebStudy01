#!/bin/bash

# 1. Build the project using Maven
echo "🛠️ Building the project..."
mvn clean package

if [ $? -ne 0 ]; then
    echo "❌ Build failed. Exiting."
    exit 1
fi

# 2. 기존 톰캣의 ROOT 폴더 및 WAR 삭제

echo "🧹 기존 배포 파일 제거 중..."
sudo rm -rf /opt/tomcat/webapps/ROOT /opt/tomcat/webapps/ROOT.war

# 3. 새 WAR 파일을 ROOT.war로 복사

echo "📦 새 버전 배포 중..."
sudo cp target/*.war /opt/tomcat/webapps/ROOT.war

echo "✅ 배포 완료!"

# 4. 톰캣 프로세스 확인, 없으면 시작
TOMCAT_PID=$(ps -ef | grep tomcat | grep -v grep | awk '{print $2}')

if [ -z "$TOMCAT_PID" ]; then
    echo "Tomcat이 실행 중이 아닙니다."
    /opt/tomcat/bin/startup.sh
    echo "Tomcat을 시작했습니다."
else
    echo "Tomcat이 실행 중입니다. PID: $TOMCAT_PID"
fi


echo "------------------------------------------"

# 5. 로그 실시간 확인
sudo tail -f /opt/tomcat/logs/catalina.out