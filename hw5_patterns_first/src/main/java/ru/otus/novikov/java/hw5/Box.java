package ru.otus.novikov.java.hw5;

import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public final class Box implements Iterable<String>{
    private final List<String> firstList;
    private final List<String> secondList;
    private final List<String> thirdList;
    private final List<String> fourthList;

    @Override
    public Iterator iterator() {
        return new BoxIterator();
    }

    private class BoxIterator implements Iterator{
        private final Iterator<String> iterator1 = firstList.iterator();
        private final Iterator<String> iterator2 = secondList.iterator();
        private final Iterator<String> iterator3 = thirdList.iterator();
        private final Iterator<String> iterator4 = fourthList.iterator();

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
}
