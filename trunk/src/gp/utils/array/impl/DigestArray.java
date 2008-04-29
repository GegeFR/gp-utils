/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.utils.array.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *
 * @author gege
 */
public class DigestArray extends ReadOnlyArray
{
    private boolean doDigest;

    private Array array;
    
    private byte[] digest;

    private MessageDigest msgDigest;
    
    public DigestArray(Array array, String algorithm)
    {
        this.array = array;
        try
        {
            this.msgDigest = MessageDigest.getInstance(algorithm);
        }
        catch(NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
        this.length = msgDigest.getDigestLength();
        this.doDigest = true;
    }
    
    @Override
    public byte doGet(int i)
    {
        if(doDigest)
        {
            computeDigest();
        }
        
        return digest[i];
    }

    private void computeDigest()
    {
        this.msgDigest.reset();
        for(int i=0; i<this.array.length; i++)
        {
            this.msgDigest.update(this.array.get(i));
        }
        this.digest = this.msgDigest.digest();
        this.length = this.digest.length;
    }
}
