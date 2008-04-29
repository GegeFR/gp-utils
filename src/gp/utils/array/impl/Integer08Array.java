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
