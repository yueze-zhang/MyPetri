package PetriNet;

import java.util.Arrays;

public class command {
    //-------------------------------------------------------------------------------------
    //命令：判断该ID号的变迁能否触发
    public static boolean TestTransition(PetriNet p, State state, int transitionNodeID) {

        TransitionNode transitionNode = p.transitionNodesList.get(transitionNodeID);
        int[] inputEdge = transitionNode.getInputedges(); //读取所有的链接 0.  5
        int[] stateVlaue = state.getCurrentToken();       //获取当前的状态
        int[] stateWaitTime = state.getCurrentPlaceWaitTime();
        for (int i = 0; i < inputEdge.length; i++) {
            if (stateVlaue[inputEdge[i]] <= 0) return false;       //如果上面的place没有token 那么返回false;
            //if (stateWaitTime[inputEdge[i]] != 0) return false;    //如果上面的place仍在等待中 那么返回false;
        }
        return true;
    }

    //-------------------------------------------------------------------------------------
    //命令，变迁触发
    public static int[] ChangeTransition(PetriNet p, State state, int transitionNodeID) {

        TransitionNode transitionNode = p.transitionNodesList.get(transitionNodeID);
        int[] inputEdge = transitionNode.getInputedges().clone(); //读取所有的链接 0.  5
        int[] outputEdge = transitionNode.getOutputedges().clone();//读取所有的链接 0.  5
        int[] stateVlaue = state.getCurrentToken().clone();       //获取当前的状态
        for (int i = 0; i < inputEdge.length; i++) {
            stateVlaue[inputEdge[i]] = stateVlaue[inputEdge[i]] - 1; //上面的place没有token-1
        }
        for (int i = 0; i < outputEdge.length; i++) {
            stateVlaue[outputEdge[i]] = stateVlaue[outputEdge[i]] + 1; //下面的place没有token-1
        }
        return stateVlaue;
    }

    //-------------------------------------------------------------------------------------
    //命令，判断两个State token 是否一致
    public static boolean JudgeEqualState(State o, State p) {
        return Arrays.equals(o.getCurrentToken(), p.getCurrentToken());
    }

    //-------------------------------------------------------------------------------------
    //命令，画出当前节点的所有上面的节点
    public static void DrawPath(State current) {
        while (current != null) {
            System.out.println(current.toString());             //打印current状态
            current = current.getParent();
        }
    }



}
