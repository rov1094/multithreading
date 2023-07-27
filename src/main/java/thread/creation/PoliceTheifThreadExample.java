package thread.creation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PoliceTheifThreadExample {

    private static final int MAX_PASSWORD=9999;

    public static void main(String[] args) {
        Random random=new Random();
        //Setting up vault and its password
        Vault vault=new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> threads=new ArrayList<>();
        threads.add(new AscendingThread(vault));
        threads.add(new DecendingThread(vault));
        threads.add(new PoliceThread());

        for (Thread thread:threads) {
            thread.start();
        }
    }

    //Creation of Vault
    private static class Vault{
        private int password;

        public Vault(int password){
            this.password=password;
        }

        public boolean isCorrectPassword(int guess){
            return this.password==guess;
        }

    }

    private static abstract class HackerThread extends Thread{
        protected Vault vault;

        public HackerThread(Vault vault){
            this.vault=vault;
            this.setName(this.getClass().getName());
            this.setPriority(MAX_PRIORITY);
        }

        @Override
        public synchronized void start() {
            System.out.println("Starting thread "+this.getName());
            super.start();
        }
    }

    private static class AscendingThread extends HackerThread {

        public AscendingThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int i=0;i<MAX_PASSWORD;i++){
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(this.vault.isCorrectPassword(i)){
                    System.out.println(this.getClass().getName()+" guessed the password");
                    System.exit(0);
                }
            }
        }
    }

    private static class DecendingThread extends HackerThread {

        public DecendingThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int i=MAX_PASSWORD;i>0;i--){
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(this.vault.isCorrectPassword(i)){
                    System.out.println(this.getClass().getName()+" guessed the password");
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread{
        @Override
        public void run() {
            for(int i=0;i<10;i++){
                try {
                    Thread.sleep(1000);
                    System.out.println("Police Coming in "+(10-i)+" seconds");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

            System.out.println("Game Over for Hackers");
            System.exit(0);
        }
    }
}
