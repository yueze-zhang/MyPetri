package PetriNet;

import java.util.*;

public class AStarKernel {
    static Queue<State> openList = new PriorityQueue<State>();                                                              // 创建open表 优先队列(升序)
    static List<State> closeList = new ArrayList<State>();
    //static Queue<State> succeed = new PriorityQueue<State>();    //创建close表
    static PetriNet petri;
    static State ulitamteState;

    public static void AStarStart(PetriNet o) throws Exception{
        petri = o;
        openList.clear();
        closeList.clear();
        openList.add(petri.getStartState());
        int count  = 0;// //输入起始状态
        int time = 0;

        while (!openList.isEmpty())
        {
            State current = openList.poll();//令OPEN表中第一个STATE 当作当前状态
            if(ulitamteState == null){
                if(command.JudgeEqualState(current,petri.getEndState())){                                                   //如果状态表找到目标
                    System.out.println("Time = " +current.getTime()+"count ="+count);
                    //command.DrawPath(current);
                    count++;
                    ulitamteState = current;
                }
                addNeighborNodeInOpen(current);
            }
            else if(ulitamteState.getTime()>current.getTime()){
                if(command.JudgeEqualState(current,petri.getEndState())){                                                   //如果状态表找到目标
                    System.out.println("Time = " +current.getTime()+"count ="+count);
                    //command.DrawPath(current);
                    count++;
                    ulitamteState = current;
                    if (count == 2) break;
                }
                addNeighborNodeInOpen(current);
            }

                                                                            //把当前状态收到各个变迁刺激后的下一个状态添加到OPEN表中
         }
         //State ultimateState = succeed.poll();

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
            if(command.TestTransitionToken(petri, current, transitionNodeNumber) ){ //当前可以触发变迁&&
                if(command.TestTransitionTime(petri, current, transitionNodeNumber)){
                    addAStateInOpen(petri, current, transitionNodeNumber);
                }
                else {
                    State newcurrent = command.ReduceWaitTime(petri, current,transitionNodeNumber);
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
            int hValue =command.CalcHValue(nextStateValue);
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
                child.setParent(current);
            }
            if (childTime == thisTime){
                int hValue =command.CalcHValue(nextStateValue);
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
