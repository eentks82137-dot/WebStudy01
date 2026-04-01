package kr.or.ddit.mvc;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 논리적인 뷰 네임으로 부터 실제 뷰를 찾아내는 전략의 추상화
 * 
 * 실제 뷰룰 찾아내는 구체적인 전략은 구현체를 통해 다양하게 표현할 수 있음 (다형성)
 */
public interface ViewResolver {
    public void resolveViewName(String logicalViewName, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;
}
