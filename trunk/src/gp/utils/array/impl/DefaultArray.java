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
 * @author GegeFR
 */
public class DefaultArray extends Array
{
    private byte[] array;
    
    /** Creates a new instance of Array */
    public DefaultArray(byte[] array)
    {
        this.array = array;
        this.length = array.length;
    }

    public DefaultArray(int length)
    {
        this(new byte[length]);
    }

    // <editor-fold desc=" Array interface " >
    @Override
    protected byte doGet(int i)
    {
        return array[i];
    }

    @Override
    protected void doSet(int i, byte value)
    {
        array[i] = value;
    }

    @Override
    protected void doSet(int i, int value)
    {
        array[i] = (byte) (value & 0xFF);
    }

    @Override
    public byte[] getBytes()
    {
        return array;
    }

   // </editor-fold>
}
