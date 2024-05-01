package ru.otus.novikov.java.hw5;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BoxIteratorTest {

    @Test
    public void boxIteratorTest() {
        // Test data
        List<String> secondList = Arrays.asList("second1", "second2");
        List<String> thirdList = List.of("third1", "third2");
        List<String> fourthList = List.of("fourth1", "fourth2");
        List<String> commonList = Stream.of(secondList,thirdList,fourthList)
            .flatMap(Collection::stream).toList();

        Box box = Box.builder()
            .firstList(null)
            .secondList(secondList)
            .thirdList(thirdList)
            .fourthList(fourthList)
            .build();

        // changing secondList first item
        secondList.set(0, "newSecond");

        List<String> listFromBoxIterator = new ArrayList<>();

        for(String str: box) {
            listFromBoxIterator.add(str);
        }

        assertThat(commonList).isEqualTo(listFromBoxIterator);
    }
}
