package de.jeff_media.jefflib.data;

/**
 * Represents the 16 cardinal directions (north, north north east, north east, east north east, ...)
 */
public enum CardinalDirection {

    NORTH, NORTH_NORTH_EAST, NORTH_EAST, EAST_NORTH_EAST,
    EAST, EAST_SOUTH_EASE, SOUTH_EAST, SOUTH_SOUTH_EAST,
    SOUTH, SOUTH_SOUTH_WEST, SOUTH_WEST, WEST_SOUTH_WEST,
    WEST, WEST_NORTH_WEST, NORTH_WEST, NORTH_NORTH_WEST;

    private static final double WIDTH = 22.5;

    /**
     * Converts a given yaw value to its cardinal direction
     *
     * @param yaw Yaw
     * @return Corresponding cardinal direction
     */
    public static CardinalDirection fromYaw(double yaw) {
        int index = 0;
        while (true) {
            if (yaw >= -180 - WIDTH / 2 + (index * WIDTH) && yaw <= -180 + WIDTH / 2 + (index * WIDTH)) {
                return fromIndex(index);
            }
            if (index > 16) {
                throw new IllegalStateException();
            }
            index++;
        }
    }

    /**
     * Converts an integer between 0 (inclusive) and 16 (inclusive) to its cardinal direction. 0 and 16 both return NORTH.
     *
     * @param index Index
     * @return Corresponding cardinal direction
     */
    public static CardinalDirection fromIndex(int index) {
        switch (index) {
            case 0:
            case 16:
                return NORTH;
            case 1:
                return NORTH_NORTH_WEST;
            case 2:
                return NORTH_WEST;
            case 3:
                return WEST_NORTH_WEST;
            case 4:
                return WEST;
            case 5:
                return WEST_SOUTH_WEST;
            case 6:
                return SOUTH_WEST;
            case 7:
                return SOUTH_SOUTH_WEST;
            case 8:
                return SOUTH;
            case 9:
                return SOUTH_SOUTH_EAST;
            case 10:
                return SOUTH_EAST;
            case 11:
                return EAST_SOUTH_EASE;
            case 12:
                return EAST;
            case 13:
                return EAST_NORTH_EAST;
            case 14:
                return NORTH_EAST;
            case 15:
                return NORTH_NORTH_EAST;
            default:
                throw new IllegalStateException();
        }
    }

    /**
     * Gets the abbreviated direction name, e.g. "N" for NORTH or "SSE" for SOUTH_SOUTH_EAST
     *
     * @return Abbreviated name
     */
    public String getShortName() {
        switch (this) {
            case SOUTH_SOUTH_EAST:
                return "SSE";
            case SOUTH_EAST:
                return "SE";
            case EAST_SOUTH_EASE:
                return "ESE";
            case EAST:
                return "E";
            case EAST_NORTH_EAST:
                return "ENE";
            case NORTH_EAST:
                return "NE";
            case NORTH_NORTH_EAST:
                return "NNE";
            case NORTH:
                return "N";
            case NORTH_NORTH_WEST:
                return "NNW";
            case NORTH_WEST:
                return "NW";
            case WEST_NORTH_WEST:
                return "WNW";
            case WEST:
                return "W";
            case WEST_SOUTH_WEST:
                return "WSW";
            case SOUTH_WEST:
                return "SW";
            case SOUTH_SOUTH_WEST:
                return "SSW";
            default:
                return "S";
        }
    }

}
