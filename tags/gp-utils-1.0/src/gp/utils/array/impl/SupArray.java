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

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author GegeFR
 */
public class SupArray extends Array
{
    private LinkedList<Array> arrays;
    
    private Iterator<Array> iterator;
    
    private Array currentArray;
    
    private int currentOffset;
    
    /** Creates a new instance of SupArray */
    public SupArray()
    {
        this.arrays = new LinkedList<Array>();
        this.length = 0;
        this.iterator = null;
    }
    
    public SupArray addLast(Array array)
    {
        arrays.addLast(array);
        this.iterator = null;
        this.length += array.length;
        return this;
    }

    public SupArray addFirst(Array array)
    {
        this.arrays.addFirst(array);
        this.iterator = null;
        this.length += array.length;
        return this;
    }
    
    private Array arrayForIndex(int index)
    {
        if(null == this.iterator)
        {
            this.currentOffset = 0;
            
            if(0 == this.length)
            {
                return new ConstantArray((byte) 0 ,0);
            }
            else
            {
                this.iterator = this.arrays.iterator();
                this.currentArray = iterator.next();
            }
        }
        
        //System.out.println(Integer.toHexString(this.hashCode()) +"bef index = " + index + ", currentOffset = " + this.currentOffset + ", currentLength" + this.currentArray.length);
                    
        if(index >= this.currentOffset && index < this.currentOffset + this.currentArray.length)
        {
            return this.currentArray;
        }
        
        if(index < this.currentOffset)
        {
            this.currentOffset = 0;
            this.iterator = this.arrays.iterator();
            this.currentArray = iterator.next();
        }
        
        while(index >= this.currentOffset + this.currentArray.length)
        {
            if(!iterator.hasNext())
            {
                throw new ArrayIndexOutOfBoundsException("No Array in list for index:" + index +", length:" + length);
            }
            this.currentOffset += this.currentArray.length;
            this.currentArray = iterator.next();
        }
        
        return this.currentArray;
    }
    
    // <editor-fold desc=" Array interface " >
    @Override
    protected byte doGet(int i)
    {
        return arrayForIndex(i).get(i - this.currentOffset);
    }
    
    @Override
    protected void doSet(int i, byte value)
    {
        arrayForIndex(i).set(i - this.currentOffset, value);
    }
    
    @Override
    protected void doSet(int i, int value)
    {
        arrayForIndex(i).set(i - this.currentOffset, value);
    }
    // </editor-fold>
    
}
