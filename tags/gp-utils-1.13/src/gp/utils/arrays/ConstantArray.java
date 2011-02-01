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
public class ConstantArray extends ReadOnlyArray
{
    private byte value;
    
    public ConstantArray(byte value, int length)
    {
        this.value = value;
        this.length = length;
    }

    @Override
    protected byte doGet(int i)
    {
        return this.value;
    }

    @Override
    protected void doGetBytes(int sourceOffset, byte[] target, int targetOffset, int copyLength)
    {
        if(sourceOffset + copyLength > length)
        {
            throw new ArrayLengthException("can't doGetBytes, asked bytes from " + sourceOffset + " to " + (sourceOffset + copyLength) + ", array has a size of " + length);
        }
        
        for(int i=0; i<copyLength; i++)
        {
            target[i + targetOffset] = value;
        }
    }
}
