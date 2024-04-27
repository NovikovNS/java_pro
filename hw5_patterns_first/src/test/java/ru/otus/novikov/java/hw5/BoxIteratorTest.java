package ru.otus.novikov.java.hw5;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BoxIteratorTest {
    @Test
    public void boxIteratorTest() {
        List<String> firstList = List.of("first1", "first2");
        List<String> secondList = List.of("second1", "second2");
        List<String> thirdList = List.of("third1", "third2");
        List<String> fourthList = List.of("fourth1", "fourth2");
        List<String> commonList = Stream.of(firstList,secondList,thirdList,fourthList).flatMap(Collection::stream).toList();


        Box box = new Box(
            firstList,
            secondList,
            thirdList,
            fourthList
        );

        List<String> iteratorList = new ArrayList<>();

        for(String str: box) {
            iteratorList.add(str);
        }

        assertThat(commonList).isEqualTo(iteratorList);
    }
}
