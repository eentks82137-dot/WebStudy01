package kr.or.ddit.hw01;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hw01/worldtime/html")
public class WorldTimeHtmlServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;encoding=UTF-8");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/time.html");
        requestDispatcher.forward(req, resp);
    }
}
