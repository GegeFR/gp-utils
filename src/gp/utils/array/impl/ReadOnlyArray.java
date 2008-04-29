/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.array.impl;

/**
 *
 * @author gege
 */
public abstract class ReadOnlyArray extends Array
{
    @Override
    final public void doSet(int i, byte value)
    {
        throw new UnsupportedOperationException("Not supported, this Array is readonly.");
    }

    @Override
    final public void doSet(int i, int value)
    {
        throw new UnsupportedOperationException("Not supported, this Array is readonly.");
    }
}
