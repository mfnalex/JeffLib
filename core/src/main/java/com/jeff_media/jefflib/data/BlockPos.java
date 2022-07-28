package com.jeff_media.jefflib.data;

import lombok.Getter;

public class BlockPos {

    @Getter private int x, y, z;

    public BlockPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockPos(double x, double y, double z) {
        this.x = (int) x;
        this.y = (int) y;
        this.z = (int) z;
    }

}
