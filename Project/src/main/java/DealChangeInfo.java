import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DealChangeInfo {
    public static HashSet<String> dealChangeInfo(String path, boolean type, HashMap<String,ClassNode> classNodeHashMap,HashMap<String,MethodNode>methodNodeHashMap,HashMap<String,ClassNode> testClassNodeHashMap,HashMap<String,MethodNode>testMethodNodeHashMap) throws IOException {
        ArrayList<String> info = new ArrayList<>();
        FileUnit.readChange(path,info);
//        for(String str:info){
//            System.out.println(str);
//        }
        HashSet<String> storeMethod = new HashSet<>();
        HashMap<String,ClassNode> storeClass = new HashMap<>();
        HashSet<String> check = new HashSet<>();

            for(String str:info){
                if(type) {
                    ClassNode node = classNodeHashMap.getOrDefault(str.split(" ")[0], null);
                    if (node != null) {
                        dfsClass(storeClass,node);//找出所有相关的class节点
                        for(Map.Entry<String,ClassNode> entryOut:storeClass.entrySet()) {
                            for (Map.Entry<String, MethodNode> entry : entryOut.getValue().Methods.entrySet()) {
                                dfsMethod(check, storeMethod, entry.getValue(), testMethodNodeHashMap);//读取所有class节点的method
                            }
                        }
                    }
                }
                else{
                    MethodNode node = methodNodeHashMap.getOrDefault(str,null);
                    if(node != null){
                        for(Map.Entry<String, MethodNode> entry : node.Methods.entrySet()){
                            dfsMethod(check,storeMethod,entry.getValue(),testMethodNodeHashMap);
                        }
                    }
                }
            }

            StringBuilder result = new StringBuilder();
            for(String str:storeMethod){
                result.append(str+"\n");
            }
            FileUnit.writeFile(type?"selection-class.txt":"selection-method.txt",result.toString());
        return storeMethod;
    }
    //深搜所有相关的class
    public static void dfsClass(HashMap<String,ClassNode> storeClass,ClassNode classNode){
        if(storeClass.containsKey(classNode.classInnerName)){
            return;
        }
        else{
            storeClass.put(classNode.classInnerName,classNode);
            for(Map.Entry<String,ClassNode> entry : classNode.Class.entrySet()){
                dfsClass(storeClass,entry.getValue());
            }
        }
    }
    //深搜所有相关的method
    public static void dfsMethod(HashSet<String> check,HashSet<String> store, MethodNode methodNode, HashMap<String, MethodNode> testMethodNodeHashMap){
        if(check.contains(methodNode.totalMethodName)){
            return;
        }
        else{
            check.add(methodNode.totalMethodName);
        }

        if(testMethodNodeHashMap.containsKey(methodNode.totalMethodName)  && !(methodNode.totalMethodName.contains("<init>()V")||methodNode.totalMethodName.contains("initialize()V"))){
            store.add(methodNode.totalMethodName);
        }
        for(Map.Entry<String,MethodNode>entry : methodNode.Methods.entrySet()){
            dfsMethod(check,store,entry.getValue(),testMethodNodeHashMap);
        }
    }
}
