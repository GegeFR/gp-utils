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
public class IPv4Array extends DirectMappingArray
{
    public IPv4Array(String value)
    {
        super(new DefaultArray(4));
        this.setValue(value);
    }

    public IPv4Array(Array array)
    {
        super(array);
        if(array.length != 4)
        {
            throw new ArrayLengthException("IPv4Array must have a size of 4");
        }
    }
    
    public void setValue(String ip)
    {
        int min = 0;
        int max = ip.indexOf('.', min);

        this.doSet(0, Integer.parseInt(ip.substring(min, ip.indexOf(".", min))));
        min = max + 1;
        max = ip.indexOf('.', min);

        this.doSet(1, Integer.parseInt(ip.substring(min, ip.indexOf(".", min))));
        min = max + 1;
        max = ip.indexOf('.', min);

        this.doSet(2, Integer.parseInt(ip.substring(min, ip.indexOf(".", min))));
        min = max + 1;

        this.doSet(3, Integer.parseInt(ip.substring(min)));
    }
    
    public String getValue()
    {
        return (this.doGet(0)&0xff) + "." +
               (this.doGet(1)&0xff) + "." +
               (this.doGet(2)&0xff) + "." +
               (this.doGet(3)&0xff);
    }
}
