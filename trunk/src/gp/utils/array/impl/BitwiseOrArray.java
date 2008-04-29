/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.array.impl;




/**
 *
 * @author gege
 */
public class BitwiseOrArray extends OperationArray
{
    public BitwiseOrArray(Array array1, Array array2)
    {
        super(array1, array2);
    }


    @Override
    public byte operation(byte byte1, byte byte2)
    {
        return (byte) ((byte1 | byte2) & 0xFF);
    }
}
