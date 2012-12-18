/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.nthread.test;

/**
 *
 * @author Namo
 */
public class Counter {
    private int _c = 0;
    
    public synchronized void inc(){
        _c++;
    }
    
    public synchronized void dec(){
        _c--;
    }
            
    public synchronized int value(){
        return _c;
    }
}
