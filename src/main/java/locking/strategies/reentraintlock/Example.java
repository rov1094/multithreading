package locking.strategies.reentraintlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class Example {

    public static void main(String[] args) {
        //Stimualting UI
        PricesContainer pricesContainer=new PricesContainer();
        PriceUpdater priceUpdater=new PriceUpdater(pricesContainer);
        PriceDisplay priceDisplay=new PriceDisplay(pricesContainer);
        priceUpdater.start();
        priceDisplay.start();
    }

    public static class PriceDisplay extends Thread {

        private PricesContainer pricesContainer;

        PriceDisplay(PricesContainer pricesContainer){
            this.pricesContainer=pricesContainer;
        }

        @Override
        public void run() {
            while(true){
                if(pricesContainer.getLockObject().tryLock()){
//                if(true){ // Will lag
//                    pricesContainer.getLockObject().lock();
                    Consumer<PricesContainer> consumer=(pricesContainer1)->{
                        System.out.println("BitCoin Price "+pricesContainer1.getBitcoinPrice());
                        System.out.println("Ether Price "+pricesContainer1.getEtherPrice());
                        System.out.println("Litecoin Price "+pricesContainer1.getLitecoinPrice());
                        System.out.println("BitCoinCash Price "+pricesContainer1.getBitcoinCashPrice());
                    };
                    pricesContainer.getLockObject().unlock();
                    consumer.accept(pricesContainer);
                }else{
                    System.out.println("Nothing to update");
                }
            }
        }
    }

    public static class PricesContainer {
        private Lock lockObject = new ReentrantLock();

        private double bitcoinPrice;
        private double etherPrice;
        private double litecoinPrice;
        private double bitcoinCashPrice;
        private double ripplePrice;

        public Lock getLockObject() {
            return lockObject;
        }

        public double getBitcoinPrice() {
            return bitcoinPrice;
        }

        public void setBitcoinPrice(double bitcoinPrice) {
            this.bitcoinPrice = bitcoinPrice;
        }

        public double getEtherPrice() {
            return etherPrice;
        }

        public void setEtherPrice(double etherPrice) {
            this.etherPrice = etherPrice;
        }

        public double getLitecoinPrice() {
            return litecoinPrice;
        }

        public void setLitecoinPrice(double litecoinPrice) {
            this.litecoinPrice = litecoinPrice;
        }

        public double getBitcoinCashPrice() {
            return bitcoinCashPrice;
        }

        public void setBitcoinCashPrice(double bitcoinCashPrice) {
            this.bitcoinCashPrice = bitcoinCashPrice;
        }

        public double getRipplePrice() {
            return ripplePrice;
        }

        public void setRipplePrice(double ripplePrice) {
            this.ripplePrice = ripplePrice;
        }
    }

    public static class PriceUpdater extends Thread {
        private PricesContainer pricesContainer;
        private Random random = new Random();

        public PriceUpdater(PricesContainer pricesContainer) {
            this.pricesContainer = pricesContainer;
        }

        @Override
        public void run() {
            while (true) {
                pricesContainer.getLockObject().lock();
                System.out.println("Locked by PriceUpdater for 5 sec");
                try {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                    pricesContainer.setBitcoinPrice(random.nextInt(20000));
                    pricesContainer.setEtherPrice(random.nextInt(2000));
                    pricesContainer.setLitecoinPrice(random.nextInt(500));
                    pricesContainer.setBitcoinCashPrice(random.nextInt(5000));
                    pricesContainer.setRipplePrice(random.nextDouble());
                } finally {
                    pricesContainer.getLockObject().unlock();
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
