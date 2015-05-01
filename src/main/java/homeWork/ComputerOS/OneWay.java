package homeWork.ComputerOS;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *  单行道问题
 * Created by bin on 2015/5/1.
 */
public class OneWay {

    private Semaphore toLeftSem;

    private Semaphore toRightSem;

    private Semaphore passSem;

    private int passToLeftNum;

    private  int passToRightNum;

    private int threadNum;

    public OneWay(int n){
        this.threadNum = n;
        this.passToLeftNum = 0;
        this.passToRightNum = 0;
        toLeftSem = new Semaphore(1);
        toRightSem = new Semaphore(1);
        passSem = new Semaphore(1);
    }

    public static void main(String[] args){
        final OneWay oneWay = new OneWay(10);
        Random random = new Random();
        for(int i=0;i<oneWay.threadNum;i++){
            final int who = i;
            if(random.nextBoolean()){
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            oneWay.toLeft(who);
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
                            oneWay.toRight(who);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                new Thread(run).start();
            }
        }
    }

    private void toLeft(int i ) throws InterruptedException {
        toLeftSem.acquire();
        passToLeftNum++;
        if(passToLeftNum==1){
            passSem.acquire();
        }
        toLeftSem.release();
        System.out.println("I'm " + i + ", I'm passing to left...");
        System.out.println("................I'm "+ i +", passing to left end...");
        toLeftSem.acquire();
        passToLeftNum--;
        if(passToLeftNum==0){
            passSem.release();
        }
        toLeftSem.release();
    }

    private void toRight(int i) throws InterruptedException {
        toRightSem.acquire();
        passToRightNum++;
        if(passToRightNum ==1){
            passSem.acquire();
        }
        toRightSem.release();
        System.out.println("I'm " + i + ", I'm passing to right...");
        System.out.println("................I'm " + i + ", passing to right end...");
        toRightSem.acquire();
        passToRightNum--;
        if(passToRightNum ==0){
            passSem.release();
        }
        toRightSem.release();
    }
}
