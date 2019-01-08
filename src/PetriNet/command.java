package PetriNet;

import java.util.Arrays;

public class command {
    //-------------------------------------------------------------------------------------
    //命令：判断该ID号的变迁能否触发，token是否满足条件
    public static boolean TestTransitionToken(PetriNet p, State state, int transitionNodeID) {

        TransitionNode transitionNode = p.transitionNodesList.get(transitionNodeID);
        int[] inputEdge = transitionNode.getInputedges(); //读取所有的链接 0.  5
        int[] stateVlaue = state.getCurrentToken();       //获取当前的状态
        int[] stateWaitTime = state.getCurrentPlaceWaitTime();
        for (int i = 0; i < inputEdge.length; i++) {
            if (stateVlaue[inputEdge[i]] <= 0) return false;       //如果上面的place没有token 那么返回false;
        }
        return true;
    }

    //-------------------------------------------------------------------------------------
    //命令：判断该ID号的变迁能否触发，Time是否满足条件
    public static boolean TestTransitionTime(PetriNet p, State state, int transitionNodeID) {

        TransitionNode transitionNode = p.transitionNodesList.get(transitionNodeID);
        int[] inputEdge = transitionNode.getInputedges(); //读取所有的链接 0.  5
        int[] stateVlaue = state.getCurrentToken();       //获取当前的状态
        int[] stateWaitTime = state.getCurrentPlaceWaitTime();
        for (int i = 0; i < inputEdge.length; i++) {
            if (stateWaitTime[inputEdge[i]] != 0) return false;    //如果上面的place仍在等待中 那么返回false;
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

    //-------------------------------------------------------------------------------------
    //命令，增加状态的延时时间
    public static int[] ChangeWaitTime(PetriNet p,State state, int transitionNodeID){
        TransitionNode transitionNode = p.transitionNodesList.get(transitionNodeID);
        int[] outputEdge = transitionNode.getOutputedges().clone();//读取所有的链接 0.  5
        int[] currentWaitTime = state.getCurrentPlaceWaitTime().clone();       //获取当前的状态
        for (int i = 0; i < outputEdge.length; i++) {
            Place place = p.placeList.get(outputEdge[i]);                       //获取当前变迁下面的节点，并让节点所需要的时间赋予等待的时间
            currentWaitTime[outputEdge[i]] += place.getProcessTime();
        }
        return currentWaitTime;
    }


    //-------------------------------------------------------------------------------------
    //命令：把延时时间变的可用，同时增加state里的time值, 依照变迁来寻找延时
    @Deprecated
    public static State ReduceWaitTime(PetriNet p,State state, int transitionNodeID) throws Exception{
        TransitionNode transitionNode = p.transitionNodesList.get(transitionNodeID);
        int[] inputEdge = transitionNode.getInputedges().clone();//读取所有的链接 0.  5
        int[] currentWaitTime = state.getCurrentPlaceWaitTime().clone();       //获取当前的状态
        int[] saveNum = new int[inputEdge.length];
        int minNum = 0;
        for (int i = 0; i < saveNum.length; i++) {
            saveNum[i] = currentWaitTime[inputEdge[i]];
            minNum = saveNum[0];
            for(int j=0;j<saveNum.length;j++) {
                System.out.print(saveNum[j] + " ");
                if (saveNum[j] < minNum)   // 判断最小值
                    minNum = saveNum[j];
            }
        }
        for (int i = 0; i < currentWaitTime.length; i++) {
            currentWaitTime[i] = currentWaitTime[i] - minNum;
            if (currentWaitTime[i]<0){ currentWaitTime[i] = 0; }
        }
        State newCurrent =(State)state.clone();
        newCurrent.setCurrentPlaceWaitTime(currentWaitTime);
        newCurrent.setAddTime(minNum);
        return newCurrent;
    }


    //-------------------------------------------------------------------------------------
    //命令：把延时时间变的可用，同时增加state里的time值, 减少currentWaitTime除0之外的最小数
    public static State ReduceWaitTime(PetriNet p,State state){
        int[] currentWaitTime = state.getCurrentPlaceWaitTime().clone();       //获取当前的状态
        int minNum = 999;

        for(int i = 0; i<currentWaitTime.length; i++){
            if(currentWaitTime[i] != 0) {                                      //除0之外寻找最小值
                if (currentWaitTime[i] < minNum) minNum = currentWaitTime[i];
            }
        }
        for (int i = 0; i < currentWaitTime.length; i++) {              //更新currentWaitTime
            currentWaitTime[i] = currentWaitTime[i] - minNum;
            if (currentWaitTime[i]<0){ currentWaitTime[i] = 0; }
        }
        state.setCurrentPlaceWaitTime(currentWaitTime);
        state.setAddTime(minNum);
        return state;
    }

    //-------------------------------------------------------------------------------------
    //命令：数组求和
    public static int sumInt(int[] myArray){
        int sum = 0;
        for (int i = 0; i < myArray.length; i++)
            sum += myArray[i];
        return sum; // 返回总和
    }

    //命令：数组求和
    public static int sumMax(int[] myArray){
        int max = myArray[0];
        for (int i = 0; i < myArray.length; i++)
        {
            if (max < myArray[i]){ max = myArray[i]; }
        }
        return max; // 返回总和
    }

}
