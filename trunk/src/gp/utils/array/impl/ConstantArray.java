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
}
