package com.jeff_media.jefflib.tests;

import com.jeff_media.jefflib.ProfileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestProfileUtils {

    @Test
    public void testValidProfileName() {
        Assertions.assertTrue(ProfileUtils.isValidAccountName("Jeff"));
        Assertions.assertFalse(ProfileUtils.isValidAccountName("Jeff#1234"));
        Assertions.assertFalse(ProfileUtils.isValidAccountName("ThisNameIsTooLongToBeAMinecractAccount"));
    }

    @Test
    public void testUuidUtils() {
        Assertions.assertTrue(ProfileUtils.isValidUUID("00000000-0000-0000-0000-000000000000"));
        Assertions.assertTrue(ProfileUtils.isValidUUID("00000000000000000000000000000000"));
        Assertions.assertFalse(ProfileUtils.isValidUUID("00000000-0000-0000-0000-00000000000")); // to short
        Assertions.assertFalse(ProfileUtils.isValidUUID("00000000-0000-0000-0000-0000000000000")); // to long
        Assertions.assertFalse(ProfileUtils.isValidUUID("0000000000000000000000000000000")); // to short
        Assertions.assertFalse(ProfileUtils.isValidUUID("000000000000000000000000000000000")); // to long
    }
}
