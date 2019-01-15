package PetriNet;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStarPrepare  {

    public static int[] arrivalStateTime;
    public static int[] AStarPrepareStart (PetriNet petriNet)throws Exception{

        int statePlaceNumber = petriNet.getStartState().getCurrentToken().length
                - petriNet.getEndNodeNumber()- petriNet.getStateNodeNumber();           //工件移动的状态数
        State star = petriNet.getStartState();
        int[] changeStar = new int[star.getCurrentToken().length];
        for(int i = statePlaceNumber; i<changeStar.length; i++ ){
            changeStar[i] = 1;                                          //把状态位都变为1
        }
        State end = new State();
        end.setCurrentToken(changeStar);                            //更新当前prepare网的endState
        petriNet.setEndState(end);

        int[] arrivalStateTime = new int[statePlaceNumber];            //定义系数表大小
        for (int i = 0; i<arrivalStateTime.length; i++)
        {
            int[] a = changeStar.clone();
            for(int j = statePlaceNumber; j<statePlaceNumber + petriNet.getEndNodeNumber(); j++ )
            {
                a[j] = 0;
            }
            a[i] = 1;
            State start = petriNet.getStartState();
            start.setCurrentToken(a);
            petriNet.setStartState(start);
            arrivalStateTime[i]= AStarKernel.AstarPrePare(petriNet,petriNet);
        }
        return arrivalStateTime;
    }

}
