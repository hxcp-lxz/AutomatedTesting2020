import java.io.*;
import java.util.ArrayList;

public class FileUnit {
    /**
     * 读取f下所有class文件的路径
     * @param f
     * @param files
     */
    static void dfs(File f,ArrayList<String> files){
        if(f.exists()){
            File[] fileList = f.listFiles();
            if(fileList!=null){
                for(File file: fileList){
                    if(file.isDirectory()){
                        dfs(file,files);
                    }
                    else{
                        if(file.getName().contains(".class")){
                            files.add(file.getPath());
                        }
                    }
                }
            }
            else{
                System.out.println(f.getPath()+"为空目录");
            }
        }
        else{
            System.out.println(f.getPath()+"路径不存在");
        }

    }

    /**
     * 读取path中的源文件与测试文件
     * @param path
     * @param srcClass
     * @param testClass
     */
    public static void readFile(String path,ArrayList<String> srcClass,ArrayList<String> testClass){
        File f = new File(path);
        if(f.exists()){
            File[] pathList = f.listFiles();
            if(pathList!=null) {
                for (File file : pathList) {
                    if (file.getName().equals("classes")) {
                        dfs(file, srcClass);
                    }
                    if(file.getName().equals("test-classes")){
                        dfs(file,testClass);
                    }
                }
            }
            else {
                System.out.println("空文件");
            }
        }
        else{
            System.out.println("target文件不存在");
        }
    }

    /**
     * 读取changeInfo
     * @param path
     * @param info
     * @throws IOException
     */
    public static void readChange(String path,ArrayList<String> info) throws IOException {
        BufferedReader fileReader;
        String changeInfo;
        fileReader = new BufferedReader(new FileReader(path));
        while ((changeInfo = fileReader.readLine()) != null) {
            info.add(changeInfo);
        }
    }

    /**
     * 用于写入dot文件t、xt文件
     * @param path
     * @param content
     */
    public static void writeFile(String path,String content){
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
