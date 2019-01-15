package PetriNet;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
/**
 * 用DOM方式读取xml文件
 * @author lune
 */
public class xmlDoc {

    private static DocumentBuilderFactory dbFactory = null;
    private static DocumentBuilder db = null;
    private static Document document = null;
    private static Map<Integer,Place> places = null;
    private static Map<Integer,TransitionNode> transitionNodes = null;
    private static int starNodeNumber;
    private static int endNodeNumber;
    private static int stateNodeNumber;


    static {
        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            db = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
//--------------------------------------------------------------------------------
//                读取XML文件
//--------------------------------------------------------------------------------
    public static PetriNet getPetriNetDocument(String fileName)throws Exception{   //将给定URI的内容解析为一个XML文档,并返回Document对象
        document = db.parse(fileName);                 //按文档顺序返回包含在文档中且具有给定标记名称的所有 Element 的 NodeList
        Map<Integer,Place> place = getPlace(document);
        Map<Integer,TransitionNode> transitionNodes = getTransitionNode(document);
        State startState = getStartState(document);
        State endState = getEndState(document);
        PetriNet petriNet = new PetriNet(place,transitionNodes,startState,endState);
        return petriNet;
    }

    public static PetriNet getPetriNetPrepareDocument(String fileName)throws Exception{   //将给定URI的内容解析为一个XML文档,并返回Document对象
        document = db.parse(fileName);                 //按文档顺序返回包含在文档中且具有给定标记名称的所有 Element 的 NodeList
        Map<Integer,Place> place = getPlace(document);
        Map<Integer,TransitionNode> transitionNodes = getTransitionNode(document);
        State startState = getStartState(document);
        State endState = getEndState(document);
        getNodeNumber(document);

        PetriNet petriNetPrepare = new PetriNet(place,transitionNodes,startState,endState);
        petriNetPrepare.setEndNodeNumber(endNodeNumber);
        petriNetPrepare.setStarNodeNumber(starNodeNumber);
        petriNetPrepare.setStateNodeNumber(stateNodeNumber);
        return petriNetPrepare;
    }
//--------------------------------------------------------------------------------
//                读取XML中的PLACE信息
//--------------------------------------------------------------------------------
        public static Map<Integer,Place> getPlace(Document document) throws Exception{
            NodeList placeList = document.getElementsByTagName("Place");
            places = new HashMap<Integer,Place>();

            for(int i=0;i<placeList.getLength();i++){
                Place place = new Place();                                           //获取第i个Place结点
                org.w3c.dom.Node node = placeList.item(i);                           //获取第i个book的所有属性
                NamedNodeMap namedNodeMap = node.getAttributes();                    //获取已知名为id的属性值
                String ID = namedNodeMap.getNamedItem("ID").getTextContent();       //System.out.println(id);
                place.setID(Integer.parseInt(ID));
                //获取place结点的子节点,包含了Test类型的换行
                NodeList cList = node.getChildNodes();
                //将一个Place里面的属性加入数组
                ArrayList<String> contents = new ArrayList<>();
                for(int j=1;j<cList.getLength();j+=2){
                    org.w3c.dom.Node cNode = cList.item(j);
                    String content = cNode.getFirstChild().getTextContent();
                    contents.add(content);

                }
                place.setLabel(contents.get(0));                                                    //存入Place标签
                place.setProcessTime(Integer.parseInt(contents.get(1)));                            //存入Place的需要进程时间
                //place.setProcessTime(Integer.parseInt(contents.get(2)));                            //存入加工时间
                places.put(Integer.parseInt(ID),place);
            }
            return places;
        }

//--------------------------------------------------------------------------------
//                读取XML中的TransitionNode信息
//--------------------------------------------------------------------------------
    public static Map<Integer,TransitionNode> getTransitionNode(Document document) throws Exception{
        NodeList transitionNodeList = document.getElementsByTagName("TransitionNode");
        transitionNodes = new HashMap<Integer, TransitionNode>();                                        //遍历transitionNode

        for(int i=0;i<transitionNodeList.getLength();i++){
            TransitionNode transitionNode = new TransitionNode();                                           //获取第i个TN结点
            org.w3c.dom.Node node = transitionNodeList.item(i);                                 //获取第i遍历transitionNode个所有属性
            NamedNodeMap namedNodeMap = node.getAttributes();                                   //获取已知名为id的属性值
            String ID = namedNodeMap.getNamedItem("ID").getTextContent();
            transitionNode.setID(Integer.parseInt(ID)); //添加ID

            NodeList cList = node.getChildNodes();

            //将一个transitionNode里面的属性加入数组
            ArrayList<String> contents = new ArrayList<>();
            for(int j=1;j<cList.getLength();j+=2){

                org.w3c.dom.Node cNode = cList.item(j);
                String content = cNode.getFirstChild().getTextContent();
                contents.add(content);
                //System.out.println(contents);
            }
            transitionNode.setLabel(contents.get(0)); //添加标签

            String s1 = contents.get(1);   //拆分Inputegdes字符串为链表
            String[] split1 = s1.split(" ");
            int[] A1=new int[split1.length];
            for(int z =0;z<split1.length;z++){
                int ss = new Integer(split1[z]);
                A1[z] = ss;
            }
            transitionNode.setInputedges(A1);

            String s2 = contents.get(2);   //拆分Outputegdes字符串为链表
            String[] split2 = s2.split(" ");
            int[] A2=new int[split2.length];
            for(int z =0;z<split2.length;z++){
                int ss = new Integer(split2[z]);
                A2[z] = ss;
            }
            transitionNode.setOutputedges(A2);
            transitionNode.setTransitionCost(Integer.parseInt(contents.get(3)));                                        //当前触发变迁所需要的花费
            transitionNodes.put(Integer.parseInt(ID),transitionNode);
        }
        return transitionNodes;
    }
    //--------------------------------------------------------------------------------
    //                读取XML中的初始STARTSTATE信息
    //--------------------------------------------------------------------------------
    public static State getStartState(Document document) throws Exception{
        NodeList placeList = document.getElementsByTagName("StartState");

            State state = new State();
            org.w3c.dom.Node node = placeList.item(0);
            NodeList cList = node.getChildNodes();
            org.w3c.dom.Node cNode = cList.item(1);
            String content = cNode.getFirstChild().getTextContent();
            String s1 = content;
            String[] split1 = s1.split(" ");
            int[] A1=new int[split1.length];
            for(int z =0;z<split1.length;z++){
                int ss = new Integer(split1[z]);
                A1[z] = ss;
            }
            state.setCurrentToken(A1);
            int[] length = new int[state.getCurrentToken().length];
            state.setCurrentPlaceWaitTime(length);
        return state;
    }

    //--------------------------------------------------------------------------------
    //                读取XML中的初始ENDSTATE信息
    //--------------------------------------------------------------------------------
    public static State getEndState(Document document) throws Exception{
        NodeList placeList = document.getElementsByTagName("EndState");

        State state = new State();
        org.w3c.dom.Node node = placeList.item(0);
        NodeList cList = node.getChildNodes();
        org.w3c.dom.Node cNode = cList.item(1);
        String content = cNode.getFirstChild().getTextContent();
        String s1 = content;
        String[] split1 = s1.split(" ");
        int[] A1=new int[split1.length];
        for(int z =0;z<split1.length;z++){
            int ss = new Integer(split1[z]);
            A1[z] = ss;
        }
        state.setCurrentToken(A1);
        return state;
    }

    //--------------------------------------------------------------------------------
    //                读取XML中的初始NodeNumber信息
    //--------------------------------------------------------------------------------
    public static void getNodeNumber(Document document) throws Exception{
        NodeList placeList = document.getElementsByTagName("NodeNumber");
                                //获取第i个Place结点
            org.w3c.dom.Node node = placeList.item(0);                           //获取第i个book的所有属性
            NamedNodeMap namedNodeMap = node.getAttributes();                    //获取已知名为id的属性值
            //获取place结点的子节点,包含了Test类型的换行
            NodeList cList = node.getChildNodes();
            //将一个Place里面的属性加入数组
            ArrayList<String> contents = new ArrayList<>();
            for(int j=1;j<cList.getLength();j+=2){
                org.w3c.dom.Node cNode = cList.item(j);
                String content = cNode.getFirstChild().getTextContent();
                contents.add(content);
            }
            starNodeNumber = Integer.parseInt(contents.get(0));
            endNodeNumber = Integer.parseInt(contents.get(1));;
            stateNodeNumber = Integer.parseInt(contents.get(2));
    }



}


