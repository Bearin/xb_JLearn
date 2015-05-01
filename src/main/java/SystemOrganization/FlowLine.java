package SystemOrganization;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 *  模拟流水线
 * Created by bin on 2015/5/1.
 */
public class FlowLine {
    public static void main(String[] args){
        FlowLine fl = new FlowLine(5);
        fl.startFlowLine();
        fl.outResults();
    }

    //输入数据队列，使用随机数生成队列中的元素
    private Queue<Integer> queue = new LinkedList<Integer>();

    //相加结果队列，用来临时记录两个数据的相加结果
    private Queue<Integer> results = new LinkedList<Integer>();

    //相加结果输出时间队列, 记录流水线完成的时间点
    private Queue<Integer> exceptTimes = new LinkedList<Integer>();

    private int totalTime = 0;

    /**
     * 结果输出
     */
    private void outResults(){
        System.out.println("The totalTime is : " + this.totalTime);
        System.out.println("The add-result is : " + this.queue.peek());
    }

    /**
     * 初始化数据
     * @param n
     */
    public FlowLine(int n){
        Random random = new Random();
        for(int i=0;i<n;i++){
            queue.add(random.nextInt(10));
        }
        System.out.println("The queue is : " + queue.toString());
    }

    /**
     * 开始流水线
     */
    private void startFlowLine(){
        while(true){
            totalTime++;
            if(queue.size()>=2){
                results.add(queue.poll()+queue.poll());
                exceptTimes.add(totalTime + 3);
            }
            if(totalTime==exceptTimes.peek()){
                queue.add(results.poll());
                exceptTimes.poll();
            }
            if(queue.size()==1&&results.size()==0){
                break;
            }
        }
    }
}
