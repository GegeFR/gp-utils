/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.utils.test;

import gp.utils.arrays.Array;
import gp.utils.arrays.ConstantArray;
import gp.utils.arrays.DefaultArray;
import gp.utils.arrays.OverlayArray;
import gp.utils.arrays.SupArray;

/**
 *
 * @author gpasquiers
 */
public class Test
{
    public static void main(String... args)
    {
        DefaultArray array = new DefaultArray(16);
        long val = 4000000000l;
        int len = 32;
        int offset = 9;
        array.setBitsL(offset, len, val);
        
        System.out.println(val);
        System.out.println(array.getBits(offset, len));
        
        System.out.println(array.toString());
        
//        Array data = new ConstantArray((byte)0xdd, 50);
//        Array key = new ConstantArray((byte)0xaa,16);
//        
//        Array macArray = new MacArray(data, "HmacMD5", key);
//        System.out.println(macArray);
        
//        Array array = Array.fromHexString("0123456789abcd");
//        System.out.println(array);
//
//        while(true)
//        {
//            RandomArray random_src = new RandomArray(50);
//
//            Array random_dst = Array.fromHexString(Array.toHexString(random_src));
//
//            if(!random_src.equals(random_dst)) System.err.println("err");
//            else System.err.println(Array.toHexString(random_src));
//
//        }


//        DefaultArray bitArray = new DefaultArray(3);
//        System.err.println(bitArray);
//        bitArray.setBit(0, 1);
//        bitArray.setBit(1, 1);
//        bitArray.setBit(2, 1);
//        bitArray.setBit(3, 1);
//        bitArray.setBit(4, 1);
//        System.err.println(bitArray.getBit(0));
//        System.err.println(bitArray.getBit(1));
//        System.err.println(bitArray.getBit(2));
//        System.err.println(bitArray.getBit(3));
//        System.err.println(bitArray.getBits(3,2));
//        System.err.println(bitArray);
//
//        SupArray background = new SupArray();
//        for(int i=0; i<64; i++){
//            background.addLast(new ConstantArray((byte) (i), 1));
//        }
//        Array overlay = background.subArray(2,32);
//
//        //Array res = new OverlayArray(background, overlay, 16);
//        Array res = background;
//        System.err.println(res);
//        System.err.println();
//
//
//        System.err.println(new DefaultArray(res.getBytes()));
//        System.err.println();
//
//        byte[] data = new byte[64];
//
//        for(int i=0; i<data.length; i++) data[i] = (byte) 0xff;
//
//        res.getBytes(0, data, 0, 8);
//        System.err.println(new DefaultArray(data));
//        System.err.println();
//        res.getBytes(8, data, 8, 16);
//        System.err.println(new DefaultArray(data));
//        System.err.println();
//        res.getBytes(24, data, 24, 16);
//        System.err.println(new DefaultArray(data));
//        System.err.println();
//        res.getBytes(40, data, 40, 16);
//        System.err.println(new DefaultArray(data));
//        System.err.println();
//        res.getBytes(56, data, 56, 8);
//        System.err.println(new DefaultArray(data));
//        System.err.println();
//
//        for(int i=0; i<data.length; i++) data[i] = (byte) 0xff;
//
//
//        res.getBytes(56, data, 0, 8);
//        System.err.println(new DefaultArray(data));
//        System.err.println();
//        res.getBytes(40, data, 8, 16);
//        System.err.println(new DefaultArray(data));
//        System.err.println();
//        res.getBytes(24, data, 24, 16);
//        System.err.println(new DefaultArray(data));
//        System.err.println();
//        res.getBytes(8, data, 40, 16);
//        System.err.println(new DefaultArray(data));
//        System.err.println();
//        res.getBytes(0, data, 56, 8);
//        System.err.println(new DefaultArray(data));
//        System.err.println();
    }
}
