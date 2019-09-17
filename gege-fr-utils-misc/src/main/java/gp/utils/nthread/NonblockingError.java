/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.utils.nthread;

import gp.utils.nthread.NonblockingThread.StackElement;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

/**
 *
 * @author Namo
 */
public class NonblockingError {
    private StackElement[] _stack;
    private String _message;
    private Throwable _throwable;
    
    public NonblockingError(NonblockingThread thread, String message, Throwable throwable){
        _stack = thread.getStack();
        _message = message;
        _throwable = throwable;
    }
    
    @Override
    public String toString(){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(os);
        pw.println("[Nonblocking stack] " + getClass().getName() + ": " + _message);
        for(StackElement element:_stack){
            pw.println("\tin " + element.f.getClass().getName());
        }
        if(null != _throwable){
            pw.print("[Exception stack] ");
            _throwable.printStackTrace(pw);
        }
        pw.close();
        return new String(os.toByteArray());
    }
    
}
