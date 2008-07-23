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

import gp.utils.arrays.ArrayLengthException;

/**
 * This calss constructors are protected because only the subArray method should be used as it contains some otimizations.
 * @author GegeFR
 */
public class SubArray extends Array
{
    private Array array;
    
    private int offset;

    protected SubArray(Array array, int offset, int length) throws ArrayIndexOutOfBoundsException
    {
        if(offset < 0 || offset > array.length || offset + length  > array.length)
        {
            throw new ArrayLengthException("Invalid SubArray parameters offset=" + offset + ", length=" + length + ", embedded array length=" + array.length);
        }
        
        this.array = array;
        this.offset = offset;
        this.length = length;
    }
    
    protected SubArray(Array array, int offset) throws ArrayIndexOutOfBoundsException
    {
        if(offset < 0 || offset > array.length)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid SubArray parameters offset=" + offset + ", embedded array length=" + array.length);
        }
        
        this.array = array;
        this.offset = offset;
        this.length = array.length - this.offset;
    }   
    
    // <editor-fold desc=" Array interface " >
    @Override
    protected byte doGet(int i)
    {
        return array.get(offset + i);
    }

    @Override
    protected void doSet(int i, byte value)
    {
        array.set(offset + i, value);
    }
    
    @Override
    protected void doSet(int i, int value)
    {
        array.set(offset + i, value);
    }
    // </editor-fold>
    
    @Override
    public Array subArray(int offset, int length)
    {
        return this.array.subArray(this.offset + offset, length);
    }
    
}
