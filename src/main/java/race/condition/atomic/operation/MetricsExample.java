package race.condition.atomic.operation;

import java.util.Random;

public class MetricsExample {

    public static void main(String[] args) {
        Metrics metrics=new Metrics();
        BusinessLogic1 businessLogic1=new BusinessLogic1(metrics);
        BusinessLogic2 businessLogic2=new BusinessLogic2(metrics);
        MetricsPrinter metricsPrinter=new MetricsPrinter(metrics);

        businessLogic1.start();
        businessLogic2.start();
        metricsPrinter.start();


    }

    private static class MetricsPrinter extends Thread{

        private Metrics metrics;

        public MetricsPrinter(Metrics metrics){
            this.metrics=metrics;
        }
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                double currentAverage=metrics.getAverage();
                System.out.println("Average metrics is :"+currentAverage);
            }
        }
    }

    private static class BusinessLogic1 extends Thread{

        private Metrics metrics;

        Random random=new Random();
        public BusinessLogic1(Metrics metrics){
            this.metrics=metrics;
        }

        @Override
        public void run() {
            while(true){
                long startTime=System.currentTimeMillis();
                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long endTime=System.currentTimeMillis();
                metrics.addSample(endTime-startTime);
            }
        }
    }

    private static class BusinessLogic2 extends Thread{

        private Metrics metrics;

        Random random=new Random();
        public BusinessLogic2(Metrics metrics){
            this.metrics=metrics;
        }

        @Override
        public void run() {
            while(true){
                long startTime=System.currentTimeMillis();
                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long endTime=System.currentTimeMillis();
                metrics.addSample(endTime-startTime);
            }
        }
    }

    private static class Metrics{
        private long count=0; // Number of samples captures

        private volatile double average=0.0; // volatile makes double thread safe and will force update 64 bits in single core itself

        public void addSample(long sample){
            double currentSum=count*average;
            count++;
            average=(currentSum+sample)/count; // Here average can be not thread safe as it is 64 bits so we will use volatile keyword
        }

        public double getAverage(){ // this is thread safe as we studied in last lecture
            return this.average;
        }
    }
}
