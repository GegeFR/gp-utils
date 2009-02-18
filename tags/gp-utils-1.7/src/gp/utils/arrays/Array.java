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
 * @author Gwenhael PasquiersFR
 */
public abstract class Array
{
    public int length;

    public final byte get(int i)
    {
        this.assertValidIndex(i);
        return this.doGet(i);
    }

    public final void set(int i, byte value)
    {
        this.assertValidIndex(i);
        this.doSet(i, value);

    }

    public final void set(int i, int value)
    {
        this.assertValidIndex(i);
        this.doSet(i, value);
    }

    protected abstract byte doGet(int i);
    
    protected abstract void doSet(int i, byte value);
    
    protected abstract void doSet(int i, int value);

    public byte[] getBytes()
    {
        byte[] array = new byte[this.length];
        
        for(int i=0; i<this.length; i++)
        {
            array[i] = this.get(i);
        }
        
        return array;
    }
    
    public Array subArray(int offset)
    {
        return this.subArray(offset, this.length - offset);
    }
    
    public Array subArray(int offset, int length)
    {
        return new SubArray(this, offset, length);
    }

    public int indexOf(Array array)
    {
        return this.indexOf(array, 0);
    }

    public int indexOf(Array array, int minimum)
    {
        if(minimum < 0 || minimum >= this.length) throw new ArrayIndexException("invalid start index ("+minimum+") for array of size " + this.length);

        if(array.length == 0) throw new ArrayIndexException("invalid array (size = 0)");

        for(int i=minimum; i< (this.length - (array.length - 1)); i++)
        {
            if(this.get(i) == array.get(0))
            {
                int candidate = i;
                for(int j=1; j<array.length; j++)
                {
                    if(this.get(i+j) != array.get(j))
                    {
                        candidate = -1;
                        break;
                    }
                }
                
                if(candidate != -1) return candidate;
            }
        }

        return -1;
    }

    private final void assertValidIndex(int i)
    {
        if(i< 0 || i >= length)
        {
            throw new ArrayIndexOutOfBoundsException("Invalid index: " + i +" of " + length + " elements");
        }
    }
    
    @Override
    public String toString()
    {
        StringBuffer stringBuffer = new StringBuffer();

        for(int i=0; i<this.length; i++)
        {
            byte val = this.get(i);
            stringBuffer.append(Integer.toHexString((val & 0xFF) >> 4)).append(Integer.toHexString(val & 0x0F));
            if((i + 1) % 16 == 0)
            {
                if(i+1 < this.length) stringBuffer.append("\n");
            }
            else
            {
                if(i+1 < this.length) stringBuffer.append(" ");
            }
        }
        
        return stringBuffer.toString();
    }
    
    @Override
    public boolean equals(Object object)
    {
        if(!(object instanceof Array))
        {
            return false;
        }
        
        Array array = (Array) object;
        
        if(this.length != array.length)
        {
            return false;
        }
        
        for(int i=0; i<this.length; i++)
        {
            if(this.get(i) != array.get(i))
            {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public Array clone()
    {
        return new DefaultArray(this.getBytes());
    }
    
    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
    
    public static Array fromHexString(String string)
    {
        if(null == string) throw new IllegalArgumentException("string cannot be null");
        
        if(string.length() % 2 != 0) throw new IllegalArgumentException("string length is not even");
        
        DefaultArray array = new DefaultArray(string.length() / 2);
        
        for(int i=0; i<array.length; i++)
        {
            byte value = 0;
            value += charToByte(string.charAt(i*2)) << 4;
            value += charToByte(string.charAt(i*2 + 1));
            array.set(i, value);
        }

        return array;
    }

    public static String toHexString(Array array)
    {
        char[] chars = new char[array.length * 2];
        
        int i=0;
        int j=0;
        while(i < array.length)
        {
            chars[j] =  byteToChar((byte) ((array.get(i)&0xff) >> 4));
            chars[j+1] = byteToChar((byte) (array.get(i) & 0x0f));
            
            i += 1;
            j += 2;
        }
        
        return new String(chars);
    }

    public static Array fromBase64String(String string)
    {
        return new DefaultArray(Base64Coder.decode(string));
    }

    public static String toBase64String(Array array)
    {
        return Base64Coder.encode(array);
    }

    private static byte charToByte(char character)
    {
        switch(character)
        {
            case '0': return 0;
            case '1': return 1;
            case '2': return 2;
            case '3': return 3;
            case '4': return 4;
            case '5': return 5;
            case '6': return 6;
            case '7': return 7;
            case '8': return 8;
            case '9': return 9;
            case 'A': 
            case 'a': return 10;
            case 'B': 
            case 'b': return 11;
            case 'C':
            case 'c': return 12;
            case 'D':
            case 'd': return 13;
            case 'E':
            case 'e': return 14;
            case 'F':
            case 'f': return 15;
            default : throw new IllegalArgumentException("unknown char " + character);
        }
    }
    
    private static char byteToChar(byte byteValue)
    {
        switch(byteValue)
        {
            case  0: return '0';
            case  1: return '1';
            case  2: return '2';
            case  3: return '3';
            case  4: return '4';
            case  5: return '5';
            case  6: return '6';
            case  7: return '7';
            case  8: return '8';
            case  9: return '9';
            case 10: return 'a';
            case 11: return 'b';
            case 12: return 'c';
            case 13: return 'd';
            case 14: return 'e';
            case 15: return 'f';
            default: throw new IllegalArgumentException("invalid value " + byteValue);
        }
    }

}
