package thread.creation;

public class ThreadUncaughtException {

    public static void main(String[] args) {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("Intentional Exception");
            }
        });
        thread.setName("RV's Worker Thread");
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){

            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Exception is caught for thread : "+t.getName()+" Exception is "+e.getMessage());
            }
        });

        //starting the thread
        thread.start();
    }
}
