package kr.or.ddit.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <h3>서블릿 필터</h3>
 * 
 * <p>
 * 요청에 대한 전처리나 응답에 대한 후처리를 담당하는 재활용 가능한 객체
 * </p>
 * 
 * <p>
 * 컨테이너에 의해 생명주기가 관리됨
 * </p>
 * 
 * <ul>
 * <li>생명주기 콜백: init, destroy</li>
 * <li>요청 콜백: doFilter</li>
 * </ul>
 * 
 */

public class CharacterEncodingFilter extends HttpFilter {
    private String encoding;

    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init();
        StringBuilder sb = new StringBuilder();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        formatter.localizedBy(Locale.KOREA);
        String formattedNow = now.format(formatter);

        sb.append("""
                ┌──────────────────────────────────────────────────────────────────────────────────────────────┐
                │                                                                                              │
                │  ██╗    ██╗███████╗██████╗     ███████╗████████╗██╗   ██╗██████╗ ██╗   ██╗     ██████╗  ██╗  │
                │  ██║    ██║██╔════╝██╔══██╗    ██╔════╝╚══██╔══╝██║   ██║██╔══██╗╚██╗ ██╔╝    ██╔═████╗███║  │
                │  ██║ █╗ ██║█████╗  ██████╔╝    ███████╗   ██║   ██║   ██║██║  ██║ ╚████╔╝     ██║██╔██║╚██║  │
                │  ██║███╗██║██╔══╝  ██╔══██╗    ╚════██║   ██║   ██║   ██║██║  ██║  ╚██╔╝      ████╔╝██║ ██║  │
                │  ╚███╔███╔╝███████╗██████╔╝    ███████║   ██║   ╚██████╔╝██████╔╝   ██║       ╚██████╔╝ ██║  │
                │   ╚══╝╚══╝ ╚══════╝╚═════╝     ╚══════╝   ╚═╝    ╚═════╝ ╚═════╝    ╚═╝        ╚═════╝  ╚═╝  │
                │                                                                                              │
                └──────────────────────────────────────────────────────────────────────────────────────────────┘
                """).append(formattedNow);
        System.out.println(sb);

        encoding = config.getInitParameter("encoding");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        req.setCharacterEncoding(encoding);
        res.setCharacterEncoding(encoding);

        chain.doFilter(req, res);
    }

}
