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

package gp.utils.array.impl;


/**
 *
 * @author gege
 */
public class PaddingArray extends ReadOnlyArray
{
    private Array data;
    
    private byte paddedValue;
    
    public PaddingArray(Array data, byte paddedValue, int paddedLength)
    {
        this.data = data;
        this.length = paddedLength;
        this.paddedValue = paddedValue;
    }

    @Override
    public byte doGet(int i)
    {
        if(i >= data.length)
        {
            return this.paddedValue;
        }
        else
        {
            return this.data.get(i);
        }
    }
}
