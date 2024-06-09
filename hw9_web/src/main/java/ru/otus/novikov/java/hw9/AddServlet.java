package ru.otus.novikov.java.hw9;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "AddServlet", urlPatterns = "/add")
public class AddServlet extends CalcServlet{
    @Override
    protected String calculate(int first, int second) {
        return String.valueOf(first + second);
    }
}
