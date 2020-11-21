import java.util.HashMap;
import java.util.Map;

public class GenerateDot {
    public static void generateDot(String name,HashMap<String,ClassNode> classNodeHashMap,HashMap<String,MethodNode> methodNodeHashMap){
        StringBuilder result;
        result = new StringBuilder();
        result.append("digraph cmd_method {\n");
        for(Map.Entry<String,ClassNode> entry: classNodeHashMap.entrySet()){
            ClassNode node = entry.getValue();
            for(String str:node.Class.keySet()){
                result.append("\t\"").append(node.classInnerName).append("\" -> \"").append(str).append("\"\n");
            }
        }
        result.append("}");
        FileUnit.writeFile("../Report/"+name+"-class.dot",result.toString());
        result = new StringBuilder();
        result.append("digraph cmd_method {\n");
        for(Map.Entry<String,MethodNode> entry: methodNodeHashMap.entrySet()){
            MethodNode node = entry.getValue();
            for(String str:node.Methods.keySet()){
                result.append("\t\"").append(node.signature).append("\" -> \"").append(str).append("\"\n");
            }
        }
        result.append("}");
        FileUnit.writeFile("../Report/"+name+"-method.dot",result.toString());
    }

}
