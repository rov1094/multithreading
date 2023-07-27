package thread.termination;

public class Main {

    public static void main(String[] args) {
        Thread thread=new Thread(new BlockingThread());

        thread.start();
        thread.interrupt();

    }

    private static class BlockingThread implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(5000000);
            } catch (InterruptedException e) {
                System.out.println("Existing blocking thread");
            }
        }
    }
}
