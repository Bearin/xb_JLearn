package homeWork.ComputerOS;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *  读写问题
 * Created by bin on 2015/5/1.
 */
public class Rw {

    private Semaphore wsem;

    private Semaphore rsem;

    private int readerCount;

    private int n;

    public static void main(String[] args){
        final Rw rw = new Rw(10);
        Random random = new Random();
        for(int i=0;i<rw.n;i++){
            final int who = i;
            if(random.nextBoolean()){
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            rw.read(who);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                new Thread(run).start();
            }else{
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            rw.write(who);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                new Thread(run).start();
            }
        }
    }

    public Rw(int n){
        this.n = n;
        this.readerCount = 0;
        this.wsem = new Semaphore(1);
        this.rsem = new Semaphore(1);
    }

    private void read(int i) throws InterruptedException {
            rsem.acquire();
            readerCount++;
            if(readerCount==1){
                wsem.acquire();
            }
            rsem.release();
            System.out.println("I'm reader "+Integer.valueOf(i)+", I'm reading");
            System.out.println("...............................I'm reader "+Integer.valueOf(i)+", Read end...");
            rsem.acquire();
            readerCount--;
            if(readerCount==0){
                wsem.release();
            }
            rsem.release();
    }

    private void write(int i) throws InterruptedException {
        if(wsem.tryAcquire()){
            System.out.println("I'm writer " + Integer.valueOf(i) + ", I'm writing...");
            System.out.println("...............................I'm writer "+Integer.valueOf(i)+", Write end...");
            wsem.release();
        }
    }

}

