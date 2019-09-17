/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.nthread;

import java.util.ArrayDeque;

/**
 *
 * @author Namo
 */
public class NonblockingPark {

    private ArrayDeque<NonblockingThread> _parking;

    public NonblockingPark() {
        _parking = new ArrayDeque<NonblockingThread>();
    }

    protected synchronized void nbPark(NonblockingThread nonblockingThread) {
        if(!_parking.contains(nonblockingThread)){
            _parking.add(nonblockingThread);
            nonblockingThread.nbWait();
        }
        else {
            throw new IllegalArgumentException("NonblockingThread " + nonblockingThread + " already found in parking.");
        }
    }

    public synchronized void unpark(NonblockingThread nonblockingThread) {
        if (_parking.remove(nonblockingThread)) {
            nonblockingThread.nbContinue();
        }
        else {
            throw new IllegalArgumentException("NonblockingThread " + nonblockingThread + " not found in parking.");
        }
    }

    public synchronized void unparkIn(NonblockingThread nonblockingThread, long delay) {
        if (_parking.remove(nonblockingThread)) {
            nonblockingThread.nbContinueIn(delay);
        }
        else {
            throw new IllegalArgumentException("NonblockingThread " + nonblockingThread + " not found in parking.");
        }
    }

    public synchronized void unparkAt(NonblockingThread nonblockingThread, long date) {
        if (_parking.remove(nonblockingThread)) {
            nonblockingThread.nbContinueAt(date);
        }
        else {
            throw new IllegalArgumentException("NonblockingThread " + nonblockingThread + " not found in parking.");
        }
    }

    public synchronized void unparkAny() {
        if (!_parking.isEmpty()) {
            _parking.getFirst().nbContinue();
        }
        else {
            throw new IllegalArgumentException("Parking is empty.");
        }
    }

    public synchronized void unparkAnyIn(long delay) {
        if (!_parking.isEmpty()) {
            _parking.getFirst().nbContinueIn(delay);
        }
        else {
            throw new IllegalArgumentException("Parking is empty.");
        }
    }

    public synchronized void unparkAnyAt(long date) {
        if (!_parking.isEmpty()) {
            _parking.getFirst().nbContinueAt(date);
        }
        else {
            throw new IllegalArgumentException("Parking is empty.");
        }
    }
}
