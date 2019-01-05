package PetriNet;

import java.util.*;

public class AStarKernel {
    static Queue<State> openList = new PriorityQueue<State>();                                                              // 创建open表 优先队列(升序)
    static List<State> closeList = new ArrayList<State>();                                                                  //创建close表
    static PetriNet petri;


    public static void AStarStart(PetriNet o){
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
    private static void  addNeighborNodeInOpen(State current){
        for(int transitionNodeNumber = 0; transitionNodeNumber < petri.transitionNodesList.size(); transitionNodeNumber++)  //检测出一共有多少个个变迁
        {
            addAStateInOpen(current, transitionNodeNumber);
        }
    }

    /**
     * 添加一个邻结点到open表
     */
    private static void addAStateInOpen (State current, int transitionNodeNumber){
        if(command.TestTransition(petri, current, transitionNodeNumber))                                                //-判断当前是否可以触发
        {
            int[] nextStateValue = command.ChangeTransition(petri, current, transitionNodeNumber).clone();              //变迁触发后产生新的状态值
            int transitionCost = petri.transitionNodesList.get(transitionNodeNumber).getTransitionCost();               //获取当前变迁的花费
            int nextGValue  = current.getgValue() + transitionCost;                                                     // 计算邻结点的G值: G是个准确的值，是起点到当前结点的代价
            State child = findNodeInOpen(nextStateValue);                                                               //查找这个状态表里在不在OPEn表中
            if (child == null)
            {
                int hValue = 0;
                child = new State(nextStateValue, current, nextGValue, hValue,transitionNodeNumber );                                        //创建新的状态
                openList.add(child);
            }
            else if (child.getgValue() > nextGValue)                                                                    //如果Child在OPEn表中 ，并且Child的G比nextGValue还大，说明发现了到child状态的近路，近路是current状态这条路
            {
                child.setgValue(nextGValue);
                child.setParent(current);
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
