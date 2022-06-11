package de.jeff_media.jefflib.tests;

import de.jeff_media.jefflib.McVersion;
import de.jeff_media.jefflib.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestMcVersion extends UnitTest {

    @Test
    public void testIsAtLeast() {

        final McVersion v1_8_8 = new McVersion(1,8,8);
        final McVersion v1_17 = new McVersion(1,17);
        final McVersion v1_18 = new McVersion(1,18);
        final McVersion v1_18_1 = new McVersion(1,18,1);
        final McVersion v1_18_2 = new McVersion(1,18,2);
        final McVersion v1_19 = new McVersion(1,19);

        Assertions.assertEquals(v1_8_8.toString(), "1.8.8");
        Assertions.assertEquals(v1_19.toString(), "1.19");

        Assertions.assertTrue(v1_18.isAtLeast(v1_18));
        Assertions.assertFalse(v1_18.isAtLeast(v1_18_1));
        Assertions.assertTrue(v1_18.isAtLeast(v1_17));

        Assertions.assertEquals(v1_18_2,new McVersion(1,18,2));

        Assertions.assertEquals(McVersion.current(), v1_18);
    }

    @Test
    public void testComparable() {

        final McVersion v1_8_8 = new McVersion(1,8,8);
        final McVersion v1_18 = new McVersion(1,18);
        final McVersion v1_18_2 = new McVersion(1,18,2);
        final McVersion v1_19 = new McVersion(1,19);

        final List<McVersion> unsorted = Arrays.asList(v1_18, v1_8_8, v1_19, v1_18_2);
        final McVersion[] sorted = new ArrayList<>(unsorted).stream().sorted().toArray(McVersion[]::new);
        Assertions.assertArrayEquals(sorted, new McVersion[] {v1_8_8, v1_18, v1_18_2, v1_19});
    }
}
