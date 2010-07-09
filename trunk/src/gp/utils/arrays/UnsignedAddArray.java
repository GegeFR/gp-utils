/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.utils.arrays;

/**
 *
 * @author gpasquiers
 */
public class UnsignedAddArray extends ReadOnlyArray
{
    private Array operand_1;
    private Array operand_2;
    
    private byte[] resultant;
    
    private boolean doCompute;
    
    public UnsignedAddArray(Array operand_1, Array operand_2)
    {
        this.operand_1 = operand_1;
        this.operand_2 = operand_2;
        this.resultant = new byte[Math.max(this.operand_1.length, this.operand_2.length)];
        this.length = this.resultant.length;
        this.doCompute = true;
    }
        
    private synchronized void compute()
    {
        this.doCompute = false;
        int overflow = 0;
        int op_1;
        int op_2;
        int temp;
        for(int i=0; i<this.resultant.length; i++)
        {
            if(i >= this.operand_1.length) op_1 = 0;
            else op_1 = this.operand_1.get(this.operand_1.length - 1 - i) & 0xff;
            
            if(i >= this.operand_2.length) op_2 = 0;
            else op_2 = this.operand_2.get(this.operand_2.length - 1 - i) & 0xff;
            
            temp = op_1 + op_2 + overflow;
            this.resultant[resultant.length - 1 - i] = (byte) (temp % 256);
            
            overflow = temp / 256;
        }
    }
    
    @Override
    protected byte doGet(int i)
    {
        if(this.doCompute)
        {
            compute();
        }
        
        return this.resultant[i];
    }

    @Override
    protected void doGetBytes(byte[] container, int offset, int length)
    {
        if(this.doCompute)
        {
            compute();
        }

        System.arraycopy(resultant, 0, container, offset, length);
    }

}
