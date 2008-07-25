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

import java.util.ArrayList;


/**
 *
 * @author Gwenhael PasquiersFR
 */
public class SupArray extends Array
{
    private ArrayList<SupArrayPart> arrayList;
    
    
    private boolean locked;
            
    private int currentArrayListIndex;
    
    /** Creates a new instance of SupArray */
    public SupArray()
    {
        this.locked = false;
        this.length = 0;
        this.arrayList = new ArrayList<SupArrayPart>();
        this.currentArrayListIndex = -1;
    }
    
    public SupArray addLast(Array array)
    {
        assertIsNotLocked();

        int start;
        if(0 == this.arrayList.size())
        {
            start = 0;
        }
        else
        {
            SupArrayPart lastArray = this.arrayList.get(this.arrayList.size() - 1);
            start = lastArray.start + lastArray.array.length;
        }
        
        this.arrayList.add(new SupArrayPart(array, start));
        this.length += array.length;
        return this;
    }

    public SupArray addFirst(Array array)
    {
        assertIsNotLocked();
        this.arrayList.add(0, new SupArrayPart(array, 0));
        
        int size = this.arrayList.size();
        for(int i=1; i<size; i++)
        {
            this.arrayList.get(i).start += array.length;
        }
        this.length += array.length;
        return this;
    }
    
    private SupArrayPart arrayForIndex(int index)
    {
        if(index == 0)
        {
            this.currentArrayListIndex = 0;
        }
        
        if(index == this.length - 1)
        {
            this.currentArrayListIndex = this.arrayList.size() - 1;
        }
        
        SupArrayPart supArrayPart = arrayList.get(currentArrayListIndex);
        while(!(index >= supArrayPart.start && index < supArrayPart.start + supArrayPart.array.length))
        {
            if(index < supArrayPart.start)
            {
                this.currentArrayListIndex--;
            }
            else
            {
                this.currentArrayListIndex++;
            }
            supArrayPart = arrayList.get(currentArrayListIndex);
        }
                
        return supArrayPart;
    }
    
    // <editor-fold desc=" Array interface " >
    @Override
    protected byte doGet(int i)
    {
        SupArrayPart supArrayPart = arrayForIndex(i);
        return supArrayPart.array.get(i - supArrayPart.start);
    }
    
    @Override
    protected void doSet(int i, byte value)
    {
        SupArrayPart supArrayPart = arrayForIndex(i);
        supArrayPart.array.set(i - supArrayPart.start, value);
    }
    
    @Override
    protected void doSet(int i, int value)
    {
        SupArrayPart supArrayPart = arrayForIndex(i);
        supArrayPart.array.set(i - supArrayPart.start, value);
    }
    // </editor-fold>
                 
    private void assertIsNotLocked()
    {
        if(true == this.locked)
        {
            throw new RuntimeException("Can't add Array to SupArray. It is now locked.");
        }
    }
    
    @Override
    public Array subArray(int offset, int length)
    {
        this.locked = true;
        SupArrayPart supArrayPart = arrayForIndex(offset);
        if(length <= supArrayPart.array.length - (offset - supArrayPart.start))
        {
            return supArrayPart.array.subArray(offset, length);
        }
        else
        {
            return super.subArray(offset, length);
        }
    }
    
    private class SupArrayPart
    {
        public int start;
        public Array array;
        
        public SupArrayPart(Array array, int start)
        {
            this.start = start;
            this.array = array;
        }
    }
}
