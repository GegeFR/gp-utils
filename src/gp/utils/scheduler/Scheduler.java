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

package gp.utils.scheduler;

import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.LockSupport;

/**
 *
 * @author Gwenhael Pasquiers
 */
public class Scheduler
{
    private final PriorityQueue<DatedTask> queuedTaskQueue;
    private final LinkedBlockingQueue<Task> normalTaskQueue;
    private final LinkedBlockingQueue<Task> urgentTaskQueue;
    
    private final Semaphore globalTaskSemaphore;
    private final Semaphore urgentTaskSemaphore;

    private final Thread schedulerThread;

    public Scheduler(int threadsCount)
    {
        
        this.normalTaskQueue = new LinkedBlockingQueue();
        this.urgentTaskQueue = new LinkedBlockingQueue();
        this.queuedTaskQueue = new PriorityQueue();
        this.globalTaskSemaphore = new Semaphore(0);
        this.urgentTaskSemaphore = new Semaphore(0);
        
        
        /*
         * Creation of a thread dedicated to the transfer of scheduled Tasks
         * to executable Tasks when their scheduled date is met.
         */
        final Scheduler _this = this;
        Runnable runnable = new Runnable(){
            public void run()
            {
                /*
                 * Tells if the scheduledTasks list is empty or not. If it is 
                 * empty we have to wait (semaphoreSchedule.acquire()) for an
                 * unknown time
                 */
                boolean isEmpty;
                
                /*
                 * Delay to wait before we can transfer the first scheduled task
                 * to the executable tasks. Not used if isEmpty is true. If its
                 * value over 0 we wait for the required time (tryAcquire(...))
                 * If the value is <= 0, then task should be != null as well.
                 */
                long delay;

                /*
                 * Next task to transfer from the queuedTaskQueue to the 
                 * urgentTaskQueue. If null, don't transfer anything
                 * and wait for the delay to the task on top of queue.
                 */
                DatedTask task;

                    
                while (true)
                {
                    synchronized(_this.queuedTaskQueue)
                    {
                        task = _this.queuedTaskQueue.peek();
                        
                        if(null != task)
                        {
                            isEmpty = false;
                            delay = task.date() - System.currentTimeMillis();
                        }
                        else
                        {
                            isEmpty = true;
                            delay = Long.MIN_VALUE;
                        }
                            
                        
                        if(!isEmpty && delay <= 0)
                        {
                            _this.queuedTaskQueue.remove();
                        }
                    }
                    
                    
                    try
                    {
                        if(isEmpty)
                        {
                            LockSupport.park();
                        }
                        else if(delay > 0)
                        {
                            LockSupport.parkNanos(delay * 1000 * 1000);
                        }
                        else
                        {
                            _this.urgentTaskQueue.offer(task.task);
                            _this.urgentTaskSemaphore.release();
                            _this.globalTaskSemaphore.release();
                        }
                    }
                    catch (Throwable t)
                    {
                        System.err.println("Scheduler internal thread died. This is fatal.");
                        t.printStackTrace();
                        return;
                    }
                }
            }
        };

        this.schedulerThread = new Thread(runnable);
        this.schedulerThread.setDaemon(true);
        this.schedulerThread.start();
        
        for(int i=0; i<threadsCount; i++)
        {
            runnable = new Runnable(){
                public void run()
                {
                    while(true)
                    {
                        try
                        {
                            _this.nextTask().execute();
                        }
                        catch(Throwable t)
                        {
                            System.err.println("Throwable catched in scheduler executor thread " + Thread.currentThread().getName() + ": " + t.toString());
                            t.printStackTrace();
                        }
                    }
                }
            };
            
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            thread.start();
        }
    }

    /**
     * This method adds a Task to the end of the queue of normal task.
     * Normal task are executed when there is no more urgent tasks.
     * @param task
     */
    public void execute(Task task, boolean urgent)
    {
        if(urgent)
        {
            this.urgentTaskQueue.offer(task);
            this.urgentTaskSemaphore.release();
        }
        else
        {
            this.normalTaskQueue.offer(task);
        }
        this.globalTaskSemaphore.release();
    }

    /**
     * Schedule a task in delay milliseconds. After this delay has expired, this
     * task will become an urgent task.
     * @param task
     * @param delay
     */
    public void scheduleIn(Task task, long millis)
    {
        this.scheduleAt(task, System.currentTimeMillis() + millis);
    }

    /**
     * Schedule a task at the date date. After the date is met, this task will
     * become an urgent task.
     * @param task
     * @param delay
     */
    public void scheduleAt(Task task, long date)
    {
        if(date < 0) throw new IllegalArgumentException("date must be positive or null");
        DatedTask scheduledTask = new DatedTask(task, date);
        synchronized (this.queuedTaskQueue)
        {
            this.queuedTaskQueue.add(scheduledTask);
            if(this.queuedTaskQueue.peek().equals(scheduledTask))
            {
                LockSupport.unpark(this.schedulerThread);
            }
        }
    }
    
    /**
     * Unschedule a scheduled task. This method will return true if the task was
     * successfuly removed: if it was still in the queued task queue.
     * @param task
     * @return wheter the task was successfuly unscheduled or not.
     */
    public boolean unschedule(Task task)
    {
        synchronized (this.queuedTaskQueue)
        {
            return this.queuedTaskQueue.remove(new DatedTask(task, -1));
        }
    }
    
    private Task nextTask() throws InterruptedException
    {
        this.globalTaskSemaphore.acquire();

        if(this.urgentTaskSemaphore.tryAcquire())
        {
            return this.urgentTaskQueue.poll();
        }
        
        return normalTaskQueue.poll();
    }
    
    private class DatedTask implements Task, Comparable<DatedTask>
    {

        private Task task;
        private long date;

        public DatedTask(Task task, long millis)
        {
            this.task = task;
            this.date = millis;
        }

        public long date()
        {
            return this.date;
        }


        public void execute()
        {
            this.task.execute();
        }

        @Override
        public boolean equals(Object another)
        {
            if (another instanceof DatedTask)
            {
                return this.task.equals(((DatedTask) another).task);
            }

            return false;
        }

        @Override
        public int hashCode()
        {
            return task.hashCode();
        }

        public int compareTo(DatedTask o)
        {
            // specific case when compareTo is called from the remove() method
            // in Java 1.5 (we created a dummy task with a -1 timestamp);
            if(this.date == -1 || o.date == -1)
            {
                if(this.task.equals(o.task)) return 0;
                else                         return 1;
            }

            return (int) (this.date - o.date);
        }
    }
}
