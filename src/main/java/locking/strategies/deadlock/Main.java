package locking.strategies.deadlock;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Intersection intersection=new Intersection();
        Thread trainA=new Thread(new TrainA(intersection));
        Thread trainB=new Thread(new TrainB(intersection));

        trainA.start();
        trainB.start();
    }

    private static class TrainA implements Runnable{

        private Intersection intersection;

        Random random=new Random();

        public TrainA(Intersection intersection){
            this.intersection=intersection;
        }



        @Override
        public void run() {
            while(true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                intersection.takeRoadA();
            }
        }
    }

    private static class TrainB implements Runnable{

        private Intersection intersection;

        Random random=new Random();

        public TrainB(Intersection intersection){
            this.intersection=intersection;
        }



        @Override
        public void run() {
            while(true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                intersection.takeRoadB();
            }
        }
    }

    private static class Intersection{
        Object roadA=new Object();
        Object roadB=new Object();

        public void takeRoadA(){
            synchronized (roadA){
                System.out.println("Road is locked by Thread : "+Thread.currentThread().getName());
                synchronized (roadB){
                    System.out.println("Train is passing through road A");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        public void takeRoadB(){
            synchronized (roadA){
                System.out.println("Road is locked by Thread : "+Thread.currentThread().getName());
                synchronized (roadB){
                    System.out.println("Train is passing through Road B");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
