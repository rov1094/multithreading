package race.condition.datarace;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        SharedClass sharedClass=new SharedClass();

        Thread thread1=new Thread(()->{
            while(true)
                sharedClass.increment();
        });

        Thread thread2=new Thread(()->{
            while (true)
                sharedClass.checkForDataRace();
        });

        thread1.start();
        thread2.start();
    }



    private static class SharedClass{
        private volatile int x=0; // By Declaring here , we are avoiding data race condition
        private volatile int y=0;

        public void increment(){
            x++;
            y++;
        }

        public void checkForDataRace(){
            if(y>x){
                System.out.println("This should not be possible");
            }
        }
    }
}
