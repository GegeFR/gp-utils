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
public class Integer16Array extends DirectMappingArray
{
    public Integer16Array(int value)
    {
        super(new DefaultArray(2));
        this.setValue(value);
    }

    public Integer16Array(Array array)
    {
        super(array);
        
        if(array.length != 2)
        {
            throw new ArrayLengthException("Integer16Array must have a size of 2");
        }
    }
    
    public void setValue(int value)
    {
        this.doSet(0, (byte) (value >> 8) & 0xFF);
        this.doSet(1, (byte) value & 0xFF);
    }
    
    public int getValue()
    {
        int value = 0;
        value += (this.doGet(0) & 0xFF) << 8;
        value += this.doGet(1) & 0xFF;
        return value;
    }
}
