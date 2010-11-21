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

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 * @author Gwenhael Pasquiers
 */
public class MacArray extends ReadOnlyArray
{
    private boolean doMac;

    private Array data;
    
    private byte[] result;

    private SecretKeySpec secretKeySpec;

    private Mac mac;
    
    public MacArray(Array array, String algorithm, Array secret)
    {
        init(array, algorithm, secret);
    }
    
    public MacArray(Array array, String algorithm, String secret, String charset) 
    {
        try
        {
            this.init(array, algorithm, new DefaultArray(secret.getBytes(charset)));
        }
        catch(UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Inits the SecretKeySpec and Mac objects. WARN: this method reads the
     * secret Array.
     * @param array
     * @param algorithm
     * @param secret
     */
    private void init(Array array, String algorithm, Array secret)
    {
        this.data = array;
        
        try
        {
            this.secretKeySpec = new SecretKeySpec(secret.getBytes(), algorithm);
            this.mac = (Mac) Mac.getInstance(this.secretKeySpec.getAlgorithm()).clone();
            this.mac.init(this.secretKeySpec);
        }
        catch(NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
        catch(CloneNotSupportedException e)
        {
            throw new RuntimeException(e);
        }
        catch(InvalidKeyException e)
        {
            throw new RuntimeException(e);
        }
        
        this.length = this.mac.getMacLength();
        
        this.doMac = true;
    }
    
    @Override
    protected byte doGet(int i)
    {
        if(this.doMac)
        {
            computeMac();
        }
        
        return result[i];
    }

    private synchronized void computeMac()
    {
        if(this.doMac == false) return;
        

        // Encode compute the byte array
        for(int i=0; i<this.data.length; i++) this.mac.update(this.data.get(i));

        this.result = mac.doFinal();

        this.doMac = false;
    }

    @Override
    protected void doGetBytes(int sourceOffset, byte[] target, int targetOffset, int copyLength)
    {
        if(this.doMac)
        {
            computeMac();
        }

        System.arraycopy(result, sourceOffset, target, targetOffset, copyLength);
    }
}
