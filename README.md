# World time

## Requirements

- Java 21
- Maven 3.9.9
- tomcat 10.1.0 (`/opt/tomcat`)

## Build and Run

./deploy.sh 스크립트를 실행하여 빌드 및 배포를 진행합니다.
/opt/tomcat/webapps/ROOT 디렉토리에 빌드된 파일이 복사됩니다.

`./deploy.sh`

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
