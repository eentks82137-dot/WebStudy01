package kr.or.ddit.hw03;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import java.util.Locale;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hw03/convert")
public class ConvertHW extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accept = req.getHeader("Accept");
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String value = req.getParameter("value");
        Locale locale = req.getLocale();

        double convertedValue = 0;

        try {
            convertedValue = UnitConverter.converter(from, to, value);
        } catch (IllegalArgumentException e) {
            writeErrorResponse(resp, accept, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }

        String formattedValue = UnitConverter.format(convertedValue, locale);

        if (accept.equals("") || accept.contains("text/html")) {
            resp.setContentType("text/html");
            req.setAttribute("from", from);
            req.setAttribute("to", to);
            req.setAttribute("value", value);
            req.setAttribute("result", convertedValue);
            req.setAttribute("formattedResult", formattedValue);
            req.setAttribute("locale", locale);
            String view = "/WEB-INF/views/hw03/unit-converter.jsp";
            req.getRequestDispatcher(view).forward(req, resp);
        } else if (accept != null && accept.contains("application/json")) {
            resp.setContentType("application/json");
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("from", from);
            body.put("to", to);
            body.put("value", value);
            body.put("result", convertedValue);
            body.put("formattedResult", formattedValue);
            body.put("locale", locale);

            try (PrintWriter out = resp.getWriter()) {
                out.print(new Gson().toJson(body));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            writeErrorResponse(resp, accept, HttpServletResponse.SC_NOT_ACCEPTABLE, "지원하지 않는 Accept 헤더입니다");
            return;
        }
    }

    private void writeErrorResponse(HttpServletResponse resp, String accept, int statusCode, String message)
            throws IOException {
        resp.setStatus(statusCode);

        if (accept != null && accept.contains("application/json")) {
            resp.setContentType("application/json;charset=UTF-8");
            Map<String, Object> errorBody = new LinkedHashMap<>();
            errorBody.put("error", getErrorText(statusCode));
            errorBody.put("status", statusCode);
            errorBody.put("message", message);
            resp.getWriter().write(new Gson().toJson(errorBody));
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("<h1>Error</h1><h2>" + statusCode + "</h2><p>" + message + "</p>");
    }

    private String getErrorText(int statusCode) {
        if (statusCode == HttpServletResponse.SC_BAD_REQUEST) {
            return "Bad Request";
        }

        if (statusCode == HttpServletResponse.SC_NOT_ACCEPTABLE) {
            return "Not Acceptable";
        }

        return "Error";
    }
}
