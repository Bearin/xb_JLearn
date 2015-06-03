package homeWork.SystemOrganization;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 *  计算机系统结构试验
 *  加法乘法静态多功能流水线调度
 * Created by bin on 2015/5/7.
 * @author xbearin@gmail.com
 */
public class AddMulFlowLine {


    public static void main(String[] args){
        System.out.println("---------------------------------------------------------------------------");
        for(int i=4;i<=20;i++){
            AddMulFlowLine fl = new AddMulFlowLine(i);
            fl.startFlowLine();
            fl.outResults();
            System.out.println("---------------------------------------------------------------------------");
        }
    }

    /**
     * 加法结果队列
     */
    private Queue<Integer> results = new LinkedList<Integer>();

    /**
     * 流水线输出时间记录队列,使用优先队列实现,执行poll(),时间按从小到大排列
     */
    private Queue<Integer> outTimes = new PriorityQueue<Integer>();

    /**
     * 乘法队列A
     */
    private Queue<Integer> inputMulQueueA = new LinkedList<Integer>();

    /**
     * 乘法队列B
     */
    private Queue<Integer> inputMulQueueB = new LinkedList<Integer>();

    /**
     * 待加队列
     */
    private Queue<Integer> tempResults = new LinkedList<Integer>();

    /**
     * 记录总时间
     */
    private int totalTime = 0;

    /**
     * 结果输出
     */
    private void outResults(){
        System.out.println("The totalTime is : " + this.totalTime);
        System.out.println("The add-result is : " + this.results.peek());
    }

    /**
     * 初始化数据
     * 为了方便, 使用随机数生成初始队列中的数据
     * @param n 初始乘法序列长度
     */
    public AddMulFlowLine(int n){
        Random random = new Random();
        for(int i=0;i<n;i++){
            inputMulQueueA.add(random.nextInt(10));
            inputMulQueueB.add(random.nextInt(10));
        }
        System.out.println("The queue is : ");
        System.out.println("............" + inputMulQueueA.toString());
        System.out.println("............" + inputMulQueueB.toString());
    }

    /**
     * 开始流水线
     */
    private void startFlowLine(){
        while(true){
            //时间片计数
            totalTime++;
            mul();
            add();
            while(outTimes.size()>0&&totalTime==outTimes.peek()){
                results.add(tempResults.poll());
                outTimes.poll();
            }
            //当临时结果队列为空, 并且加法结果队列只剩下一个元素时,流水线结束
            if(results.size()==1&&tempResults.size()==0){
                break;
            }
        }
    }

    /**
     * 加法器操作
     */
    private void add(){
        if(results.size()>=2){
            tempResults.add(results.poll() + results.poll());
            outTimes.add(totalTime + 3);
        }
    }

    /**
     * 乘法器操作
     */
    private void mul(){
        if(inputMulQueueA.size()>0 && inputMulQueueB.size()>0){
            tempResults.add(inputMulQueueA.poll() * inputMulQueueB.poll());
            outTimes.add(totalTime + 2);
        }
    }
}
