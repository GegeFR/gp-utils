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

package gp.utils.map;

import gp.utils.exception.AssociationHashMapUniquenessException;
import java.util.HashMap;

/**
 *
 * @author gege
 */
public class AssociationHashMap<L, R>
{
    private HashMap<L, R> leftToRight;
    private HashMap<R, L> rightToLeft;

    public AssociationHashMap()
    {
        this.leftToRight = new HashMap<L, R>();
        this.rightToLeft = new HashMap<R, L>();
    }
    
    synchronized public void put(L left, R right) throws AssociationHashMapUniquenessException
    {
        //if(!this.leftToRight.containsKey(left) && !this.rightToLeft.containsKey(right))
        {
            this.leftToRight.put(left, right);
            this.rightToLeft.put(right, left);
        }
        /*else
        {
            throw new AssociationHashMapUniquenessException("Pair " + left + "/" + right + " is not unique");
        }*/
    }
    
    public R getRight(L left)
    {
        return this.leftToRight.get(left);
    }
    
    public L getLeft(R right)
    {
        return this.rightToLeft.get(right);
    }
    
    synchronized public R removeLeft(L left)
    {
        R right = this.leftToRight.remove(left);
        this.rightToLeft.remove(right);
        return right;
    }

    synchronized public L removeRight(R right)
    {
        L left = this.rightToLeft.remove(right);
        this.leftToRight.remove(left);
        return left;
    }
}
