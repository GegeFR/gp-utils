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
public abstract class OperationArray extends ReadOnlyArray
{
    private Array array1;
    private Array array2;
    
    public OperationArray(Array array1, Array array2)
    {
        if(array1.length != array2.length)
        {
            throw new ArrayIndexOutOfBoundsException("Size error: array1=" + array1.length + " array2=" + array2.length + ", must be equal");
        }
        
        this.length = array1.length;
        
        this.array1 = array1;
        this.array2 = array2;
    }
    
    @Override
    protected byte doGet(int i)
    {
        return this.operation(this.array1.get(i), this.array2.get(i));
    }

    @Override
    protected void doGetBytes(byte[] container, int offset, int length)
    {
        for(int i=0; i<length; i++){
            container[i + offset] = this.operation(this.array1.get(i), this.array2.get(i));
        }
    }

    public abstract byte operation(byte byte1, byte byte2);
}
