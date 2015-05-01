package homeWork.ComputerOS;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * 生产者消费者问题
 * Created by bin on 2015/5/1.
 */
public class ProducerAndCustomer {

        private Queue<Integer> buffer = new LinkedList<Integer>();

        private final int maxSize = 10;

        public static void main(String[] args){
            int num  = 20;
            Random random = new Random();
            ProducerAndCustomer pac = new ProducerAndCustomer();
            for(int i=0;i<num;i++){
                if(random.nextBoolean()){
                    new Thread(new Producer(pac, 1, i)).start();
                }else{
                    new Thread(new Customer(pac, 1, i)).start();
                }
            }
        }

        private synchronized void produce(int needNum, int who){
            if(needNum+buffer.size()>maxSize){
                System.out.println("The buffer is full...");
            }else{
                System.out.println("I'm " + who + ", I'm producing....");
                buffer.add(1);
                System.out.println(".............................I'm "+ who+ ", Producing end...The current num is " + buffer.size());
            }
        }

    private synchronized void consume(int needNum, int who){
        if(buffer.size()-needNum<0){
            System.out.println("The buffer is not enough...");
        }else{
            System.out.println("I'm " + who + ", I'm consuming....");
            buffer.poll();
            System.out.println(".............................I'm "+ who+ ", Consuming end...The current num is " + buffer.size());
        }
    }

    static class Producer implements Runnable{

        private ProducerAndCustomer pac;

        private int needNum;

        private int who;

        public Producer(ProducerAndCustomer pac, int needNum, int who){
            this.needNum = needNum;
            this.pac = pac;
            this.who = who;
        }

        @Override
        public void run() {
            pac.produce(needNum, who);
        }
    }

    static class Customer implements Runnable{

        private ProducerAndCustomer pac;

        private int needNum;

        private int who;

        public Customer(ProducerAndCustomer pac, int needNum, int who){
            this.needNum = needNum;
            this.pac = pac;
            this.who = who;
        }

        @Override
        public void run() {
            pac.consume(needNum, who);
        }

    }


}
