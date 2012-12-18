/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package gp.utils.arrays;

/**
 *
 * @author
 * Gwenhael
 * Pasquiers
 */
public abstract class Array {

    public int length;

    public final byte get(int i) {
        this.assertValidIndex(i);
        return this.doGet(i);
    }

    public final void set(int i, byte value) {
        this.assertValidIndex(i);
        this.doSet(i, value);

    }

    public final void set(int i, int value) {
        this.assertValidIndex(i);
        this.doSet(i, value);
    }

    public void setBit(int index, int bitValue) {
        if (bitValue != 0 && bitValue != 1) {
            throw new RuntimeException("invalid bit value (" + bitValue + ", should be 0 or 1)");
        }

        this.assertValidBitIndex(index);

        int arrayIndex = index / 8;
        int bitIndex = index % 8;

        int byteValue = this.doGet(arrayIndex);

        int mask = 0x80 >> bitIndex;

        if (1 == bitValue) {
            byteValue |= mask;
        }
        else {
            byteValue &= 0xff - mask;
        }

        this.doSet(arrayIndex, byteValue);
    }

    public void setBits(int startIndex, int length, int bitsValue) {
        if (length > 32) {
            throw new RuntimeException("invalid length " + length + ", because bitsValue is an int (4 bytes : 32 bits)");
        }
        else if (length == 32) {
            // full scope of int type, it's ok
        }
        else {
            int maxValue = (1 << length) - 1;
            if (bitsValue > maxValue || bitsValue < 0) {
                throw new RuntimeException("invalid length regarding to bitsValue, length is " + length + " but bitsValue (" + bitsValue + ") is greater than 2^" + length + "-1 (" + maxValue + ")");
            }
        }

        setBitsL(startIndex, length, (long) (bitsValue & 0xffffffffl));
    }

    public void setBitsL(int startIndex, int length, long bitsValue) {

        if (length > 64) {
            throw new RuntimeException("invalid length " + length + ", because bitsValue is a long (8 bytes : 64 bits)");
        }
        else if (length == 64) {
            // full scope of long type, it's ok
        }
        else {
            long maxValue = (1l << length) - 1;
            if (bitsValue > maxValue || bitsValue < 0) {
                throw new RuntimeException("invalid length regarding to bitsValue, length is " + length + " but bitsValue (" + bitsValue + ") is greater than 2^" + length + "-1 (" + maxValue + ")");
            }
        }

        long mask;
        for (int i = length - 1; i >= 0; i--) {
            mask = 1l << length - 1 - i;
            if ((bitsValue & mask) != 0) {
                this.setBit(startIndex + i, 1);
            }
            else {
                this.setBit(startIndex + i, 0);
            }
        }
    }

    public int getBit(int index) {
        this.assertValidBitIndex(index);

        int arrayIndex = index / 8;
        int bitIndex = index % 8;
        int byteValue = this.doGet(arrayIndex);

        return ((byteValue & (0x80 >> bitIndex)) == 0) ? 0 : 1;
    }

    public int getBits(int startIndex, int length) {
        if (length < 0 || length > 32) {
            throw new RuntimeException("invalid length " + length + ", because return type is an int (4 bytes : 32 bits)");
        }

        return (int) getBitsL(startIndex, length);
    }

    public long getBitsL(int startIndex, int length) {
        if (length < 0 || length > 64) {
            throw new RuntimeException("invalid length " + length + ", because return type is a long (8 bytes : 64 bits)");
        }

        long value = 0;

        for (int i = length - 1; i >= 0; i--) {
            value += ((long) this.getBit(startIndex + i)) << (length - 1 - i);
        }
        return value;
    }

    protected abstract byte doGet(int i);

    protected abstract void doSet(int i, byte value);

    protected abstract void doSet(int i, int value);

    protected abstract void doGetBytes(int sourceOffset, byte[] target, int targetOffset, int copyLength);

    public void getBytes(int sourceOffset, byte[] target, int targetOffset, int copyLength) {
        doGetBytes(sourceOffset, target, targetOffset, copyLength);
    }

    public byte[] getBytes() {
        byte[] array = new byte[length];

        doGetBytes(0, array, 0, length);

        return array;
    }

    public Array subArray(int offset) {
        return this.subArray(offset, this.length - offset);
    }

