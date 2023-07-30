package race.condition.atomic.operation;

import java.util.Random;

public class MinMaxMetricsExample {
    public static void main(String[] args) {
        MinMaxMetrics minMaxMetrics=new MinMaxMetrics();
        BusinessLogic1 businessLogic1=new BusinessLogic1(minMaxMetrics);
        BusinessLogic2 businessLogic2=new BusinessLogic2(minMaxMetrics);
        MetricsPrinter metricsPrinter=new MetricsPrinter(minMaxMetrics);

        businessLogic1.start();
        businessLogic2.start();
        metricsPrinter.start();

    }

    private static class MinMaxMetrics {

        // Add all    necessary member variables
        private volatile long min;
        private volatile long max;

        /**
         * Initializes all member variables
         */
        public MinMaxMetrics() {
            // Add code here
            min=Long.MAX_VALUE;
            max=Long.MIN_VALUE;
        }

        /**
         * Adds a new sample to our metrics.
         */
        public synchronized void addSample(long newSample) {
            // Add code here
            if(newSample>max){
                max=newSample;
            }else if(newSample<min){
                min=newSample;
            }
        }

        /**
         * Returns the smallest sample we've seen so far.
         */
        public long getMin() {
            // Add code here
            return min;
        }

        /**
         * Returns the biggest sample we've seen so far.
         */
        public long getMax() {
            // Add code here
            return max;
        }
    }

    private static class MetricsPrinter extends Thread{

        private MinMaxMetrics metrics;

        public MetricsPrinter(MinMaxMetrics metrics){
            this.metrics=metrics;
        }
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long min=metrics.getMin();
                long max=metrics.getMax();
                System.out.println("Min and Max metrics are :"+min +" "+max);
            }
        }
    }

    private static class BusinessLogic1 extends Thread{

        private MinMaxMetrics metrics;

        Random random=new Random();
        public BusinessLogic1(MinMaxMetrics metrics){
            this.metrics=metrics;
        }

        @Override
        public void run() {
            while(true){
                long startTime=System.currentTimeMillis();
                try {
                    Thread.sleep(random.nextInt(100));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long endTime=System.currentTimeMillis();
                metrics.addSample(random.nextInt(100000));
            }
        }
    }

    private static class BusinessLogic2 extends Thread{

        private MinMaxMetrics metrics;

        Random random=new Random();
        public BusinessLogic2(MinMaxMetrics metrics){
            this.metrics=metrics;
        }

        @Override
        public void run() {
            while(true){
                long startTime=System.currentTimeMillis();
                try {
                    Thread.sleep(random.nextInt(100));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long endTime=System.currentTimeMillis();
                metrics.addSample(random.nextInt(100000));
            }
        }
    }

}
