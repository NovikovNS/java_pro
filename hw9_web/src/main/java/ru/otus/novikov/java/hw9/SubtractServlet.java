package ru.otus.novikov.java.hw9;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "SubtractServlet", urlPatterns = "/subtract")
public class SubtractServlet extends CalcServlet{
    @Override
    protected String calculate(int first, int second) {
        return String.valueOf(first - second);
    }
}
