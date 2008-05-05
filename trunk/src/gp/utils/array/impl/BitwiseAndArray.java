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
public class BitwiseAndArray extends OperationArray
{
    public BitwiseAndArray(Array array1, Array array2)
    {
        super(array1, array2);
    }


    @Override
    public byte operation(byte byte1, byte byte2)
    {
        return (byte) ((byte1 & byte2) & 0xFF);
    }
}
