import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class MainFix
{ 
   /* 
    * @class BuggyQueue
    *
    * @brief This class doesn't work properly when accessed via
    *        multiple threads since it's not synchronized properly.
    *
    */
    static class BuggyQueue
    {
        // Resizable-array implementation.
        private BlockingQueue<String> mQ = new ArrayBlockingQueue<>(mMaxIterations, false);

        // Insert msg at the tail of the queue.
        public void put(String msg){ mQ.offer(msg); }

        // Remove msg from the head of the queue.
        public String take(){ return mQ.poll(); }
    }

    static int mMaxIterations = 100;

    /*
     * Main entry point into the BuggyQueue class.
     */
    public static void main(String argv[]) {
        final BuggyQueue buggyQueue = new BuggyQueue();

        /* 
         * Create a producer thread.
         */
        Thread producer = 
            new Thread(new Runnable(){ 
                    public void run(){ 
                        for(int i = 0; i < mMaxIterations; i++)
                            buggyQueue.put(Integer.toString(i)); 
                    }});
        /* 
         * Create a consumer thread.
         */
        Thread consumer =
            new Thread(new Runnable(){
                    public void run(){ 
                        for(int i = 0; i < mMaxIterations; i++)
                            System.out.println(buggyQueue.take());
                    }});

        /* 
         * Run both threads concurrently.
         */
        consumer.start();
        producer.start();
    }
}
