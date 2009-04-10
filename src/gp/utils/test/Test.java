/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.utils.test;

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


        DefaultArray bitArray = new DefaultArray(3);
        System.err.println(bitArray);
        bitArray.setBit(0, 1);
        bitArray.setBit(1, 1);
        bitArray.setBit(2, 1);
        bitArray.setBit(3, 1);
        bitArray.setBit(4, 1);
        System.err.println(bitArray.getBit(0));
        System.err.println(bitArray.getBit(1));
        System.err.println(bitArray.getBit(2));
        System.err.println(bitArray.getBit(3));
        System.err.println(bitArray.getBits(3,2));
        System.err.println(bitArray);

    }
}
