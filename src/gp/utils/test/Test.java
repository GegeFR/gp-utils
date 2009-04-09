/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.utils.test;

import gp.utils.arrays.BitArray;
import gp.utils.arrays.DefaultArray;

/**
 *
 * @author gpasquiers
 */
public class Test
{
    public static void main(String... args)
    {
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


        BitArray bitArray = new BitArray(new DefaultArray(3));
        System.err.println(bitArray);
//        bitArray.setBitFromLeft(0, 1);
//        bitArray.setBitFromLeft(1, 1);
//        bitArray.setBitFromLeft(2, 1);
//        bitArray.setBitFromLeft(3, 1);
//        bitArray.setBitFromLeft(8, 1);
        bitArray.setBitsFromLeft(0, 8, 0xab);
        bitArray.setBitsFromLeft(8, 4, 0xc);
        bitArray.setBitsFromRight(8, 4, 0xd);
        bitArray.setBitsFromRight(0, 8, 0xef);
        System.err.println(bitArray);

    }
}
