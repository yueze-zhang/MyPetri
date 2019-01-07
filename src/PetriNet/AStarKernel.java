package PetriNet;

import java.util.*;

public class AStarKernel {
    static Queue<State> openList = new PriorityQueue<State>();                                                              // 创建open表 优先队列(升序)
    static List<State> closeList = new ArrayList<State>();                                                                  //创建close表
    static PetriNet petri;


    public static void AStarStart(PetriNet o) throws Exception{
        petri = o;
        openList.clear();
        closeList.clear();
        openList.add(petri.getStartState());                                                                            // //输入起始状态
        while (!openList.isEmpty())
        {
            State current = openList.poll();                                                                            //令OPEN表中第一个STATE 当作当前状态
            if(command.JudgeEqualState(current,petri.getEndState())){                                                   //如果状态表找到目标
                System.out.println("---------------SUCCESS!!!!-----------------");
                command.DrawPath(current);                                                                              //画图
                break;
            }
            if (current.getParent()!=null){
                System.out.println("父亲状态"+ current.getParent().toString());
            }
            System.out.println("当前状态"+current.toString());
            System.out.println("-------------------------------------");
            closeList.add(current);
            addNeighborNodeInOpen(current);                                                                             //把当前状态收到各个变迁刺激后的下一个状态添加到OPEN表中
         }
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
        if (count == petri.transitionNodesList.size()){
            current = command.ReduceWaitTime(petri, current);   //这句话出问题了，断点打在这里
            openList.add(current);
            count = 0;
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
            int hValue = 0;
            child = new State(nextStateValue, current, 0, hValue,transitionNodeNumber, time);
            child.setCurrentPlaceWaitTime(childWaitTime);//创建新的状态
            openList.add(child);
        }
        else if (child.getTime() + command.sumInt(child.getCurrentPlaceWaitTime()) > time + command.sumInt(childWaitTime))                                                                    //如果Child在OPEn表中 ，并且Child的G比nextGValue还大，说明发现了到child状态的近路，近路是current状态这条路
        {
            child.setTime(time);
            child.setCurrentPlaceWaitTime(childWaitTime);
            child.setParent(current);
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
