package ru.otus.novikov.java.hw5;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public final class Box implements Iterable<String> {
    private final List<String> firstList = new ArrayList<>();
    private final List<String> secondList = new ArrayList<>();
    private final List<String> thirdList = new ArrayList<>();
    private final List<String> fourthList = new ArrayList<>();

    public Box(List<String> firstList, List<String> secondList, List<String> thirdList, List<String> fourthList) {
        this.firstList.addAll(firstList == null ? List.of() : firstList);
        this.secondList.addAll(secondList == null ? List.of() : secondList);
        this.thirdList.addAll(thirdList == null ? List.of() : thirdList);
        this.fourthList.addAll(fourthList == null ? List.of() : fourthList);
    }

    @Override
    public Iterator<String> iterator() {
        return new BoxIterator(firstList, secondList, thirdList, fourthList);
    }

    public static BoxBuilder builder() {
        return new BoxBuilder();
    }

    private static class BoxIterator implements Iterator<String> {
        private final Iterator<String> iterator1;
        private final Iterator<String> iterator2;
        private final Iterator<String> iterator3;
        private final Iterator<String> iterator4;

        public BoxIterator(List<String> firstList, List<String> secondList, List<String> thirdList, List<String> fourthList) {
            this.iterator1 = firstList.iterator();
            this.iterator2 = secondList.iterator();
            this.iterator3 = thirdList.iterator();
            this.iterator4 = fourthList.iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator1.hasNext() || iterator2.hasNext() || iterator3.hasNext() || iterator4.hasNext();
        }

        @Override
        public String next() {
            if (iterator1.hasNext()) {
                return iterator1.next();
            } else if (iterator2.hasNext()) {
                return iterator2.next();
            } else if (iterator3.hasNext()) {
                return iterator3.next();
            } else if (iterator4.hasNext()) {
                return iterator4.next();
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class BoxBuilder {
        private List<String> firstList;
        private List<String> secondList;
        private List<String> thirdList;
        private List<String> fourthList;

        public BoxBuilder firstList(List<String> firstList) {
            this.firstList = firstList;
            return this;
        }

        public BoxBuilder secondList(List<String> secondList) {
            this.secondList = secondList;
            return this;
        }

        public BoxBuilder thirdList(List<String> thirdList) {
            this.thirdList = thirdList;
            return this;
        }

        public BoxBuilder fourthList(List<String> fourthList) {
            this.fourthList = fourthList;
            return this;
        }

        public Box build() {
            return new Box(this.firstList, this.secondList, this.thirdList, this.fourthList);
        }
    }
}
