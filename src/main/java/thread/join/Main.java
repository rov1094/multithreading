package thread.join;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Calculate Factorials of the given number
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers= Arrays.asList(1000000L,3435L,34354L,2324L,4656L,23L,5556L);
        //Thread Creation
        List<FactorialThread> threads=new ArrayList<>();
        for (Long inputNumber:inputNumbers){
            threads.add(new FactorialThread(inputNumber));
        }

        //Starting each threads
        for (Thread thread:threads){
            // Instead of interrupting the thread
            // We can set it to demon thread
            thread.setDaemon(true);// By using this application will not wait for this thread to be completed.
            thread.start();
        }

        for(Thread thread:threads){
            thread.join(2000); // Without using join we were running into race condition //This will force the threads to finish the work
        }

        //Result :
        for (int i=0;i<inputNumbers.size();i++){
            FactorialThread thread=threads.get(i);
            if(thread.isFinished()){ // We were checking the result but thread were still calculating
                System.out.println("Factorial for input number : "+thread.getInputNumber()+ " is "+thread.getResult());
            }else{
                System.out.println("Factorial calculation is still in progrss for the input number : "+thread.getInputNumber());
                System.out.println("Terminating the thread : ");
                thread.interrupt(); // Added this as Application was not getting killed
            }
        }
    }


    private static class FactorialThread extends Thread{
    private long inputNumber;
    private BigInteger result=BigInteger.ZERO;
    private boolean isFinished;

    public FactorialThread(Long inputNumber){
        this.inputNumber=inputNumber;
    }

        @Override
        public void run() {
            this.result=factorial(inputNumber);
            this.isFinished=true;
        }

        private BigInteger factorial(long inputNumber) {

            BigInteger tempResult=BigInteger.ONE;
            for(long i=inputNumber;i>0;i--){
                if(currentThread().isInterrupted()){ // Interruption needs to be handled or else it will not break
                    System.out.println("Thread interrupted "+currentThread().getName());
                    tempResult=BigInteger.ZERO;
                    break;
                }
                tempResult=tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

        public BigInteger getResult() {
            return result;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public long getInputNumber(){
        return this.inputNumber;
        }
    }
}
