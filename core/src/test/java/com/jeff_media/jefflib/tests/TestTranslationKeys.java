package com.jeff_media.jefflib.tests;

import static org.junit.Assert.assertNotNull;
import com.jeff_media.jefflib.EntityUtils;
import com.jeff_media.jefflib.UnitTest;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;

public class TestTranslationKeys extends UnitTest {

    @Test
    public void testEntityTypeKeys() {
        // Not implemented in MockBukkit
//        for(EntityType type : EntityType.values()) {
//            String translationKey = EntityUtils.getTranslationKey(type);
//            assertNotNull( "Translation key for " + type + " is null", translationKey);
//        }
    }
}
