import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.util.CancelException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class SelectorTest {
    String[][]Arguments = new String[][]{
            {
                    "-m",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/0-CMD/target",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/0-CMD/data/change_info.txt"
            },
            {
                    "-c",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/0-CMD/target",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/0-CMD/data/change_info.txt"
            },
            {
                    "-m",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/1-ALU/target",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/1-ALU/data/change_info.txt"
            },
            {
                    "-c",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/1-ALU/target",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/1-ALU/data/change_info.txt"
            },
            {
                    "-m",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/2-DataLog/target",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/2-DataLog/data/change_info.txt"
            },
            {
                    "-c",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/2-DataLog/target",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/2-DataLog/data/change_info.txt"
            },
            {
                    "-m",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/3-BinaryHeap/target",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/3-BinaryHeap/data/change_info.txt"
            },
            {
                    "-c",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/3-BinaryHeap/target",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/3-BinaryHeap/data/change_info.txt"
            },
            {
                    "-m",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/4-NextDay/target",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/4-NextDay/data/change_info.txt"
            },
            {
                    "-c",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/4-NextDay/target",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/4-NextDay/data/change_info.txt"
            },
            {
                    "-m",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/5-MoreTriangle/target",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/5-MoreTriangle/data/change_info.txt"
            },
            {
                    "-c",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/5-MoreTriangle/target",
                    "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/5-MoreTriangle/data/change_info.txt"
            },
    };
    String[] checkFile = new String[]{
            "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/0-CMD/data/selection-method.txt",
            "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/0-CMD/data/selection-class.txt",

            "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/1-ALU/data/selection-method.txt",
            "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/1-ALU/data/selection-class.txt",

            "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/2-DataLog/data/selection-method.txt",
            "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/2-DataLog/data/selection-class.txt",

            "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/3-BinaryHeap/data/selection-method.txt",
            "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/3-BinaryHeap/data/selection-class.txt",

            "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/4-NextDay/data/selection-method.txt",
            "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/4-NextDay/data/selection-class.txt",

            "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/5-MoreTriangle/data/selection-method.txt",
            "C:/Users/12463/IdeaProjects/AutomatedTesting2020/Data/5-MoreTriangle/data/selection-class.txt",

    };
    public void helper(String path, HashSet<String> result) throws IOException {
        boolean method = false;
        if(path.contains("selection-method")){
            method = true;
        }
        ArrayList<String> str = new ArrayList<>();
        ArrayList<String> gap = new ArrayList<>();
        FileUnit.readChange(path,str);
        for(String s : str){
            if(s.length()!=0) {
                if (!result.contains(s)) {
                    gap.add(s);
                } else {
                    result.remove(s);
                }
            }
        }
        if(gap.size()!=0){
            System.out.println("缺少");
            for(String s: gap){
                System.out.println(s);
            }
            Assert.fail();
        }
        if(result.size()!=0){
            System.out.println(method?"测试方法":"测试类");
            System.out.println(result);
        }
        Assert.assertEquals(0,result.size());
    }
    @Test
    public void TestCMD() throws CancelException, ClassHierarchyException, InvalidClassFileException, IOException {
        HashSet<String> r = new HashSet<>();
        r = testSelection.cal(Arguments[0]);
        helper(checkFile[0],r);
        r = testSelection.cal(Arguments[1]);
        helper(checkFile[1],r);
    }
    @Test
    public void TestALU() throws CancelException, ClassHierarchyException, InvalidClassFileException, IOException {
        HashSet<String> r = new HashSet<>();
        r = testSelection.cal(Arguments[2]);
        helper(checkFile[2],r);

        r = testSelection.cal(Arguments[3]);
        helper(checkFile[3],r);
    }
    @Test
    public void TestDataLog() throws CancelException, ClassHierarchyException, InvalidClassFileException, IOException {
        HashSet<String> r = new HashSet<>();
        r = testSelection.cal(Arguments[4]);
        helper(checkFile[4],r);
        r = testSelection.cal(Arguments[5]);
        helper(checkFile[5],r);
    }
    @Test
    public void TestBinaryHeap() throws CancelException, ClassHierarchyException, InvalidClassFileException, IOException {
        HashSet<String> r = new HashSet<>();
        r = testSelection.cal(Arguments[6]);
        helper(checkFile[6],r);
        r = testSelection.cal(Arguments[7]);
        helper(checkFile[7],r);
    }
    @Test
    public void TestNextDay() throws CancelException, ClassHierarchyException, InvalidClassFileException, IOException {
        HashSet<String> r = new HashSet<>();
        r = testSelection.cal(Arguments[8]);
        helper(checkFile[8],r);
        r = testSelection.cal(Arguments[9]);
        helper(checkFile[9],r);
    }
    @Test
    public void TestMoreTriangle() throws CancelException, ClassHierarchyException, InvalidClassFileException, IOException {
        HashSet<String> r = new HashSet<>();
        r = testSelection.cal(Arguments[10]);
        helper(checkFile[10],r);
        r = testSelection.cal(Arguments[11]);
        helper(checkFile[11],r);
    }
}
//
//缺少
//        Lnet/mooctest/DatalogTest net.mooctest.DatalogTest.getArguments()V
//        Lnet/mooctest/DatalogTest5 net.mooctest.DatalogTest5.blowTestValue()V
//        Lnet/mooctest/DatalogTest net.mooctest.DatalogTest.blowConstructor2()V
//        Lnet/mooctest/DatalogTest net.mooctest.DatalogTest.getPredicate()V
//        Lnet/mooctest/DatalogTest net.mooctest.DatalogTest.blowConstructor1()V
//        Lnet/mooctest/DatalogOtherTest net.mooctest.DatalogOtherTest.testVariable3()V
//        Lnet/mooctest/DatalogOtherTest net.mooctest.DatalogOtherTest.testValue()V