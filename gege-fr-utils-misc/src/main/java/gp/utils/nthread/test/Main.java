/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.nthread.test;

import gp.utils.nthread.NonblockingEngine;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Namo
 */
public class Main {
    static public void main(String... args){
        NonblockingEngine engine = new NonblockingEngine(1);
        
        int number = 3000000;
        Function[] functions = new Function[number];
        
        for(int i=0; i<number; i++){
            functions[i] = new Function();
        }
        long before = System.currentTimeMillis();
        Semaphore semaphore = new Semaphore(0);
        
        for(int i=0; i<number; i++){
            engine.nbStart(functions[i], semaphore);
        }
        
        semaphore.acquireUninterruptibly(number * 2);
        long after = System.currentTimeMillis();
        System.out.println("duration = " + (after-before) + " ms");
        System.out.println(semaphore.availablePermits());
    }
}
