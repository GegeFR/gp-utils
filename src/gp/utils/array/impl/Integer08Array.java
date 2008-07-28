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
public class Integer08Array extends DirectMappingArray
{
    public Integer08Array(int value)
    {
        super(new DefaultArray(1));
        this.setValue(value);
    }

    public Integer08Array(Array array)
    {
        super(array);
        if(array.length != 1)
        {
            throw new ArrayLengthException("Integer08Array must have a size of 1");
        }
    }
    
    public void setValue(int value)
    {
        this.doSet(0, (byte) value & 0xFF);
    }
    
    public int getValue()
    {
        return this.doGet(0) & 0xFF;
    }
}
