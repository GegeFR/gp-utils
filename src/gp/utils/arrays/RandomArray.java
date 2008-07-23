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

import java.util.Random;

/**
 *
 * @author GegeFR
 */
public class RandomArray extends ReadOnlyArray
{
    private byte[] array;
    
    /** Creates a new instance of Array */
    public RandomArray(int length)
    {
        this.array = new byte[length];
        this.length = this.array.length;
        new Random().nextBytes(this.array);
    }

    // <editor-fold desc=" Array interface " >
    @Override
    protected byte doGet(int i)
    {
        return array[i];
    }

    @Override
    public byte[] getBytes()
    {
        return array;
    }
    
    @Override
    public Array subArray(int offset, int length)
    {
        return new DefaultArray(this.array, offset, length);
    }
    // </editor-fold>
}
