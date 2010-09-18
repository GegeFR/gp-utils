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

package gp.utils.arrays;

/**
 *
 * @author Gwenhael Pasquiers
 */
public class OverlayArray extends Array
{
    private Array parent;
    private Array overlay;
    private int offset;
    
    
    /** Creates a new instance of Array */
    public OverlayArray(Array parent, Array overlay, int offset)
    {
        this.length = parent.length;
        this.parent = parent;

        this.overlay = overlay;

        this.offset = offset;

        if(offset < 0 || offset + overlay.length  > parent.length)
        {
            throw new ArrayLengthException("Invalid OverlayArray parameters offset=" + offset + ", length=" + length + ", overlay array length=" + overlay.length);
        }
    }

    // <editor-fold desc=" Array interface " >
    @Override
    protected byte doGet(int i)
    {
        if(i >= offset && i < offset + overlay.length)
        {
            return overlay.get(i - offset);
        }
        else
        {
            return parent.get(i);
        }
    }

    @Override
    protected void doSet(int i, byte value)
    {
        if(i >= offset && i < offset + overlay.length)
        {
            overlay.set(i - offset, value);
        }
        else
        {
            parent.set(i, value);
        }
    }

    @Override
    protected void doSet(int i, int value)
    {
        doSet(i, (byte) (value & 0xFF));
    }

    @Override
    protected void doGetBytes(int sourceOffset, byte[] target, int targetOffset, int copyLength)
    {
        int done = 0;
        int todo = copyLength;
        
        if(sourceOffset + copyLength >= 0 && sourceOffset <= offset)
        {
            int toCopy = Math.min(copyLength, offset);
            parent.doGetBytes(sourceOffset, target, targetOffset, toCopy);
            todo -= toCopy;
            done += toCopy;
        }

        if(sourceOffset + copyLength >= offset && sourceOffset <= offset + overlay.length)
        {
            int localOffset = Math.max(0, sourceOffset - offset);
            int toCopy = Math.min(todo, overlay.length - localOffset);
            overlay.doGetBytes(localOffset, target, targetOffset + Math.max(0, (offset - sourceOffset)), toCopy);
            todo -= toCopy;
            done += toCopy;
        }

        if(sourceOffset + copyLength >= offset + overlay.length && sourceOffset <= length)
        {
            parent.doGetBytes(Math.max(sourceOffset, offset + overlay.length), target, targetOffset + done, todo);
        }
    }

    // </editor-fold>
}
