#!/bin/bash

# 톰캣 프로세스 확인
TOMCAT_PID=$(ps -ef | grep tomcat | grep -v grep | awk '{print $2}')

if [ -z "$TOMCAT_PID" ]; then
    echo "Tomcat이 실행 중이 아닙니다."
    # 필요시 톰캣 시작 명령 추가: /path/to/tomcat/bin/startup.sh
    /opt/tomcat/bin/startup.sh
    echo "Tomcat을 시작했습니다."
else
    echo "Tomcat이 실행 중입니다. PID: $TOMCAT_PID"
fi
