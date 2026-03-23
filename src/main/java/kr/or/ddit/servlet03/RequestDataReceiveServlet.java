package kr.or.ddit.servlet03;

import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.util.Arrays;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.servlet03.dto.DummyRequest;

@WebServlet("/03/request-data")
public class RequestDataReceiveServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // request line : URL, Method, Protocol
        String uri = req.getRequestURI();
        String method = req.getMethod();
        String protocol = req.getProtocol();
        System.out.println("uri : %s, method: %s, protocol: %s".formatted(uri, method, protocol));
        String queryString = req.getQueryString();
        System.out.println("query string : %s".formatted(queryString));

        // request header : accept-*, content-*, user-agent
        // String accept = req.getHeader("Accept");
        // Locale acceptLanguage = req.getLocale();
        String contentType = req.getContentType();
        long contentLength = req.getContentLengthLong();
        // String userAgent = req.getHeader("User-Agent");
        System.out.println("content-length: %d, content-type: %s".formatted(contentLength, contentType));

        // request body
        // url encoding 된 문자열 파라미터 집합: name=%EB%B0%B1%EB%91%90%EC%82%B0&age=25
        // json payload 형태: {"name":"백두산","age":"25"}
        Map<String, String[]> parameterMap = req.getParameterMap();
        parameterMap.forEach((k, values) -> {
            System.out.printf("key: %s, values: %s\n", k, Arrays.toString(values));
        });
        System.out.println("===================BODY===================");
        // req.getReader().lines().forEach(System.out::println);

        // if ("post".equalsIgnoreCase(method) && contentType.contains("json")) {
        // receiveJsonPayload(req);
        // } else if ("post".equalsIgnoreCase(method) &&
        // contentType.contains("urlencoded")) {
        // receiveParameters(req);
        // } else if ("get".equalsIgnoreCase(method)) {
        // receiveParameters(req);
        // }

    }

    /**
     * 파라미터 맵을 수신하고, 콘솔에 출력
     * 1. Get + Query String
     * 2. Post + Url Encoded Content Type
     */

    private void receiveParameters(HttpServletRequest req) {
        String name = req.getParameter("name");
        String ageParam = req.getParameter("age");
        int age = Integer.parseInt(ageParam);

        DummyRequest reqDto = new DummyRequest(name, age);
        System.out.println("수신한 json 객체: %s".formatted(reqDto));
    }

    /**
     * Json payload를 수신하고 콘솔에 출력
     * 1. Post + Json Content Type
     * 
     * @throws IOException
     * @throws JsonIOException
     * @throws JsonSyntaxException
     */

    private void receiveJsonPayload(HttpServletRequest req) throws JsonSyntaxException, JsonIOException, IOException {
        // json 수신 -> 역직렬화 -> java object
        DummyRequest reqDto = new Gson().fromJson(req.getReader(), DummyRequest.class);
        System.out.println("수신한 json 객체: %s".formatted(reqDto));
    }

}
