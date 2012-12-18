/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.nthread;

import gp.utils.scheduler.Scheduler;

/**
 *
 * @author Namo
 */
public class NonblockingEngine {
    private Scheduler _scheduler;
    
    public NonblockingEngine(int numberOfThreads){
        _scheduler = new Scheduler(numberOfThreads);
    }
    
    public void nbStart(NonblockingFunction nonblockingFunction, Object arg){
        NonblockingThread nonblockingThread = new NonblockingThread(this);
        //nonblockingFunction.securitySetActive();
        nonblockingThread.nbCall(nonblockingFunction, arg);
    }
    
    protected void nbContinue(NonblockingThread nonblockingThread){
        _scheduler.execute(nonblockingThread, false);
    }
    
    protected void nbContinueIn(NonblockingThread nonblockingThread, long millis) {
        _scheduler.scheduleIn(nonblockingThread, millis);
    }

    protected void nbContinueAt(NonblockingThread nonblockingThread, long date) {
        _scheduler.scheduleAt(nonblockingThread, date);
    }
    
}
