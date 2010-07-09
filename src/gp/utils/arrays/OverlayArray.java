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
    private Array parentBefore;
    private Array overlay;
    private Array parentAfter;
    private int offset;
    
    
    /** Creates a new instance of Array */
    public OverlayArray(Array parent, Array overlay, int offset)
    {
        this.length = parent.length;
        this.parentBefore = parent.subArray(0, offset);
        this.parentAfter = parent.subArray(offset + overlay.length, parent.length - offset - overlay.length);

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
        if(i < offset)
        {
            return parentBefore.get(i);
        }
        else if(i >= offset && i < offset + overlay.length)
        {
            return overlay.get(i - offset);
        }
        else
        {
            return parentAfter.get(i - offset - overlay.length);
        }
    }

    @Override
    protected void doSet(int i, byte value)
    {
        if(i < offset)
        {
            parentBefore.set(i, value);
        }
        else if(i >= offset && i < offset + overlay.length)
        {
            overlay.set(i - offset, value);
        }
        else
        {
            parentAfter.set(i - offset - overlay.length, value);
        }
    }

    @Override
    protected void doSet(int i, int value)
    {
        doSet(i, (byte) (value & 0xFF));
    }

    @Override
    protected void doGetBytes(byte[] container, int offset, int length)
    {
        if(length >= 0)
        {
            parentBefore.doGetBytes(container, offset, Math.min(length, parentBefore.length));
        }

        if(length >= this.offset)
        {
            overlay.doGetBytes(container, offset + this.offset, Math.min(length - this.offset, overlay.length));
        }

        if(length >= this.offset + overlay.length)
        {
            parentAfter.doGetBytes(container, offset + this.offset + overlay.length, Math.min(length - this.offset - overlay.length, parentAfter.length));
        }
    }

    // </editor-fold>
}
