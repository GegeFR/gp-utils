/*
 * SubArray.java
 *
 * Created on 14 octobre 2007, 00:17
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
public class SubArray extends Array
{
    private Array array;
    
    private int offset;
      
    public SubArray(Array array, int offset, int length) throws ArrayIndexOutOfBoundsException
    {
        if(offset < 0 || offset > array.length || offset + length  > array.length)
        {
            throw new ArrayLengthException("Invalid SubArray parameters offset=" + offset + ", length=" + length + ", embedded array length=" + array.length);
        }
        
        this.array = array;
        this.offset = offset;
        this.length = length;
    }
    
    public SubArray(Array array, int offset) throws ArrayIndexOutOfBoundsException
    {
        if(offset < 0 || offset > array.length)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid SubArray paramters offset=" + offset + ", embedded array length=" + array.length);
        }
        
        this.array = array;
        this.offset = offset;
        this.length = array.length - this.offset;
    }   
    
    // <editor-fold desc=" Array interface " >
    @Override
    public byte doGet(int i)
    {
        return array.get(offset + i);
    }

    @Override
    public void doSet(int i, byte value)
    {
        array.set(offset + i, value);
    }
    
    @Override
    public void doSet(int i, int value)
    {
        array.set(offset + i, value);
    }
    // </editor-fold>
    
}
