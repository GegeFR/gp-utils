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
 * @author Gwenhael Pasquiers
 */
public class BitArray extends DirectMappingArray
{
    public BitArray(Array array)
    {
        super(array);
    }
    
    public void setBitFromLeft(int index, int bitValue)
    {
        if(bitValue != 0 && bitValue != 1) throw new RuntimeException("bitValue should be 0 or 1");

        if(index/8 >= this.length) throw new RuntimeException("bit index too high ("+index+" from left)");

        int arrayIndex = index/8;

        int byteValue = this.get(arrayIndex);

        int bitIndex = index % 8;

        int mask = 0x80 >> bitIndex;

        if(1 == bitValue) byteValue |=  mask;
        else byteValue &= 0xff - mask;

        this.set(arrayIndex, byteValue);
    }

    public void setBitFromRight(int index, int bitValue)
    {
        if(index/8 >= this.length) throw new RuntimeException("bit index too high ("+index+" from left)");
        this.setBitFromLeft(8*this.length - 1 - index, bitValue);
    }

    public void setBitsFromLeft(int startIndex, int length, int bitsValue)
    {
        if(0 <= bitsValue && bitsValue > Math.pow(2, startIndex + length) -1) throw new RuntimeException("bitsValue negative or too high; it shoud be at most " + (Math.pow(2, startIndex + length) -1));

        for(int i=0; i<length; i++)
        {
            this.setBitFromLeft(startIndex + length - 1 - i, (bitsValue & (1 << i))>>i);
        }
    }

    public void setBitsFromRight(int startIndex, int length, int bitsValue)
    {
        this.setBitsFromLeft(8*this.length - startIndex - length, length, bitsValue);
    }
}
