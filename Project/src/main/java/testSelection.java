import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.util.CancelException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class testSelection {


    public static void main(String[] args) throws IOException, InvalidClassFileException, ClassHierarchyException, CancelException {
        cal(args);
    }
    public static HashSet<String> cal(String[] args) throws CancelException, ClassHierarchyException, InvalidClassFileException, IOException {
        ArrayList<String> srcFile = new ArrayList<String>();
        ArrayList<String> testFile = new ArrayList<String>();
        HashMap<String,ClassNode> classNodes = new HashMap<String, ClassNode>();
        HashMap<String,MethodNode> methodNodes = new HashMap<String,MethodNode>();
        HashMap<String,ClassNode> testClassNodes = new HashMap<String, ClassNode>();
        HashMap<String,MethodNode> testMethodNodes = new HashMap<String,MethodNode>();

        FileUnit.readFile(args[1],srcFile,testFile);
        GraphBuilder.generateScope(srcFile,testFile,classNodes,methodNodes);
        GraphBuilder.generateScope(new ArrayList<>(),testFile,testClassNodes,testMethodNodes);
        GenerateDot.generateDot("graph",classNodes,methodNodes);
        return DealChangeInfo.dealChangeInfo(args[2], "-c".equals(args[0]),classNodes,methodNodes,testClassNodes,testMethodNodes);
    }
}
