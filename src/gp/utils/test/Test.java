/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.utils.test;

import gp.utils.arrays.Array;
import gp.utils.arrays.RandomArray;

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
        
        Array array = Array.fromHexString("0123456789abcd");
        System.out.println(array);
        
        while(true)
        {
            RandomArray random_src = new RandomArray(50);
            
            Array random_dst = Array.fromHexString(Array.toHexString(random_src));
            
            if(!random_src.equals(random_dst)) System.err.println("err");
            else System.err.println(Array.toHexString(random_src));
            
        }
    }
}
