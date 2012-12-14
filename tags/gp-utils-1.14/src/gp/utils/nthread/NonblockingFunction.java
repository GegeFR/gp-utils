/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.nthread;

/**
 *
 * @author Namo
 */
abstract public class NonblockingFunction {

    private NonblockingThread _owner = null;
    private boolean _active = false;

    protected final void securityLock(NonblockingThread owner) {
        if (_owner == null) {
            _owner = owner;
        }
        else {
            throw new IllegalStateException("Cannot lock " + this + " is is already owned.");
        }
    }

    protected final void securityUnlock(NonblockingThread owner) {
        if (_owner == owner && owner != null) {
            _owner = null;
        }
        else {
            throw new IllegalStateException("Cannot unlock NonblockingFunction " + this + " passed wrong owner.");
        }
    }

    protected final boolean securityIsActive() {
        return _active;
    }

    protected final void securitySetActive() {
        if (_active == false) {
            _active = true;
        }
        else {
            throw new IllegalStateException("Cannot set active " + this + ", already active.");
        }
    }

    protected final void securitySetInactive() {
        if(_active == true){
            _active = false;
        }
        else{
            throw new IllegalStateException("Cannot set inactive " + this + ", already inactive.");
        }
    }

    protected abstract void nbCalled(NonblockingThread t, Object arg);

    protected abstract void nbContinued(NonblockingThread t);

    protected abstract void nbReturned(NonblockingThread t, Object ret);
}
