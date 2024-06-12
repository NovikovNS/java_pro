package ru.otus.novikov.java.hw10.services;

import java.util.Scanner;

public class IOServiceImpl implements IOService {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public Long readLong() {
        return Long.parseLong(sc.nextLine());
    }

    @Override
    public String readString() {
        return sc.nextLine();
    }

    @Override
    public void outString(String text) {
        System.out.println(text + "\r\n");
    }
}