    public Array subArray(int offset, int length) {
        return new SubArray(this, offset, length);
    }

    public int indexOf(Array array) {
        return this.indexOf(array, 0);
    }

    public int indexOf(Array array, int minimum) {
        if (minimum < 0 || minimum >= this.length) {
            throw new ArrayIndexException("invalid start index (" + minimum + ") for array of size " + this.length);
        }

        if (array.length == 0) {
            throw new ArrayIndexException("invalid array (size = 0)");
        }

        for (int i = minimum; i < (this.length - (array.length - 1)); i++) {
            if (this.get(i) == array.get(0)) {
                int candidate = i;
                for (int j = 1; j < array.length; j++) {
                    if (this.get(i + j) != array.get(j)) {
                        candidate = -1;
                        break;
                    }
                }

                if (candidate != -1) {
                    return candidate;
                }
            }
        }

        return -1;
    }

    private final void assertValidIndex(int i) {
        if (i < 0 || i >= length) {
            throw new ArrayIndexOutOfBoundsException("Invalid index: " + i + " of " + length + " elements");
        }
    }

    private final void assertValidBitIndex(int i) {
        if (i < 0 || i >= length * 8) {
            throw new ArrayIndexOutOfBoundsException("Invalid bit index: " + i + " of " + 8 * length + " bits");
        }
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < this.length; i++) {
            byte val = this.get(i);
            stringBuffer.append(Integer.toHexString((val & 0xFF) >> 4)).append(Integer.toHexString(val & 0x0F));
            if ((i + 1) % 16 == 0) {
                if (i + 1 < this.length) {
                    stringBuffer.append("\n");
                }
            }
            else {
                if (i + 1 < this.length) {
                    stringBuffer.append(" ");
                }
            }
        }

        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Array)) {
            return false;
        }

        Array array = (Array) object;

        if (this.length != array.length) {
            return false;
        }

        for (int i = 0; i < this.length; i++) {
            if (this.get(i) != array.get(i)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Array clone() {
        return new DefaultArray(this.getBytes());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static Array fromHexString(String string) {
        if (null == string) {
            throw new IllegalArgumentException("string cannot be null");
        }

        if (string.length() % 2 != 0) {
            throw new IllegalArgumentException("string length is not even");
        }

        DefaultArray array = new DefaultArray(string.length() / 2);

        for (int i = 0; i < array.length; i++) {
            byte value = 0;
            value += charToByte(string.charAt(i * 2)) << 4;
            value += charToByte(string.charAt(i * 2 + 1));
            array.set(i, value);
        }

        return array;
    }

    public static String toHexString(Array array) {
        char[] chars = new char[array.length * 2];

        int i = 0;
        int j = 0;
        while (i < array.length) {
            chars[j] = byteToChar((byte) ((array.get(i) & 0xff) >> 4));
            chars[j + 1] = byteToChar((byte) (array.get(i) & 0x0f));

            i += 1;
            j += 2;
        }

        return new String(chars);
    }

    public static Array fromBase64String(String string) {
        return new DefaultArray(Base64Coder.decode(string));
    }

    public static String toBase64String(Array array) {
        return Base64Coder.encode(array);
    }

    private static byte charToByte(char character) {
        switch (character) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'A':
            case 'a':
                return 10;
            case 'B':
            case 'b':
                return 11;
            case 'C':
            case 'c':
                return 12;
            case 'D':
            case 'd':
                return 13;
            case 'E':
            case 'e':
                return 14;
            case 'F':
            case 'f':
                return 15;
            default:
                throw new IllegalArgumentException("unknown char " + character);
        }
    }

    private static char byteToChar(byte byteValue) {
        switch (byteValue) {
            case 0:
                return '0';
            case 1:
                return '1';
            case 2:
                return '2';
            case 3:
                return '3';
            case 4:
                return '4';
            case 5:
                return '5';
            case 6:
                return '6';
            case 7:
                return '7';
            case 8:
                return '8';
            case 9:
                return '9';
            case 10:
                return 'a';
            case 11:
                return 'b';
            case 12:
                return 'c';
            case 13:
                return 'd';
            case 14:
                return 'e';
            case 15:
                return 'f';
            default:
                throw new IllegalArgumentException("invalid value " + byteValue);
        }
    }
}
