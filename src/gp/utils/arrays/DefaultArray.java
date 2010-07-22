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
public class DefaultArray extends Array
{
    private byte[] array;
    
    private int offset;
    
    
    /** Creates a new instance of Array */
    public DefaultArray(byte[] array, int offset, int length)
    {
        if(offset < 0 || offset > array.length || offset + length  > array.length)
        {
            throw new ArrayLengthException("Invalid DefaultArray parameters offset=" + offset + ", length=" + length + ", embedded array length=" + array.length);
        }
        
        this.array = array;
        this.length = length;
        this.offset = offset;
    }

    public DefaultArray(byte[] array)
    {
        this(array, 0, array.length);
    }

    public DefaultArray(int length)
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
    protected void doSet(int i, byte value)
    {
        array[this.offset + i] = value;
    }

    @Override
    protected void doSet(int i, int value)
    {
        array[this.offset + i] = (byte) (value & 0xFF);
    }

    @Override
    protected void doGetBytes(int sourceOffset, byte[] target, int targetOffset, int copyLength)
    {
        System.arraycopy(array, offset + sourceOffset, target, targetOffset, copyLength);
    }

    @Override
    public byte[] getBytes(){
        if(this.offset != 0 || this.length != this.array.length)
        {
            return super.getBytes();
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
        return new DefaultArray(this.array, this.offset + offset, length);
    }

}
