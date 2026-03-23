# World time

## Requirements

- Java 21
- Maven 3.9.9
- tomcat 10.1.0 (`/opt/tomcat`)
- `xmllint`

## Deployment Modes

이 프로젝트는 개발용 exploded 배포와 운영용 WAR 배포를 분리해서 사용할 수 있습니다.

### 개발용 exploded 배포

- 스크립트: `sudo ./init_dev_deploy.sh`
- 목적: JSP, 정적 리소스, `target/classes` 변경을 빠르게 반영
- 동작:
  - `mvn compile` 및 런타임 의존성 복사
  - `/opt/tomcat/conf/Catalina/localhost/ROOT.xml` 생성
  - `docBase`를 `/opt/tomcat/devapps/webstudy01-root`로 설정
  - `src/main/webapp`, `target/classes`, `target/dev-lib`를 심볼릭 링크로 연결
  - Tomcat `allowLinking=true`, `reloadable=true` 활성화

개발 모드에서는 저장 후 아래 경로로 접근합니다.

- `http://localhost:8080/`
- `http://localhost:8080/index.html`

주의사항:

- Java 변경은 VS Code 자동 빌드로 `target/classes`가 갱신되어야 반영됩니다.
- 클래스 변경은 Tomcat 컨텍스트 reload가 필요할 수 있습니다.
- 개발 모드 설정은 운영 배포 전에 제거해야 합니다.

### 운영용 WAR 배포

- 스크립트: `sudo ./deploy.sh`
- 목적: 개발용 설정을 정리하고 `ROOT.war` 기준으로 배포
- 동작:
  - `mvn clean package -DskipTests`
  - 개발용 `ROOT.xml` 및 개발용 `docBase` 제거
  - `/opt/tomcat/webapps/ROOT.war`로 배포
  - Tomcat 실행 중이면 자동 redeploy, 아니면 시작

로그를 계속 추적하려면 아래처럼 실행합니다.

- `FOLLOW_LOGS=true sudo ./deploy.sh`

## Build and Run

운영 배포는 `./deploy.sh`, 개발용 빠른 반영은 `./init_dev_deploy.sh`를 사용합니다.

`./deploy.sh`

`./init_dev_deploy.sh`

## 접근 URL

### React

- [http://localhost:8080/worldtime.html](http://localhost:8080/worldtime.html) (React로 만든 html, js로 json 불러옴)

### Servlet

#### World time

- [http://localhost:8080/hw01/worldtime](http://localhost:8080/hw01/worldtime) (서블릿에서 html응답, parameter로 locale, timezone 받음)

- [http://localhost:8080/hw01/worldtime/html](http://localhost:8080/hw01/worldtime/html) (서블릿에서 html 응답, vanilla js로 json 불러옴)

- [http://localhost:8080/hw01/worldtime/json](http://localhost:8080/hw01/worldtime/json) (서블릿에서 json 응답, parameter로 locale, timezone 받음)

#### Image list

- [http://localhost:8080/hw02/image](http://localhost:8080/hw02/image) (서블릿에서 image 응답, parameter로 filename 받음)

- [http://localhost:8080/hw02/imageList](http://localhost:8080/hw02/imageList) (서블릿에서 jsp 응답, 이미지 목록 보여줌)

- [http://localhost:8080/hw02/imageList/json](http://localhost:8080/hw02/imageList/json) (서블릿에서 json 응답, 이미지 목록 반환)

#### Converter

- [http://localhost:8080/hw03/convert](http://localhost:8080/hw03/convert) (jsp 응답, parameter로 from, to, value 받음)

- [http://localhost:8080/hw03/convert/json](http://localhost:8080/hw03/convert/json) (json 응답, parameter로 from, to, value 받음)
