import java.util.HashMap;

public class ClassNode {
    String classInnerName;
    HashMap<String,MethodNode> Methods;
    HashMap<String,ClassNode> Class;
    public ClassNode(String classInnerName){
        this.classInnerName = classInnerName;
        Class = new HashMap<String, ClassNode>();
        Methods = new HashMap<String, MethodNode>();
    }
}
