package PetriNet;

import java.util.*;

public class AStarKernel {
    static Queue<State> openList = new PriorityQueue<State>();                                                              // 创建open表 优先队列(升序)
    static List<State> closeList = new ArrayList<State>();
    //static Queue<State> succeed = new PriorityQueue<State>();    //创建close表

    static PetriNet petri;
    static PetriNet prepare;
    static State ulitamteState;
    static int[] arrivalTime;

    public static int AstarPrePare(PetriNet p, PetriNet q) throws Exception {                        //A 星算法准备
        int time = 0;
        petri = q;
        prepare = p;
        openList.clear();
        closeList.clear();
        openList.add(prepare.getStartState());
        while (!openList.isEmpty())
        {
            State current = openList.poll();                                                                            //令OPEN表中第一个STATE 当作当前状态
            if(command.JudgePrepareEqualState(p,current)){                                                   //如果状态表找到目标
                //System.out.println("---------------SUCCESS!!!!-----------------");
                //command.DrawPath(current);                                                                              //画图
                return current.getTime();
            }
            //System.out.println("当前状态"+current.toString());
            //System.out.println("-------------------------------------");
            closeList.add(current);
            addNeighborNodeInOpen(current);
        }
        return 0;
    }





    public static void AStarStart(PetriNet o, int[] arrivaltime) throws Exception{
        arrivalTime = arrivaltime.clone();
        petri = o;
        openList.clear();
        closeList.clear();
        openList.add(petri.getStartState());
        int count  = 0;// //输入起始状态
        int time = 0;
        while (!openList.isEmpty())
        {
            State current = openList.poll();

            if (current.getStateID() == 1217171){
                int a= 0;
                a= a++;
            }


            //令OPEN表中第一个STATE 当作当前状态
            if(ulitamteState == null){          //如果，没有算出结果
                if(command.JudgeEqualState(current,petri.getEndState())){           //如果当前current是结束状态                                              //如果状态表找到目标
                    System.out.println("Time = " +current.getTime()+"count ="+count);       //打印当前状态 当前花费时间
                    command.DrawPath(current);
                    count++;
                    ulitamteState = current;                                        //把current记录为最佳结果
                }
                addNeighborNodeInOpen(current);                                     //把当前状态添加到邻接表中

            }
            else if(ulitamteState.getTime()>current.getTime()){                     //如果当前current的时间比之前得到的结果状态时间小的话
                if(command.JudgeEqualState(current,petri.getEndState())){            //如果状态表找到目标
                    System.out.println("Time = " +current.getTime()+"count ="+count);
                    command.DrawPath(current);
                    count++;
                    ulitamteState = current;
                    if (count >= 3) break;
                }
                if (count >= 3) break;
                addNeighborNodeInOpen(current);                                 //当前状态不是endstate 继续寻找
            }
        }
        System.out.println("---------------ultimateState!!!!-----------------");
        command.DrawPath(ulitamteState);
    }


    /**
     * 添加所有邻状态到open表
     */
    private static void  addNeighborNodeInOpen(State current) throws Exception{
        int count = 0;                                                                                                      //用于记录addAStateInOpen返回了多少false
        for(int transitionNodeNumber = 0; transitionNodeNumber < petri.transitionNodesList.size(); transitionNodeNumber++)  //检测出一共有多少个个变迁
        {
            if(command.TestTransitionToken(petri, current, transitionNodeNumber) ){                             //当前可以触发变迁&&
                if(command.TestTransitionTime(petri, current, transitionNodeNumber)){                           //当前时间可以触发变迁
                    addAStateInOpen(petri, current, transitionNodeNumber);
                }
                else {
                    State newcurrent = command.ReduceWaitTime(petri, current,transitionNodeNumber);             //时间不可以触发，更改时间，并把新的current 放入邻接表中
                    openList.add(newcurrent);
                }
            }else{
                count++;
            }
        }
    }

    /**
     * 添加一个邻结点到open表
     */
    private static void addAStateInOpen (PetriNet petri, State current, int transitionNodeNumber){
        int time = current.getTime();                                        //获取当前状态下经历的时间
        int[] childWaitTime = command.ChangeWaitTime(petri, current,transitionNodeNumber);                      //添加等待时间
        int[] nextStateValue = command.ChangeTransition(petri, current, transitionNodeNumber).clone();          //变迁触发后产生新的状态值
        //int transitionCost = petri.transitionNodesList.get(transitionNodeNumber).getTransitionCost();          //获取当前变迁的花费
        //int nextGValue  = current.getgValue() + transitionCost;                                               // 计算邻结点的G值: G是个准确的值，是起点到当前结点的代价
        State child = findNodeInOpen(nextStateValue);                                                           //查找这个状态表里在不在OPEn表中
        if (child == null)
        {
            int hValue =command.CalcHValue(nextStateValue,arrivalTime);
            child = new State(nextStateValue, current, 0, hValue,transitionNodeNumber, time);
            child.setCurrentPlaceWaitTime(childWaitTime);//创建新的状态
            openList.add(child);
        }
        else {
            int childTime = child.getTime() + command.sumMax(child.getCurrentPlaceWaitTime());     //存在节点经历的时间
            int thisTime = time + command.sumMax(childWaitTime);                                    //current节点触发后的节点经历的时间
            if (childTime > thisTime){
                child.setTime(time);
                child.setCurrentPlaceWaitTime(childWaitTime);
                child.setTransitionNodeID(transitionNodeNumber);
                child.setParent(current);
            }
            if (childTime == thisTime){
                int hValue =command.CalcHValue(nextStateValue,arrivalTime);
                child = new State(nextStateValue, current, 0, hValue,transitionNodeNumber, time);
                child.setCurrentPlaceWaitTime(childWaitTime);//创建新的状态
                openList.add(child);
            }
        }
    }

    //查询Open表里有没有stateValue状态
    private static State findNodeInOpen(int[] stateValue){
        if (stateValue == null || openList.isEmpty()) return null;
        for (State state : openList)
        {                                                                                                               //遍历整个OPEN表查看当前状态是否在OPEN表中
            if (state.equals(stateValue)) { return state; }
        }
        return null;
    }
}
