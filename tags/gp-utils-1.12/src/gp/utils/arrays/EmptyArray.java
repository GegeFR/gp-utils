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
public class EmptyArray extends Array
{
    public EmptyArray()
    {
        this.length = 0;
    }

    @Override
    protected byte doGet(int i)
    {
        throw new UnsupportedOperationException("Not supported, this Array is empty.");
    }

    @Override
    protected void doSet(int i, byte value)
    {
        throw new UnsupportedOperationException("Not supported, this Array is empty.");
    }

    @Override
    protected void doSet(int i, int value)
    {
        throw new UnsupportedOperationException("Not supported, this Array is empty.");
    }

    @Override
    protected void doGetBytes(int sourceOffset, byte[] target, int targetOffset, int copyLength)
    {
        if(copyLength >0)
        {
            throw new ArrayLengthException("can't doGetBytes, asked " + copyLength + " bytes , this is an EmptyArray");
        }
        // do nothing
    }
}
