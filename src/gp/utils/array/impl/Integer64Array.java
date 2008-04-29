/*
 * Array.java
 *
 * Created on 14 octobre 2007, 15:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package gp.utils.array.impl;

import gp.utils.exception.ArrayLengthException;


/**
 *
 * @author GegeFR
 */
public class Integer64Array extends DirectMappingArray
{
    public Integer64Array(long value)
    {
        super(new DefaultArray(8));
        this.setValue(value);
    }

    public Integer64Array(Array array)
    {
        super(array);
        if(array.length != 8)
        {
            throw new ArrayLengthException("Integer64Array must have a size of 8");
        }
    }
    
    public void setValue(long value)
    {
        this.doSet(0, (byte) (value >> 56) & 0xFF);
        this.doSet(1, (byte) (value >> 48) & 0xFF);
        this.doSet(2, (byte) (value >> 40) & 0xFF);
        this.doSet(3, (byte) (value >> 32) & 0xFF);
        this.doSet(4, (byte) (value >> 24) & 0xFF);
        this.doSet(5, (byte) (value >> 16) & 0xFF);
        this.doSet(6, (byte) (value >> 8) & 0xFF);
        this.doSet(7, (byte) value & 0xFF);
    }
    
    public long getValue()
    {
        long value = 0;
        value += ((long) this.doGet(0) & 0xFF) << 56;
        value += ((long) this.doGet(1) & 0xFF) << 48;
        value += ((long) this.doGet(2) & 0xFF) << 40;
        value += ((long) this.doGet(3) & 0xFF) << 32;
        value += ((long) this.doGet(4) & 0xFF) << 24;
        value += ((long) this.doGet(5) & 0xFF) << 16;
        value += ((long) this.doGet(6) & 0xFF) << 8;
        value += (long) this.doGet(7) & 0xFF;
        return value;
    }
}
