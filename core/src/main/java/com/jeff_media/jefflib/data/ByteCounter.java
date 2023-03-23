/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib.data;

import lombok.Getter;

import javax.annotation.Nonnull;
import java.io.DataOutput;

/**
 * A simple {@link DataOutput} implementation that counts the number of bytes written and discards the actual data
 */
public class ByteCounter implements DataOutput {

    @Getter
    private int bytes = 0;

    public static long getUTFLength(final @Nonnull String s) {
        long length = 0;
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (c >= 0x0001 && c <= 0x007F) {
                length++;
            } else if (c > 0x07FF) {
                length += 3;
            } else {
                length += 2;
            }
        }
        return length;
    }

    @Override
    public void write(final int i) {
        bytes++;
    }

    @Override
    public void write(final @Nonnull byte[] bytes) {
        this.bytes += bytes.length;
    }

    @Override
    public void write(final @Nonnull byte[] bytes, final int off, final int len) {
        this.bytes += len;
    }

    @Override
    public void writeBoolean(final boolean bool) {
        bytes++;
    }

    @Override
    public void writeByte(final int i) {
        bytes++;
    }

    @Override
    public void writeShort(final int i) {
        bytes += 2;
    }

    @Override
    public void writeChar(final int i) {
        bytes += 2;
    }

    @Override
    public void writeInt(final int i) {
        bytes += 4;
    }

    @Override
    public void writeLong(final long l) {
        bytes += 8;
    }

    @Override
    public void writeFloat(final float f) {
        bytes += 4;
    }

    @Override
    public void writeDouble(final double d) {
        bytes += 8;
    }

    @Override
    public void writeBytes(final String s) {
        bytes += s.length();
    }

    @Override
    public void writeChars(final String s) {
        bytes += s.length() * 2;
    }

    @Override
    public void writeUTF(final String s) {
        bytes += 2 + getUTFLength(s);
    }
}