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

        if(offset != 0 && sourceOffset >= 0 && sourceOffset <= offset){
            int copyFrom = Math.max(sourceOffset, 0);
            int copyUntil = Math.min(sourceOffset + copyLength, offset);
            int toCopy = copyUntil - copyFrom;

            parent.doGetBytes(sourceOffset, target, targetOffset, toCopy);
            done += toCopy;
        }


        if(sourceOffset < offset + overlay.length && sourceOffset + copyLength >= offset){
            int copyFrom = Math.max(sourceOffset, offset);
            int copyUntil = Math.min(sourceOffset + copyLength, offset + overlay.length);
            int toCopy = copyUntil - copyFrom;

            overlay.doGetBytes(copyFrom - offset, target, targetOffset + done, toCopy);
            done += toCopy;
        }
        
        if(sourceOffset < this.length && sourceOffset + copyLength >= offset + overlay.length){
            int copyFrom = Math.max(sourceOffset, offset + overlay.length);
            int copyUntil = Math.min(sourceOffset + copyLength, this.length);
            int toCopy = copyUntil - copyFrom;

            parent.doGetBytes(copyFrom - offset - overlay.length, target, targetOffset + done, toCopy);
            done += toCopy;
        }
    }

    // </editor-fold>
}
