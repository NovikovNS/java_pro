package ru.otus.novikov.java.hw9;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class CalcServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        int first = Integer.parseInt(req.getParameter("first"));
        int second = Integer.parseInt(req.getParameter("second"));
        String result = calculate(first, second);
        out.println("<html><body><h1>" + String.format("%s", result) + "</h1></body></html>");
        out.close();
    }

    protected abstract String calculate(int first, int second);
}
