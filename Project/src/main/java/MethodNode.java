import java.util.HashMap;

public class MethodNode {
    String classInnerName;
    String signature;
    String totalMethodName;
    HashMap<String,MethodNode> Methods;
    HashMap<String,ClassNode> Class;
    public MethodNode(String classInnerName,String signature){
        this.signature = signature;
        this.classInnerName = classInnerName;
        totalMethodName = classInnerName + " " + signature;
        Methods = new HashMap<String, MethodNode>();
        Class = new HashMap<String, ClassNode>();

    }
}
