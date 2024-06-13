package ru.otus.novikov.java.hw9;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "MultiplyServlet", urlPatterns = "/multiply")
public class MultiplyServlet extends CalcServlet{
    @Override
    protected String calculate(int first, int second) {
        return String.valueOf(first * second);
    }
}
