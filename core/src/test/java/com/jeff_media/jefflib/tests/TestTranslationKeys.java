package com.jeff_media.jefflib.tests;

import static org.junit.Assert.assertNotNull;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.jeff_media.jefflib.EntityUtils;
import com.jeff_media.jefflib.UnitTest;
import org.bukkit.entity.EntityType;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTranslationKeys extends UnitTest {

    @Test
    public void testEntityTypeKeys() {
        try {
            // Not implemented in MockBukkit
            for (EntityType type : EntityType.values()) {
                String translationKey = EntityUtils.getTranslationKey(type);
                Assertions.assertNotNull(translationKey, "Translation key for " + type + " is null");
            }
        } catch (UnimplementedOperationException ignored) {
            // Ignore
        }
    }
}
