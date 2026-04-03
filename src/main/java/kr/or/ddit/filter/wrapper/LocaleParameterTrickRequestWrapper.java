package kr.or.ddit.filter.wrapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class LocaleParameterTrickRequestWrapper extends HttpServletRequestWrapper {

    public LocaleParameterTrickRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        if ("locale".equalsIgnoreCase(name)) {
            return "ko-KR";
        } else {
            return super.getParameter(name);
        }
    }
}
