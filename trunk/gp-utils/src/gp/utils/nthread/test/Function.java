/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.nthread.test;

import gp.utils.nthread.NonblockingFunction;
import gp.utils.nthread.NonblockingThread;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Namo
 */
public class Function extends NonblockingFunction{
    private Semaphore _arg;
    private FunctionDeux _functionDeux = new FunctionDeux();
    
    @Override
    protected void nbCalled(NonblockingThread t, Object arg) {
        _arg = (Semaphore) arg;
        t.nbContinue();
    }

    @Override
    protected void nbContinued(NonblockingThread t) {
       _arg.release();
       t.nbCall(_functionDeux, _arg);
    }

    @Override
    protected void nbReturned(NonblockingThread t, Object ret) {
        t.nbReturn(null);
    }
}
