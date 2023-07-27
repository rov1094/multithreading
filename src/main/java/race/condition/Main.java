package race.condition;

/**
 * Resource Sharing issue
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter=new InventoryCounter();
        IncrementThread incrementThread=new IncrementThread(inventoryCounter);
        DecrementThread decrementThread=new DecrementThread(inventoryCounter);
        incrementThread.start();
        decrementThread.start();

        incrementThread.join();// we joined increment thread after starting decrement thread this will not give proper result
        decrementThread.join();

        System.out.println("Inventory Item in the stock are : "+inventoryCounter.getInventory()); // Error in result
    }

    private static class IncrementThread extends Thread{

        private InventoryCounter inventoryCounter;

        public IncrementThread(InventoryCounter inventoryCounter){
            this.inventoryCounter=inventoryCounter;
        }

        @Override
        public void run(){
            for (int i=0;i<10000;i++){
                inventoryCounter.increment();
            }
        }
    }

    private static class DecrementThread extends Thread{

        private InventoryCounter inventoryCounter;

        public DecrementThread(InventoryCounter inventoryCounter){
            this.inventoryCounter=inventoryCounter;
        }

        @Override
        public void run(){
            for (int i=0;i<10000;i++){
                inventoryCounter.decrement();
            }
        }

    }

    private static class InventoryCounter{
        private int item;

        public synchronized void increment(){
            item++;
        }

        public synchronized void decrement(){
            item--;
        }

        public synchronized int getInventory(){
            return item;
        }
    }
}
