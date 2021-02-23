import managering.Commander;
import managering.CollectionManager;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Commander commander = new Commander(new CollectionManager(args[0]));
        commander.interactiveMod();

        /*XMLToObjectParser p = new XMLToObjectParser();
        String s = "<tag at1=\"1st at value\" at2=\"2nd at value\">it's tag value</tag>";
        HashMap<String, String> map = p.getAttributes(s);

        Set<String> keys = map.keySet();
        for (String key : keys){
            System.out.println("Key: " + key + ", value: " + map.get(key));
        }

        File file = new File("test.xml");
        ObjectToXMLParser objectToXMLParser = new ObjectToXMLParser();
        objectToXMLParser.writeSingleElementWithAttributes(file, "TAG", "VALUE", 0, map);*/

    }
}
