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

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 * @author Gwenhael Pasquiers
 */
public class CipherArray extends ReadOnlyArray
{
    private boolean doCipher;

    private Array data;
    private Array secret;
    private Array salt;
    private int mode;
    private String cipherAlgorithm;
    private String keyAlgorithm;

    private byte[] result;
    
    public CipherArray(Array data, Array secret, Array salt, String cipherAlgorithm, String keyAlgorithm, int cipherMode)
    {
        this.secret = secret;
        this.data = data;
        this.salt = salt;
        this.mode = cipherMode;
        this.cipherAlgorithm = cipherAlgorithm;
        this.keyAlgorithm = keyAlgorithm;

        this.doCipher = true;
        doCipher();
    }
    
    @Override
    protected byte doGet(int i)
    {
        if(this.doCipher)
        {
            doCipher();
        }
        
        return result[i];
    }

    private synchronized void doCipher()
    {
        if(this.doCipher == false) return;
        
        try
        {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), this.keyAlgorithm);
            Cipher cipher = Cipher.getInstance(this.cipherAlgorithm);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(this.salt.getBytes());
            cipher.init(this.mode, secretKeySpec, ivParameterSpec);
            this.result = cipher.doFinal(this.data.getBytes());
            this.length = this.result.length;
        }
        catch (IllegalBlockSizeException e)
        {
            throw new RuntimeException(e);
        }
        catch (BadPaddingException e)
        {
            throw new RuntimeException(e);
        }
        catch (InvalidAlgorithmParameterException e)
        {
            throw new RuntimeException(e);
        }
        catch(NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
        catch(InvalidKeyException e)
        {
            throw new RuntimeException(e);
        }
        catch (NoSuchPaddingException e)
        {
            throw new RuntimeException(e);
        }

        this.doCipher = false;
    }

    @Override
    protected void doGetBytes(int sourceOffset, byte[] target, int targetOffset, int copyLength)
    {
        if(this.doCipher)
        {
            doCipher();
        }
        
        System.arraycopy(result, sourceOffset, target, targetOffset, copyLength);
    }
}
