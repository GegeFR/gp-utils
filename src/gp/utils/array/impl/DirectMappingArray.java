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
public class DirectMappingArray extends Array
{
    private Array array;
    
    public DirectMappingArray(Array array)
    {
        this.array = array;
        this.length = array.length;
    }
    
    // <editor-fold desc=" Array interface " >
    @Override
    public final byte doGet(int i)
    {
        // use doGet since it is a direct mapping
        return array.doGet(i);
    }

    @Override
    public final void doSet(int i, byte value)
    {
        // use doGet since it is a direct mapping
        array.doSet(i, value);
    }

    @Override
    public final void doSet(int i, int value)
    {
        // use doGet since it is a direct mapping
        array.doSet(i, value);
    }

    @Override
    public final byte[] getBytes()
    {
        // use array's getBytes since it is a direct mapping and array could be a DefaultArray
        return array.getBytes();
    }
    // </editor-fold>
}
