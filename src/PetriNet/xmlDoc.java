package PetriNet;
import java.util.ArrayList;
import java.util.List;

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
    private static List<Place> places = null;
    private static List<TransitionNode> transitionNodes = null;
    private static List<InputEdge> inputEdges = null;
    private static List<OutputEdge> outputEdges = null;


    static{
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
        document = db.parse(fileName);                                                  //按文档顺序返回包含在文档中且具有给定标记名称的所有 Element 的 NodeList
        List<Place> place = getPlace(document);
        List<TransitionNode> transitionNodes = getTransitionNode(document);
        List<InputEdge> inputEdges = getInputEdge(document);
        List<OutputEdge> onputEdges = getOutputEdge(document);
        PetriNet petriNet = new PetriNet(place,transitionNodes,inputEdges,outputEdges);
        return petriNet;
    }

//--------------------------------------------------------------------------------
//                读取XML中的PLACE信息
//--------------------------------------------------------------------------------
        public static List<Place> getPlace(Document document) throws Exception{
            NodeList placeList = document.getElementsByTagName("Place");
            places = new ArrayList<Place>();                                        //遍历place

            for(int i=0;i<placeList.getLength();i++){
                Place place = new Place();                                           //获取第i个Place结点
                org.w3c.dom.Node node = placeList.item(i);                           //获取第i个book的所有属性
                NamedNodeMap namedNodeMap = node.getAttributes();                    //获取已知名为id的属性值
                String ID = namedNodeMap.getNamedItem("ID").getTextContent();       //System.out.println(id);
                place.setID(Integer.parseInt(ID));

                //获取place结点的子节点,包含了Test类型的换行
                NodeList cList = node.getChildNodes();
                //System.out.println(cList.getLength());9

                //将一个Place里面的属性加入数组
                ArrayList<String> contents = new ArrayList<>();
                for(int j=1;j<cList.getLength();j+=2){

                    org.w3c.dom.Node cNode = cList.item(j);
                    String content = cNode.getFirstChild().getTextContent();
                    contents.add(content);
                    //System.out.println(contents);
                }
                place.setLabel(contents.get(0));
                places.add(place);
            }
            return places;
        }

//--------------------------------------------------------------------------------
//                读取XML中的TransitionNode信息
//--------------------------------------------------------------------------------
    public static List<TransitionNode> getTransitionNode(Document document) throws Exception{
        NodeList transitionNodeList = document.getElementsByTagName("TransitionNode");
        transitionNodes = new ArrayList<TransitionNode>();                                        //遍历transitionNode

        for(int i=0;i<transitionNodeList.getLength();i++){
            TransitionNode transitionNode = new TransitionNode();                                           //获取第i个TN结点
            org.w3c.dom.Node node = transitionNodeList.item(i);                                 //获取第i个book的所有属性
            NamedNodeMap namedNodeMap = node.getAttributes();                                   //获取已知名为id的属性值
            String ID = namedNodeMap.getNamedItem("ID").getTextContent();       //System.out.println(id);
            transitionNode.setID(Integer.parseInt(ID));

            //获取place结点的子节点,包含了Test类型的换行
            NodeList cList = node.getChildNodes();
            //System.out.println(cList.getLength());9

            //将一个Place里面的属性加入数组
            ArrayList<String> contents = new ArrayList<>();
            for(int j=1;j<cList.getLength();j+=2){

                org.w3c.dom.Node cNode = cList.item(j);
                String content = cNode.getFirstChild().getTextContent();
                contents.add(content);
                //System.out.println(contents);
            }
            transitionNode.setLabel(contents.get(0));
            transitionNodes.add(transitionNode);
        }
        return transitionNodes;
    }

//--------------------------------------------------------------------------------
//                读取XML中的inputEDGE信息
//--------------------------------------------------------------------------------
    public static List<InputEdge> getInputEdge(Document document) throws Exception{
        NodeList INputEdgeList = document.getElementsByTagName("InputEdge");
        inputEdges = new ArrayList<InputEdge>();                                        //遍历place

        for(int i=0;i<INputEdgeList.getLength();i++){
            InputEdge inputEdge = new InputEdge();                                           //获取第i个Place结点
            org.w3c.dom.Node node = INputEdgeList.item(i);                           //获取第i个book的所有属性
            NamedNodeMap namedNodeMap = node.getAttributes();                    //获取已知名为id的属性值
            String ID = namedNodeMap.getNamedItem("ID").getTextContent();       //System.out.println(id);
            inputEdge.setID(Integer.parseInt(ID));

            //获取place结点的子节点,包含了Test类型的换行
            NodeList cList = node.getChildNodes();
            //System.out.println(cList.getLength());9

            //将一个Place里面的属性加入数组
            ArrayList<String> contents = new ArrayList<>();
            for(int j=1;j<cList.getLength();j+=2){

                org.w3c.dom.Node cNode = cList.item(j);
                String content = cNode.getFirstChild().getTextContent();
                contents.add(content);
                //System.out.println(contents);
            }
            inputEdge.setSourceID(Integer.parseInt(contents.get(0)));            //读取边的起始点ID号
            inputEdge.setDestinationID(Integer.parseInt(contents.get(1)));      //读取边的终点ID号
            inputEdges.add(inputEdge);
        }
        return inputEdges;
    }

    //--------------------------------------------------------------------------------
//                读取XML中的outputEDGE信息
//--------------------------------------------------------------------------------
    public static List<OutputEdge> getOutputEdge(Document document) throws Exception{
        NodeList outEdgeList = document.getElementsByTagName("InputEdge");
        outputEdges = new ArrayList<OutputEdge>();                                        //遍历place

        for(int i=0;i<outEdgeList.getLength();i++){
            OutputEdge outputEdge = new OutputEdge();                                           //获取第i个Place结点
            org.w3c.dom.Node node = outEdgeList.item(i);                           //获取第i个book的所有属性
            NamedNodeMap namedNodeMap = node.getAttributes();                    //获取已知名为id的属性值
            String ID = namedNodeMap.getNamedItem("ID").getTextContent();       //System.out.println(id);
            outputEdge.setID(Integer.parseInt(ID));

            //获取place结点的子节点,包含了Test类型的换行
            NodeList cList = node.getChildNodes();
            //System.out.println(cList.getLength());9

            //将一个Place里面的属性加入数组
            ArrayList<String> contents = new ArrayList<>();
            for(int j=1;j<cList.getLength();j+=2){

                org.w3c.dom.Node cNode = cList.item(j);
                String content = cNode.getFirstChild().getTextContent();
                contents.add(content);
                //System.out.println(contents);
            }
            outputEdge.setSourceID(Integer.parseInt(contents.get(0)));            //读取边的起始点ID号
            outputEdge.setDestinationID(Integer.parseInt(contents.get(1)));      //读取边的终点ID号
            outputEdges.add(outputEdge);
        }
        return outputEdges;
    }

}

