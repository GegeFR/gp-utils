/*
 * Array.java
 *
 * Created on 14 octobre 2007, 15:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package gp.utils.array.impl;

import java.util.Random;

/**
 *
 * @author GegeFR
 */
public class RandomArray extends ReadOnlyArray
{
    private byte[] array;
    
    /** Creates a new instance of Array */
    public RandomArray(int length)
    {
        this.array = new byte[length];
        this.length = this.array.length;
        new Random().nextBytes(this.array);
    }

    // <editor-fold desc=" Array interface " >
    @Override
    public byte doGet(int i)
    {
        return array[i];
    }

    @Override
    public byte[] getBytes()
    {
        return array;
    }
    // </editor-fold>
}
