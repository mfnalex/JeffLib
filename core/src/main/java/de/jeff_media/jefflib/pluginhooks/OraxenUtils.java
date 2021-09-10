package de.jeff_media.jefflib.pluginhooks;

import io.th0rgal.oraxen.mechanics.Mechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanicFactory;
import lombok.experimental.UtilityClass;

@Deprecated
@UtilityClass
public class OraxenUtils {

    public static boolean isBlock(String blockId) {
        final NoteBlockMechanicFactory factory = NoteBlockMechanicFactory.getInstance();
        final Mechanic mechanic = factory.getMechanic(blockId);
        return mechanic != null;
    }
}
