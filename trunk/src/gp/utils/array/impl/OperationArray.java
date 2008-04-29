/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.array.impl;

/**
 *
 * @author gege
 */
public abstract class OperationArray extends ReadOnlyArray
{
    private Array array1;
    private Array array2;
    
    public OperationArray(Array array1, Array array2)
    {
        if(array1.length != array2.length)
        {
            throw new ArrayIndexOutOfBoundsException("Size error: array1=" + array1.length + " array2=" + array2.length + ", must be equal");
        }
        
        this.length = array1.length;
        
        this.array1 = array1;
        this.array2 = array2;
    }
    
    @Override
    public byte doGet(int i)
    {
        return this.operation(this.array1.get(i), this.array2.get(i));
    }

    public abstract byte operation(byte byte1, byte byte2);
}
