package ru.otus.novikov.java.hw9;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "DivServlet", urlPatterns = "/div")
public class DivServlet extends CalcServlet {
    @Override
    protected String calculate(int first, int second) {
        return String.valueOf(first / second);
    }
}
