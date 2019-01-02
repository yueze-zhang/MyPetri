package PetriNet;

public class command {
    //-------------------------------------------------------------------------------------
    //命令：判断该ID号的变迁能否触发
    public boolean TestTransition(PetriNet p, State state, int transitionNodeID){

        TransitionNode transitionNode = p.transitionNodesList.get(transitionNodeID);
        int[] inputEdge  = transitionNode.getInputedges(); //读取所有的链接 0.  5
        int[] stateVlaue = state.getCurrentPlace();       //获取当前的状态
        for (int i = 0; i<inputEdge.length; i++ ){
            if(stateVlaue[ inputEdge[i] ] <= 0) return false;       //如果上面的place没有token 那么返回false;
        }
        return true;
    }

    //-------------------------------------------------------------------------------------
    //命令，变迁触发
    public int[] ChangeTransition(PetriNet p, State state, int transitionNodeID){

        TransitionNode transitionNode = p.transitionNodesList.get(transitionNodeID);
        int[] inputEdge  = transitionNode.getInputedges(); //读取所有的链接 0.  5
        int[] outputEdge = transitionNode.getOutputedges();//读取所有的链接 0.  5
        int[] stateVlaue = state.getCurrentPlace();       //获取当前的状态
        for (int i = 0; i<inputEdge.length; i++ ){
            stateVlaue[ inputEdge[i] ] = stateVlaue[ inputEdge[i] ] - 1; //上面的place没有token-1
        }
        for (int i = 0; i<outputEdge.length; i++ ){
            stateVlaue[ outputEdge[i] ] = stateVlaue[ outputEdge[i] ] + 1; //下面的place没有token-1
        }
        return stateVlaue;
    }



}
