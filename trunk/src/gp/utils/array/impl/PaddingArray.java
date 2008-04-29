/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.array.impl;


/**
 *
 * @author gege
 */
public class PaddingArray extends ReadOnlyArray
{
    private Array data;
    
    private byte paddedValue;
    
    public PaddingArray(Array data, byte paddedValue, int paddedLength)
    {
        this.data = data;
        this.length = paddedLength;
        this.paddedValue = paddedValue;
    }

    @Override
    public byte doGet(int i)
    {
        if(i >= data.length)
        {
            return this.paddedValue;
        }
        else
        {
            return this.data.get(i);
        }
    }
}
