import com.ibm.wala.classLoader.Language;
import com.ibm.wala.classLoader.ShrikeBTMethod;
import com.ibm.wala.ipa.callgraph.*;
import com.ibm.wala.ipa.callgraph.impl.AllApplicationEntrypoints;
import com.ibm.wala.ipa.callgraph.impl.Util;
import com.ibm.wala.ipa.callgraph.propagation.SSAPropagationCallGraphBuilder;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.ipa.cha.ClassHierarchyFactory;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.config.AnalysisScopeReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GraphBuilder {
    
    public static void generateScope(ArrayList<String> src, ArrayList<String> test, HashMap<String ,ClassNode> classNodes, HashMap<String,MethodNode> methodNodes) throws IOException, InvalidClassFileException, ClassHierarchyException, CancelException {
        AnalysisScope scope = AnalysisScopeReader.readJavaScope("scope.txt", new File("exclusion.txt"), ClassLoader.getSystemClassLoader());
        for (String path : src) {
            File temp = new File(path);
            scope.addClassFileToScope(ClassLoaderReference.Application, temp);
        }
        for (String path : test) {
            File temp = new File(path);
            scope.addClassFileToScope(ClassLoaderReference.Application, temp);
        }
        ClassHierarchy cha = ClassHierarchyFactory.makeWithRoot(scope);
        AllApplicationEntrypoints entryPoints = new AllApplicationEntrypoints(scope, cha);
        AnalysisOptions option = new AnalysisOptions(scope, entryPoints);
        SSAPropagationCallGraphBuilder builder = Util.makeZeroCFABuilder(Language.JAVA, option, new AnalysisCacheImpl(), cha, scope);
        CallGraph callGraph = builder.makeCallGraph(option);
        for (CGNode node : callGraph) {
            if (node.getMethod() instanceof ShrikeBTMethod) {
            // node.getMethod()返回一个比较泛化的IMethod实例，不能获取到我们想要的信息
                // 一般地，本项目中所有和业务逻辑相关的方法都是ShrikeBTMethod对象
                ShrikeBTMethod method = (ShrikeBTMethod) node.getMethod();
                // 使用Primordial类加载器加载的类都属于Java原生类，我们一般不关心。
                if ("Application".equals(method.getDeclaringClass().getClassLoader().toString())) {
                    // 获取声明该方法的类的内部表示
                    String classInnerName = method.getDeclaringClass().getName().toString();
                    // 获取方法签名
                    String signature = method.getSignature();
                    String totalName = classInnerName+" "+signature;
                    ClassNode fatherClass = classNodes.getOrDefault(classInnerName,new ClassNode(classInnerName));
                    MethodNode fatherMethod = methodNodes.getOrDefault(totalName,new MethodNode(classInnerName,signature));
                    fatherClass.Methods.put(totalName,fatherMethod);
//                    System.out.println(classInnerName+" "+signature);
//                    System.out.println("相关类");
                    Iterator<CGNode> nodes = callGraph.getPredNodes(node);
                    if (nodes != null) {
                        while (nodes.hasNext()) {
                            CGNode temp = nodes.next();
                            if (temp.getMethod() instanceof ShrikeBTMethod) {
                                ShrikeBTMethod m = (ShrikeBTMethod) temp.getMethod();
                                if ("Application".equals(m.getDeclaringClass().getClassLoader().toString())) {
                                    String sonClassInnerName = m.getDeclaringClass().getName().toString();
                                    String sonSignature = m.getSignature();
                                    String sonTotalName = sonClassInnerName+" "+sonSignature;
//                                    System.out.println(sonClassInnerName+" "+sonSignature);

                                    ClassNode sonClass = classNodes.getOrDefault(sonClassInnerName,new ClassNode(sonClassInnerName));
                                    MethodNode sonMethod = methodNodes.getOrDefault(sonTotalName,new MethodNode(sonClassInnerName,sonSignature));

                                    fatherClass.Class.put(sonClassInnerName,sonClass);
                                    fatherClass.Methods.put(sonTotalName,sonMethod);
                                    fatherMethod.Methods.put(sonTotalName,sonMethod);

                                    classNodes.put(sonClassInnerName,sonClass);
                                    methodNodes.put(sonTotalName,sonMethod);
                                }
                            }
                        }
                        classNodes.put(classInnerName,fatherClass);
                        methodNodes.put(totalName,fatherMethod);
                    }
                }
            }
        }

    }
}
