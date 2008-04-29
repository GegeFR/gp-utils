/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.array.impl;

/**
 *
 * @author gege
 */
public class ConstantArray extends ReadOnlyArray
{
    private byte value;
    
    public ConstantArray(byte value, int length)
    {
        this.value = value;
        this.length = length;
    }

    @Override
    public byte doGet(int i)
    {
        return this.value;
    }
}
