/*
 * Array.java
 *
 * Created on 14 octobre 2007, 15:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
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
