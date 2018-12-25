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
    static{
        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            db = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

        public static List<Place> getPlace(String fileName) throws Exception{       //将给定URI的内容解析为一个XML文档,并返回Document对象
            document = db.parse(fileName);                                          //按文档顺序返回包含在文档中且具有给定标记名称的所有 Element 的 NodeList
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
    public static void main(String args[]){
        String fileName ="src/res/books.xml";
        try {
            List<Place> list = xmlDoc.getPlace(fileName);
            for(Place place :list){
                System.out.println(place);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
        }

}

