package thread.creation;

public class ThreadCreationAndSetter {

    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(new Runnable() { // We are purposley not making it in lambda
            @Override
            public void run() {
                System.out.println("We are now in thread "+Thread.currentThread().getName());
                System.out.println("Priority of the thread");
            }
        });
        //Setting thread name
        thread.setName("New Worker Thread");
        // As we know that CPU set the dynamic priority for thread , which is used for scheduling the thread
        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("We are not in threa "+Thread.currentThread().getName()+" before starting the thread");
        thread.start();
        System.out.println("We are not in threa "+Thread.currentThread().getName()+" after starting the thread");

        Thread.sleep(10000); // This tells CPU to not schedule me for this much period
    }
}
