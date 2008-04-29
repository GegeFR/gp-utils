/*
 * Array.java
 *
 * Created on 14 octobre 2007, 15:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package gp.utils.array.impl;

/**
 *
 * @author GegeFR
 */
public class DefaultArray extends Array
{
    private byte[] array;
    
    /** Creates a new instance of Array */
    public DefaultArray(byte[] array)
    {
        this.array = array;
        this.length = array.length;
    }

    public DefaultArray(int length)
    {
        this(new byte[length]);
    }

    // <editor-fold desc=" Array interface " >
    @Override
    public byte doGet(int i)
    {
        return array[i];
    }

    @Override
    public void doSet(int i, byte value)
    {
        array[i] = value;
    }

    @Override
    public void doSet(int i, int value)
    {
        array[i] = (byte) (value & 0xFF);
    }

    @Override
    public byte[] getBytes()
    {
        return array;
    }

   // </editor-fold>
}
