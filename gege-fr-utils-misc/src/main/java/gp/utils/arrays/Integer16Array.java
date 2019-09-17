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
public class Integer16Array extends DirectMappingArray
{
    public Integer16Array(int value)
    {
        super(new DefaultArray(2));
        this.setValue(value);
    }

    public Integer16Array(Array array)
    {
        super(array);
        
        if(array.length != 2)
        {
            throw new ArrayLengthException("Integer16Array must have a size of 2");
        }
    }
    
    public void setValue(int value)
    {
        this.doSet(0, (byte) (value >> 8) & 0xFF);
        this.doSet(1, (byte) value & 0xFF);
    }
    
    public int getValue()
    {
        int value = 0;
        value += (this.doGet(0) & 0xFF) << 8;
        value += this.doGet(1) & 0xFF;
        return value;
    }
}
