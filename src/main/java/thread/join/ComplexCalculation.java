package thread.join;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ComplexCalculation {

    public static void main(String[] args) throws InterruptedException {
        ComplexCalculation c=new ComplexCalculation();
        BigInteger result=c.calculateResult(new BigInteger("10"),new BigInteger("2"),new BigInteger("100"),new BigInteger("100"));
        System.out.println("Result is :"+result);
    }

    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) throws InterruptedException {
        BigInteger result = BigInteger.ZERO;

        //Creation of thread
        List<PowerCalculatingThread> powerCalculatingThreads=new ArrayList<>();
        powerCalculatingThreads.add(new PowerCalculatingThread(base1,power1));
        powerCalculatingThreads.add(new PowerCalculatingThread(base2,power2));

        //Starting thread
        powerCalculatingThreads.forEach(Thread::start);

        //joining the threads
        for (PowerCalculatingThread thread : powerCalculatingThreads) {
            thread.join();
        }

        for(PowerCalculatingThread thread : powerCalculatingThreads){
            result=result.add(thread.getResult());
        }

        /*
            Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
            Where each calculation in (..) is calculated on a different thread
        */
        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
           /*
           Implement the calculation of result = base ^ power
           */

            for(BigInteger i=BigInteger.ZERO;i.compareTo(power)<0;i=i.add(BigInteger.ONE)){
                result=result.multiply(base);
            }

        }

        public BigInteger getResult() { return result; }
    }
}