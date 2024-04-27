package ru.otus.novikov.java.hw5;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class Box implements Iterable<String>{
    private final List<String> firstList;
    private final List<String> secondList;
    private final List<String> thirdList;
    private final List<String> fourthList;

    @Override
    public Iterator iterator() {
        List<String> commonList = Stream.of(firstList,secondList,thirdList,fourthList).flatMap(Collection::stream).toList();
        return new BoxIterator(commonList);
    }

    class BoxIterator implements Iterator{
        List<String> commonList;
        int size;
        int currentElement;

        public BoxIterator(List<String> commonList) {
            this.commonList = commonList;
            this.size = commonList.size();
            this.currentElement = 0;
        }

        @Override
        public boolean hasNext() {
            return currentElement != size;
        }

        @Override
        public String next() {
            currentElement++;
            return commonList.get(currentElement - 1);
        }
    }
}
