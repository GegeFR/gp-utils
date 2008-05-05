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
public abstract class Array
{
    public int length;

    public final byte get(int i)
    {
        this.assertValidIndex(i);
        return this.doGet(i);
    }

    public final void set(int i, byte value)
    {
        this.assertValidIndex(i);
        this.doSet(i, value);

    }

    public final void set(int i, int value)
    {
        this.assertValidIndex(i);
        this.doSet(i, value);
    }

    protected abstract byte doGet(int i);
    
    protected abstract void doSet(int i, byte value);
    
    protected abstract void doSet(int i, int value);

    public byte[] getBytes()
    {
        byte[] array = new byte[this.length];
        
        for(int i=0; i<this.length; i++)
        {
            array[i] = this.get(i);
        }
        
        return array;
    }
    
    private final void assertValidIndex(int i)
    {
        if(i< 0 || i >= length)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid index: " + i +" of " + length + " elements");
        }
    }
    
    @Override
    public String toString()
    {
        StringBuffer stringBuffer = new StringBuffer();

        for(int i=0; i<this.length; i++)
        {
            byte val = this.get(i);
            stringBuffer.append(Integer.toHexString((val & 0xFF) >> 4)).append(Integer.toHexString(val & 0x0F));
            if((i + 1) % 16 == 0)
            {
                if(i+1 < this.length) stringBuffer.append("\n");
            }
            else
            {
                if(i+1 < this.length) stringBuffer.append(" ");
            }
        }
        
        return stringBuffer.toString();
    }
    
    @Override
    public boolean equals(Object object)
    {
        if(!(object instanceof Array))
        {
            return false;
        }
        
        Array array = (Array) object;
        
        if(this.length != array.length)
        {
            return false;
        }
        
        for(int i=0; i<this.length; i++)
        {
            if(this.get(i) != array.get(i))
            {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public Array clone()
    {
        return new DefaultArray(this.getBytes());
    }
    
    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
