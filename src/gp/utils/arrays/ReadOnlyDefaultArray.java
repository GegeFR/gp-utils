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
        if(this.offset != 0 || this.length != this.array.length)
        {
            byte[] bytes = new byte[this.length];
            System.arraycopy(this.array, this.offset, bytes, 0, this.length);
            return bytes;
        }
        else
        {
            return this.array;
        }
    }
    // </editor-fold>
    
    @Override
    public Array subArray(int offset, int length)
    {
        return new ReadOnlyDefaultArray(this.array, this.offset + offset, length);
    }

    @Override
    protected void doGetBytes(byte[] container, int offset, int length)
    {
        System.arraycopy(array, 0, container, offset, length);
    }
}
