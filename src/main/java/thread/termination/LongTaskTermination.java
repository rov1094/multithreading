package thread.termination;

import java.math.BigInteger;

/**
 * Caculate base to the power 10
 */
public class LongTaskTermination {
    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(new LongRunningTask(new BigInteger("1000000"),new BigInteger("1000000")));// This will consume lots of time
//        thread.setDaemon(true);// Daemon Thread does not block our application termination
        thread.start();
        Thread.sleep(5000);
        thread.interrupt(); // Comment when daemon is true to see the behaviour,
                            //application will be closed irrespective of deomon thread work has compelted or not
    }

    private static class LongRunningTask implements Runnable{
        BigInteger base;
        BigInteger power;

        public LongRunningTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }



        @Override
        public void run() {
            System.out.println("Value of Base "+base+" and power "+power+" is "+pow(base,power));


        }
        BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result=BigInteger.ONE;// Pow Zero ==1
            for (BigInteger i=BigInteger.ZERO;i.compareTo(power)!=0;i=i.add(BigInteger.ONE)){
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("Thread Interrupted ");
                    return BigInteger.ZERO;
                }
                result=result.multiply(base);
            }
            return result;
        }
    }
}
