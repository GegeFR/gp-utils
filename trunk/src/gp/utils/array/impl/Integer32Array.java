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
public class Integer32Array extends DirectMappingArray
{
    public Integer32Array(int value)
    {
        super(new DefaultArray(4));
        this.setValue(value);
    }

    public Integer32Array(Array array)
    {
        super(array);
        if(array.length != 4)
        {
            throw new ArrayLengthException("Integer32Array must have a size of 4");
        }
    }
    
    public void setValue(int value)
    {
        this.doSet(0, (byte) (value >> 24) & 0xFF);
        this.doSet(1, (byte) (value >> 16) & 0xFF);
        this.doSet(2, (byte) (value >> 8) & 0xFF);
        this.doSet(3, (byte) value & 0xFF);
    }
    
    public int getValue()
    {
        int value = 0;
        value += (this.doGet(0) & 0xFF) << 24;
        value += (this.doGet(1) & 0xFF) << 16;
        value += (this.doGet(2) & 0xFF) << 8;
        value += this.doGet(3) & 0xFF;
        return value;
    }
}
