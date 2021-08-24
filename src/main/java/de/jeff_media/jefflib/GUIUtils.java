package de.jeff_media.jefflib;

import lombok.Getter;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Validate;

/**
 * GUI related methods
 */
public class GUIUtils {

    /**
     * Converts a slot number to a {@link MenuPosition}
     *
     * @param slot slot number
     * @return corresponding {@link MenuPosition}
     */
    public static MenuPosition slotToPosition(final int slot) {
        Validate.inclusiveBetween(0, 54, slot);
        int row = Math.floorDiv(slot, 9);
        int column = row * 9;
        return new MenuPosition(row + 1, column + 1);
    }

    /**
     * Converts a {@link MenuPosition} to a slot number
     *
     * @param position MenuPosition
     * @return slut number
     */
    public static int positionToSlot(final MenuPosition position) {
        return position.getRow() * 9 - 9 + position.getColumn() - 1;
    }

    /**
     * Represents a position inside a GUI
     */
    public static class MenuPosition {
        @Getter
        private final int row;

        @Getter
        private final int column;

        public MenuPosition(int row, int column) {
            Validate.inclusiveBetween(1, 6, row);
            Validate.inclusiveBetween(1, 9, column);
            this.row = row;
            this.column = column;
        }
    }

}
