package kr.or.ddit.servlet01;

import java.io.IOException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * [서블릿 스펙]
 * 
 * 웹상에서 발생한 요청을 수신하고, 일정한 처리르 수행한 후, 동적인 응답을 생성할 수 있는 자바 객체에 대한 명세서.
 * 
 * - 자바 기반의 백엔드 모듈로 자바라는 언어의 특성을 그대로 사용할 수 있음.
 * - 확장 CGI(Common Gateway Interface) 방식의 구조를 갖고 있어서
 * 하나의 요청을 하나의 쓰레드로 처리하는 멀티 쓰레딩 구조를 형성할 수 있음
 * 
 * [개발 단계]
 * 1. HttpServlet 구현체 정의
 * 
 * 2. CallBack 메소드 오버라이딩
 * - 생명주기 콜백 : init, destroy
 * - - 컨테이너는 일반적으로 관리 대상인 객체를 싱글톤으로 운영함.
 * - 요청 콜백 : service, doGet, doPost 등
 * - - 매 요청 발생시 service 메소드가 호출되고,
 * service 메소드 내에서 요청의 HTTP method에 따라 doGet, doPost 등의 메소드가 호출됨.
 * - - super.doGet, super.doPost 등의 메소드를 제거(오버라이딩)하여 요청에 대한 처리를 직접 구현할 수 있음.
 * 
 * 컨테이너에 의해 서블릿이 관리되므로, 그 관리 정책을 결정할 때 등록 시에 사용되는 설정으로 제어함.
 * ex) 객체 생성 시점을 결정하는 loadOnStartup 속성
 * - lazy-loading: 객체가 사용되는 시점까지 객체 생성을 지연하는 방식
 * - eager-loading: 객체가 사용되기 전에 미리 객체를 생성하는 방식
 * ex) 객체 생성 시점에 전달하는 파라미터들.. params
 * 
 * 3. 서블릿 컨테이너에 등록
 * - web.xml에 서블릿 등록 (servlet -> servlet-name, servlet-class)
 * - @WebServlet 어노테이션을 이용한 서블릿 등록
 * 
 * 4. 서블릿의 동작 조건이 되는 url 매핑
 * - web.xml에 url 매핑 (servlet-mapping -> servlet-name, url-pattern)
 * - @WebServlet 어노테이션의 urlPatterns 속성을 이용한 url 매핑
 * 
 */

public class DescriptionServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println(this.getClass().getName() + " 객체 초기화 완료");
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("service 메소드 동작 시작");
        super.service(req, resp);
        System.out.println("service 메소드 동작 종료");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DoGet 메소드 동작");
    }

    @Override
    public void destroy() {
        System.out.println(this.getClass().getName() + " 객체 소멸");
    }
}
