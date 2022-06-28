package de.jeff_media.jefflib.tests;

import de.jeff_media.jefflib.ArrayUtils;
import de.jeff_media.jefflib.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestArrayUtils extends UnitTest {

    final String[] first = {"0","1","2","3","4"};
    final String[] second = {"0","1","3","4"};
    final String[] third = {"0","1","2","3","4","hello"};
    final String[] combined = {"0","1","2","3","4","0","1","3","4","0","1","2","3","4"};

   @Test
   public void testRemoveAtIndex() {
       Assertions.assertArrayEquals(ArrayUtils.removeAtIndex(first, 2), second);
   }

   @Test
    public void testAddAfter() {
       Assertions.assertArrayEquals(ArrayUtils.addAfter(first,"hello"), third);
   }

   @Test
    public void testCombine() {
       Assertions.assertArrayEquals(combined,ArrayUtils.combine(first,second,first));
   }
}
