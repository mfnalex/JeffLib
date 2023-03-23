package com.jeff_media.jefflib.tests;

import com.jeff_media.jefflib.CollectionUtils;
import com.jeff_media.jefflib.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TestCollectionUtils extends UnitTest {

    @Test
    public void testSortMapByKey() {
        Map<String,Integer> map = new HashMap<>();
        map.put("b",2);
        map.put("a",1);
        map.put("c",3);
        Map<String,Integer> sortedMap = new HashMap<>();
        sortedMap.put("a",1);
        sortedMap.put("b",2);
        sortedMap.put("c",3);

        Assertions.assertEquals(sortedMap, CollectionUtils.sortByEntry(map));
    }

}
