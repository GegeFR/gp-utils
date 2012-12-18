/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.nthread;

import gp.utils.scheduler.Task;
import java.util.ArrayDeque;

/**
 *
 * @author Namo
 */
public class NonblockingThread implements Task {

    protected enum State {

        CALLED,
        CONTINUED,
        RETURNED
    }
    private ArrayDeque<StackElement> _stack;
    private NonblockingEngine _engine;
    private boolean _waiting;
    
    protected NonblockingThread(NonblockingEngine engine) {
        _engine = engine;
        _stack = new ArrayDeque<StackElement>();
        _waiting = false;
    }

    public void nbCall(NonblockingFunction nonblockingFunction, Object arg) {
        if(!_stack.isEmpty()){
            _stack.getFirst().f.securitySetInactive();
        }
        nonblockingFunction.securityLock(this);

        _waiting = false;
        
        _stack.addFirst(new StackElement(nonblockingFunction, State.CALLED, arg));

        _engine.nbContinue(this);
    }

    // actions; should always be called at the end of and event (onXXX)
    
    public void nbContinue() {
        _stack.getFirst().f.securitySetInactive();
        _waiting = false;
        
        _engine.nbContinue(this);
    }

    public void nbContinueIn(long millis) {
        _stack.getFirst().f.securitySetInactive();
        _waiting = false;
        
        _engine.nbContinueIn(this, millis);
    }

    public void nbContinueAt(long date) {
        _stack.getFirst().f.securitySetInactive();
        _waiting = false;
        
        _engine.nbContinueAt(this, date);
    }

    public void nbWait() {
        _stack.getFirst().f.securitySetInactive();
        _waiting = true;
    }

    public void nbReturn(Object ret) {
        _stack.getFirst().f.securitySetInactive();
        _waiting = false;
        
        StackElement currentElement = _stack.getFirst();
        currentElement.s = State.RETURNED;
        currentElement.r = ret;

        _engine.nbContinue(this);
    }

    public void nbPark(NonblockingPark park){
        park.nbPark(this);
    }
    
    // getters
    
    public boolean isWaiting(){
        return _waiting;
    }

    protected StackElement[] getStack(){
        StackElement[] stack = _stack.toArray(new StackElement[_stack.size()]);
        return stack;
    }
    
    /**
     * Fires the appropriate event at the right moment, does Security checks to make sure there is no inconsistance.
     */
    @Override
    public synchronized void execute() {
        StackElement stackElement = _stack.peekFirst();
        switch (stackElement.s) {
            case CALLED:
                stackElement.f.securitySetActive();
                stackElement.s = State.CONTINUED;
                stackElement.f.nbCalled(this, stackElement.a);
                break;
            case CONTINUED:
                stackElement.f.securitySetActive();
                stackElement.f.nbContinued(this);
                break;
            case RETURNED:
                // get previously returned object
                Object ret = stackElement.r;
                
                // remove and free the function that returned, now it cacs be used by another nbThread
                _stack.removeFirst().f.securityUnlock(this);
                
                // now fire the onCalledReturned on the parent function (if there is one)
                if (!_stack.isEmpty()) {
                    stackElement = _stack.getFirst();
                    stackElement.f.securitySetActive();
                    stackElement.f.nbReturned(this, ret);
                }
                else {
                    stackElement = null;
                }
                break;
        }
        
        if(stackElement != null && stackElement.f.securityIsActive()){
            System.err.println(new NonblockingError(this, "No nbXXXX was called at the end of the NonblockingFunction event, state is  !", new IllegalStateException()).toString());
        }
    }

    /**
     * Convenience class that represents a stack in the thread.
     */
    protected class StackElement {

        protected NonblockingFunction f;
        protected State s;
        protected Object a;
        protected Object r;

        protected StackElement(NonblockingFunction nonblockingFunction, State state, Object arg) {
            f = nonblockingFunction;
            s = state;
            a = arg;
            r = null;
        }
    }
}
