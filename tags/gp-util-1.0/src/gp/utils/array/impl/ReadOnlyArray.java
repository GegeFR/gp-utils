/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */

package gp.utils.array.impl;

/**
 *
 * @author gege
 */
public abstract class ReadOnlyArray extends Array
{
    @Override
    final protected void doSet(int i, byte value)
    {
        throw new UnsupportedOperationException("Not supported, this Array is readonly.");
    }

    @Override
    final protected void doSet(int i, int value)
    {
        throw new UnsupportedOperationException("Not supported, this Array is readonly.");
    }
}
