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

import gp.utils.exception.ArrayLengthException;

/**
 *
 * @author GegeFR
 */
public class ReadOnlyDefaultArray extends ReadOnlyArray
{
    private byte[] array;
    
    private int offset;
    
    
    /** Creates a new instance of Array */
    public ReadOnlyDefaultArray(byte[] array, int offset, int length)
    {
        if(offset < 0 || offset > array.length || offset + length  > array.length)
        {
            throw new ArrayLengthException("Invalid ReadOnlyDefaultArray parameters offset=" + offset + ", length=" + length + ", embedded array length=" + array.length);
        }
        
        this.array = array;
        this.length = length;
        this.offset = offset;
    }
    
    /** Creates a new instance of Array */
    public ReadOnlyDefaultArray(byte[] array)
    {
        this(array, 0, array.length);
    }

    public ReadOnlyDefaultArray(int length)
    {
        this(new byte[length]);
    }

    // <editor-fold desc=" Array interface " >
    @Override
    protected byte doGet(int i)
    {
        return array[this.offset + i];
    }

    @Override
    public byte[] getBytes()
    {
        return array;
    }
    // </editor-fold>
    
    @Override
    public Array subArray(int offset, int length)
    {
        return new ReadOnlyDefaultArray(this.array, this.offset + offset, length);
    }
}
