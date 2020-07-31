package com.mezo.chaos.nyx.aliyun.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileUtil {
    /**
     * 获取windows/linux的项目根目录
     *
     * @return
     */
    public static String getConTextPath() {
        String fileUrl = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        if ("usr".equals(fileUrl.substring(1, 4))) {
            //linux
            fileUrl = (fileUrl.substring(0, fileUrl.length() - 16));
        } else {
            //windows
            fileUrl = (fileUrl.substring(1, fileUrl.length() - 16));
        }
        return fileUrl;
    }

    /**
     * 字符串转数组
     *
     * @param str      字符串
     * @param splitStr 分隔符
     * @return
     */
    public static String[] stringToArray(String str, String splitStr) {
        String[] arrayStr = null;
        if (!"".equals(str) && str != null) {
            if (str.indexOf(splitStr) != -1) {
                arrayStr = str.split(splitStr);
            } else {
                arrayStr = new String[1];
                arrayStr[0] = str;
            }
        }
        return arrayStr;
    }

    /**
     * 读取文件
     *
     * @param Path
     * @return
     */
    public static String readFile(String Path) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    /**
     * 获取文件夹下所有文件的名称 + 模糊查询（当不需要模糊查询时，queryStr传空或null即可）
     * 1.当路径不存在时，map返回retType值为1
     * 2.当路径为文件路径时，map返回retType值为2，文件名fileName值为文件名
     * 3.当路径下有文件夹时，map返回retType值为3，文件名列表fileNameList，文件夹名列表folderNameList
     *
     * @param folderPath 路径
     * @param queryStr   模糊查询字符串
     * @return
     */
    public static HashMap<String, Object> getFilesName(String folderPath, String queryStr) {
        HashMap<String, Object> map = new HashMap<>();
        //文件名列表
        List<String> fileNameList = new ArrayList<>();
        //文件夹名列表
        List<String> folderNameList = new ArrayList<>();
        File f = new File(folderPath);
        //路径不存在
        if (!f.exists()) {
            map.put("retType", "1");
        } else {
            boolean flag = f.isDirectory();
            //路径为文件
            if (flag == false) {
                map.put("retType", "2");
                map.put("fileName", f.getName());
            } else { //路径为文件夹
                map.put("retType", "3");
                File[] fa = f.listFiles();
                //若queryStr传入为null,则替换为空（indexOf匹配值不能为null）
                queryStr = queryStr == null ? "" : queryStr;
                for (int i = 0; i < fa.length; i++) {
                    File fs = fa[i];
                    if (fs.getName().indexOf(queryStr) != -1) {
                        if (fs.isDirectory()) {
                            folderNameList.add(fs.getName());
                        } else {
                            fileNameList.add(fs.getName());
                        }
                    }
                }
                map.put("fileNameList", fileNameList);
                map.put("folderNameList", folderNameList);
            }
        }
        return map;
    }

    /**
     * 以行为单位读取文件，读取到最后一行
     *
     * @param filePath
     * @return
     */
    public static List<String> readFileContent(String filePath) {
        BufferedReader reader = null;
        List<String> listContent = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                listContent.add(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return listContent;
    }

    /**
     * 读取指定行数据 ，注意：0为开始行
     *
     * @param filePath
     * @param lineNumber
     * @return
     */
    public static String readLineContent(String filePath, int lineNumber) {
        BufferedReader reader = null;
        String lineContent = "";
        try {
            reader = new BufferedReader(new FileReader(filePath));
            int line = 0;
            while (line <= lineNumber) {
                lineContent = reader.readLine();
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return lineContent;
    }

    /**
     * 读取从beginLine到endLine数据（包含beginLine和endLine），注意：0为开始行
     *
     * @param filePath
     * @param beginLineNumber 开始行
     * @param endLineNumber   结束行
     * @return
     */
    public static List<String> readLinesContent(String filePath, int beginLineNumber, int endLineNumber) {
        List<String> listContent = new ArrayList<>();
        try {
            int count = 0;
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String content = reader.readLine();
            while (content != null) {
                if (count >= beginLineNumber && count <= endLineNumber) {
                    listContent.add(content);
                }
                content = reader.readLine();
                count++;
            }
        } catch (Exception e) {
        }
        return listContent;
    }

    /**
     * 读取若干文件中所有数据
     *
     * @param listFilePath
     * @return
     */
    public static List<String> readFileContent_list(List<String> listFilePath) {
        List<String> listContent = new ArrayList<>();
        for (String filePath : listFilePath) {
            File file = new File(filePath);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                int line = 1;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    listContent.add(tempString);
                    line++;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }
        }
        return listContent;
    }

    /**
     * 文件数据写入（如果文件夹和文件不存在，则先创建，再写入）
     *
     * @param filePath
     * @param content
     * @param flag     true:如果文件存在且存在内容，则内容换行追加；false:如果文件存在且存在内容，则内容替换
     */
    public static String fileLinesWrite(String filePath, String content, boolean flag) {
        String filedo = "write";
        FileWriter fw = null;
        try {
            File file = new File(filePath);
            //如果文件夹不存在，则创建文件夹
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                //如果文件不存在，则创建文件,写入第一行内容
                file.createNewFile();
                fw = new FileWriter(file);
                filedo = "create";
            } else {
                //如果文件存在,则追加或替换内容
                fw = new FileWriter(file, flag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(content);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filedo;
    }

    /**
     * 写文件
     *
     * @param ins
     * @param out
     */
    public static void writeIntoOut(InputStream ins, OutputStream out) {
        byte[] bb = new byte[10 * 1024];
        try {
            int cnt = ins.read(bb);
            while (cnt > 0) {
                out.write(bb, 0, cnt);
                cnt = ins.read(bb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                ins.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断list中元素是否完全相同（完全相同返回true,否则返回false）
     *
     * @param list
     * @return
     */
    private static boolean hasSame(List<? extends Object> list) {
        if (null == list) {
            return false;
        }
        return 1 == new HashSet<Object>(list).size();
    }

    /**
     * 判断list中是否有重复元素（无重复返回true,否则返回false）
     *
     * @param list
     * @return
     */
    private static boolean hasSame2(List<? extends Object> list) {
        if (null == list) {
            return false;
        }
        return list.size() == new HashSet<Object>(list).size();
    }
    /**
     * 递归删除文件或者目录
     *
     * @param file_path
     */
    public static void deleteEveryThing(String file_path) {
        try {
            File file = new File(file_path);
            if (!file.exists()) {
                return;
            }
            if (file.isFile()) {
                file.delete();
            } else {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    //得到子文件或文件夹的绝对路径
                    String root = files[i].getAbsolutePath();
                    deleteEveryThing(root);
                }
                file.delete();
            }
        } catch (Exception e) {
            System.out.println("删除文件失败");
        }
    }

    /**
     * 创建目录
     *
     * @param dir_path
     */
    public static void mkDir(String dir_path) {
        File myFolderPath = new File(dir_path);
        try {
            if (!myFolderPath.exists()) {
                myFolderPath.mkdir();
            }
        } catch (Exception e) {
            System.out.println("新建目录操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 判断指定的文件是否存在。
     *
     * @param fileName
     * @return
     */
    public static boolean isFileExist(String fileName) {
        return new File(fileName).isFile();
    }

    /** 得到文件后缀名
     *
     * @param fileName
     * @return
     */
    public static String getFileExt(String fileName) {
        int point = fileName.lastIndexOf('.');
        int length = fileName.length();
        if (point == -1 || point == length - 1) {
            return "";
        } else {
            return fileName.substring(point + 1, length);
        }
    }

    /**
     * 删除文件夹及其下面的子文件夹
     *
     * @param dir
     * @throws IOException
     */
    public static void deleteDirs(File dir) throws IOException {
        if (dir.isFile()) {
            throw new IOException("IOException -> BadInputException: not a directory.");
        }
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isFile()) {
                    file.delete();
                } else {
                    deleteDirs(file);
                }
            }
        }
        dir.delete();
    }

    /**
     * 复制文件
     *
     * @param src
     * @param dst
     * @throws Exception
     */
    public static void copy(File src, File dst) throws Exception {
        int BUFFER_SIZE = 4096;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out = null;
            }
        }
    }

    /* 只清空文件夹，不删除文件夹 */
    public static synchronized void clearDir(String dir_path) throws FileNotFoundException {
        File file = new File(dir_path);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        if (file.isDirectory()) {
            File[] fe = file.listFiles();
            for (int i = 0; i < fe.length; i++) {
                deleteFiles(fe[i].toString());
                //删除已经是空的子目录
                fe[i].delete();
            }
        }
    }

    /* 清空文件夹，并删除文件夹 */
    public static synchronized void deleteDir(String dir_path) throws IOException {
        deleteDir(new File(dir_path));
    }
    /* 清空文件夹，并删除文件夹 */
    public static synchronized void deleteDir(File f) throws IOException {
        //文件夹不存在不存在
        if (!f.exists()) {
            throw new IOException("指定目录不存在:" + f.getName());
        }
        //保存中间结果
        boolean rslt = true;
        //先尝试直接删除
        if (!(rslt = f.delete())) {
            //若文件夹非空。枚举、递归删除里面内容
            File[] subs = f.listFiles();
            for (int i = 0; i <= subs.length - 1; i++) {
                if (subs[i].isDirectory()) {
                    //递归删除子文件夹内容
                    deleteDir(subs[i]);
                }
                //删除子文件夹本身
                subs[i].delete();
            }
            //删除此文件夹本身
            f.delete();
        }
    }

    //路径中的多层目录,如果不存在,则建立(mkdir－只可建最后一层目录)
    public static synchronized void makeDir(String dirPath)
            throws FileNotFoundException {
        String s = "";
        //replace tab key
        dirPath = dirPath.replaceAll("\\t", "/t");
        dirPath = dirPath.replaceAll("\\\\", "/");
        String[] aPath = dirPath.split("/");
        for (int i = 0; i < aPath.length; i++) {
            s = s + aPath[i] + "/";
            //System.out.println(s);
            File d = new File(s);
            if (!d.exists()) {
                d.mkdir();
            }
        }
    }

    //修改目录名称或文件名称 dir and file
    public static synchronized void rename(String sOld, String sNew)
            throws FileNotFoundException {
        boolean b = false;
        File d = new File(sOld);
        if (d.exists()) {
            b = d.renameTo(new File(sNew));
        }
    }

    public static synchronized String formulaDirName(String dirName) {
        dirName = dirName.replaceAll("/", "\\\\");
        return dirName;
    }

    public static synchronized String formulaPath(String dirName) {
        dirName = dirName.replaceAll("\\\\", "/");
        return dirName;
    }

    public static synchronized String lastDir(String dir_path) {
        if (dir_path.trim().compareTo("") == 0) {
            return "";
        } else {
            //两个位置，谁后取谁。因为路径中常包含这两种标识
            int i = dir_path.lastIndexOf("\\") > dir_path.lastIndexOf("/") ? dir_path.lastIndexOf("\\") : dir_path.lastIndexOf("/");
            if (i > 0) {
                return dir_path.substring(i);
            } else {
                return "";
            }
        }
    }

    //删除给定的文件
    public static void deleteFile(String FileName) {
        File f2 = new File(FileName);
        f2.delete(); //del file
    }

    /*
     *删除目录下的所有文件
     **/
    public static boolean deleteFiles(String dir) {
        if (dir == null || "".equals(dir)) {
            return true;
        }
        File f0 = new File(dir);
        if (!f0.isDirectory()) {
            return false;
        }
        File[] files = f0.listFiles();
        boolean status = true;
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (!f.isFile()) {
                continue;
            }
            boolean b = f.delete();
            status = (status && b);
        }
        return status;
    }

    /**
     * Deletes each file in <tt>files</tt> which is under <tt>path</tt>.
     * It does not delete directory.
     *
     * @param path
     * @param files
     * @return <tt>true</tt> if and only if all the files are successfully
     * deleted; <tt>false</tt> otherwise.
     */
    public static boolean deleteFiles(String path, String[] files) {
        if (path == null || files == null)
            return true;

        boolean status = true;
        for (int i = 0; i < files.length; i++) {
            File f = new File(path, files[i]);
            if (!f.isFile()) {
                continue;
            }
            //?  (f.getAbsoluteFile()).
            boolean b = f.delete();
            status = (status && b);
        }
        return status;
    }

    public static boolean deleteFiles(List files) {

        if (files == null || files.size() <= 0)
            return true;

        String fileName = "";
        boolean status = true;
        for (int i = 0; i < files.size(); i++) {
            fileName = (String) files.get(i);
            File f = new File(fileName);
            if (!f.isFile()) {
                continue;
            }
            //?  (f.getAbsoluteFile()).
            boolean b = f.delete();
            status = (status && b);
        }
        return status;
    }

    /**
     * Copies byte-content of <tt>f</tt> to <tt>os</tt>.
     *
     * @param f
     * @param os
     * @throws IOException
     */
    public static void fileToOutputStream(File f, OutputStream os)
            throws IOException {
        //
        InputStream is = new BufferedInputStream(new FileInputStream(f));
        byte[] barr = new byte[1024];
        int count;
        while (true) {
            count = is.read(barr);
            if (count == -1){
                break;
            }
            os.write(barr, 0, count);
        }
        is.close();
        return;
    }

    private String getNameWithoutExtension(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public boolean isImgFile(String file) {
        if (StringUtils.isNotEmpty(file)) {
            String s1 = "." + getFileExt(file);
            return ".jpg.jpeg.bmp.gif.png".indexOf(s1) > -1;
        }
        return false;
    }

    public String getFileName(String s) {
        try {
            s = s.replaceAll("/", "\\\\");
            int fileIndex = s.lastIndexOf("\\") + 1;
            return s.substring(fileIndex);
        } catch (Exception e) {
            return "";
        }
    }

    public String getFilePath(String s) {
        try {
            s = s.replaceAll("/", "\\\\");
            int fileIndex = s.lastIndexOf("\\");
            return s.substring(0, fileIndex);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 目录下已经有同名文件,则文件重命名,增加文件序号
     * @param sPath
     * @param sFile
     * @return
     */
    public String newFile(String sPath, String sFile) {
        String newFileName = "";
        String withoutExt = "";
        File curFile = new File(sPath + "\\" + sFile);
        if (curFile.exists()) {
            for (int counter = 1; curFile.exists(); counter++) {
                withoutExt = this.getNameWithoutExtension(curFile.getName());
                if (withoutExt.endsWith(counter - 1 + ")")) {
                    withoutExt = withoutExt.substring(0, withoutExt.indexOf("("));        //idea
                }
                newFileName = withoutExt + "(" + counter + ")" + "." + getFileExt(curFile.getName());
                curFile = new File(sPath + "\\" + newFileName);
            }
        } else {
            newFileName = curFile.getName();
        }
        return newFileName;
    }

    /**
     * 读日志文件 "c:\\Log.txt"  输入参数：sFile = Path + FileName 文件路径＋文件名称
     * @param sFile
     * @return
     */
    public List<String> readTxtFile(String sFile) {
        String str = "";
        List<String> sList = new ArrayList<String>();
        try {
            FileReader fr = new FileReader(sFile);
            BufferedReader bfr = new BufferedReader(fr);
            while ((str = bfr.readLine()) != null) {
                sList.add(str);
            }
            fr.close();
        } catch (IOException ex) {
            System.out.println("readTxtFile IOException Error." + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("readTxtFile Exception Error." + ex.getMessage());
        }
        return sList;
    }

    public String writeTxt(String sPath, String sFile, String sContent) {
        String s = "";
        //建立代表Sub目录的File对象，并得到它的一个引用
        File d = new File(sPath);
        //检查Sub目录是否存在
        if (!d.exists()) {
            //建立Sub目录
            d.mkdir();
        }

        try {
            FileWriter fw = new FileWriter(sPath + "\\" + sFile, true);
            BufferedWriter bfw = new BufferedWriter(fw);
            bfw.write(sContent);
            bfw.flush();
            fw.close();
        } catch (IOException ex) {
            s = "WriteTxt IOException Error.";
        } catch (Exception ex) {
            s = "WriteTxt Exception Error.";
        }
        return s;
    }

    /**
     * 创建新文本文件，如果文件已经存在则覆盖
     * @param sPathFile
     * @param sContent
     * @return
     * @throws FileNotFoundException
     */
    public String createTxt(String sPathFile, String sContent) throws FileNotFoundException {
        String s = "";
        String sPath = this.getFilePath(sPathFile);
        String sFile = this.getFileName(sPathFile);
        //建立代表Sub目录的File对象，并得到它的一个引用
        File d = new File(sPath);
        //检查Sub目录是否存在
        if (!d.exists()) {
            //建立Sub目录
            makeDir(sPath);
        }

        try {
            FileWriter fw = new FileWriter(sPath + "\\" + sFile, false);
            BufferedWriter bfw = new BufferedWriter(fw);
            bfw.write(sContent);
            bfw.flush();
            fw.close();
        } catch (IOException ex) {
            s = "createTxt IOException Error.";
        } catch (Exception ex) {
            s = "createTxt Exception Error.";
        }

        return s;
    }

    /**
     * 创建新文本文件，如果文件已经存在则覆盖
     * @param sPath
     * @param sFile
     * @param sContent
     * @return
     * @throws FileNotFoundException
     */
    public String createTxt(String sPath, String sFile, String sContent) throws FileNotFoundException {
        String s = "";
        //建立代表Sub目录的File对象，并得到它的一个引用
        File d = new File(sPath);
        //检查Sub目录是否存在
        if (!d.exists()) {
            //建立Sub目录
            makeDir(sPath);
        }
        try {
            FileWriter fw = new FileWriter(sPath + "\\" + sFile, false);
            BufferedWriter bfw = new BufferedWriter(fw);
            bfw.write(sContent);
            bfw.flush();
            fw.close();
        } catch (IOException ex) {
            s = "createTxt IOException Error.";
        } catch (Exception ex) {
            s = "createTxt Exception Error.";
        }
        return s;
    }

    /**
     * 创建新文本文件，如果文件已经存在则覆盖，在文件后追加内容 文件格式：encode:UTF-8
     * @param sPath
     * @param sFile
     * @param sContent
     * @param enCoding
     * @return
     * @throws FileNotFoundException
     */
    public String createTxt(String sPath, String sFile, String sContent, String enCoding) throws FileNotFoundException {
        String s = "";
        //建立代表Sub目录的File对象，并得到它的一个引用
        File d = new File(sPath);
        //检查Sub目录是否存在
        if (!d.exists()) {
            //建立Sub目录
            makeDir(sPath);
        }
        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(sPath + "\\" + sFile), enCoding);
            out.write(sContent);
            out.flush();
            out.close();
        } catch (IOException ex) {
            s = "createTxt IOException Error.";
        } catch (Exception ex) {
            s = "createTxt Exception Error.";
        }

        return s;
    }

    /**
     * 创建新文本文件，如果文件已经存在则覆盖，只覆盖不追加 文件格式：encode:UTF-8
     *
     * @param sPath
     * @param sFile
     * @param sContent
     * @param enCoding
     * @return
     * @throws FileNotFoundException
     */
    public String newTxt(String sPath, String sFile, String sContent, String enCoding) throws FileNotFoundException {
        String s = "";
        //建立代表Sub目录的File对象，并得到它的一个引用
        File d = new File(sPath);
        //检查Sub目录是否存在
        if (!d.exists()) {
            //建立Sub目录
            makeDir(sPath);
        }
        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(sPath + "\\" + sFile, false), enCoding);
            out.write(sContent);
            out.flush();
            out.close();
        } catch (IOException ex) {
            s = "createTxt IOException Error.";
        } catch (Exception ex) {
            s = "createTxt Exception Error.";
        }
        return s;
    }

    /**
     *
     * @param sFile
     * @param aTitle
     * @param aContent
     * @param sSplitFlag
     * @param iColumns
     * @return
     */
    public String writeTxt(String sFile, String[] aTitle, String[] aContent, String sSplitFlag, int iColumns) {
        String sMsg = "";;
        try {
            if (aTitle != null) {
                if (aTitle.length != iColumns) {
                    throw new Exception("Title Length is not right!");
                }
            }
            File f = new File(sFile);
            if (f.exists()) {
                f.delete();   //if exist then delete()
            }
            FileWriter fw = new FileWriter(sFile, true);
            BufferedWriter bfw = new BufferedWriter(fw);
            //write Title
            if (aTitle != null) {
                for (int i = 0; i < aTitle.length; i++) {
                    bfw.write(aTitle[i] + sSplitFlag);
                }
                bfw.newLine(); //插入换行符号
            }
            //write content
            for (int i = 0; i < aContent.length; i++) {
                bfw.write(aContent[i] + sSplitFlag);

                if ((i + 1) % iColumns == 0) {
                    bfw.newLine(); //插入换行符号
                }
            }
            bfw.flush();   //将缓冲区内的数据写入文件中
            fw.close();
        } catch (IOException ex) {
            sMsg = "WriteTxt IOException Error." + ex.getMessage();
        } catch (Exception ex) {
            sMsg = "WriteTxt Exception Error." + ex.getMessage();
        }
        return sMsg;
    }

    //边生成边写XML文件  对单表结构
    public String writeXML(String sFile, String indent, String root, String[] aTrunk, String[] aLeaf, String[] aContent) {
        int i = 0, j = 0, k = 0;
        String sIndent = "";
        String[] aTrunkSuffix = new String[aTrunk.length];
        String[] aLeafSuffix = new String[aLeaf.length];
        String sMsg = "";
        try {
            File f = new File(sFile);
            if (f.exists()) {
                f.delete();   //if exist then delete()
            }
            //inital array
            for (i = 0; i < aTrunk.length; i++) {
                for (j = 0; j < i; j++) {
                    sIndent = indent + sIndent;  //add space
                }
                aTrunk[i] = sIndent + "<" + aTrunk[i] + ">";
                aTrunkSuffix[i] = aTrunk[i].replaceFirst("<", "</");
                //System.out.println(i + " " + aTrunk[i]+aTrunkSuffix[i]);
            }
            sIndent = indent + sIndent;  //add space
            for (i = 0; i < aLeaf.length; i++) {
                aLeafSuffix[i] = "</" + aLeaf[i] + ">";
                aLeaf[i] = sIndent + "<" + aLeaf[i] + ">";
            }
            FileWriter fw = new FileWriter(sFile, true);
            BufferedWriter bfw = new BufferedWriter(fw);
            bfw.write("<?xml version=\"1.0\" ?>");
            bfw.newLine();
            if (root.length() > 0) {
                bfw.write("<" + root + ">");
                bfw.newLine();
            }
            while (k < aContent.length) {
                for (i = 0; i < aTrunk.length; i++) {
                    bfw.write(aTrunk[i]);
                    bfw.newLine();
                }
                for (i = 0; i < aLeaf.length; i++) {
                    bfw.write(aLeaf[i] + aContent[k++] + aLeafSuffix[i]);
                    bfw.newLine();
                }
                for (i = aTrunkSuffix.length - 1; i > -1; i--) {
                    bfw.write(aTrunkSuffix[i]);
                    bfw.newLine();
                }
            }//end while
            if (root.length() > 0) {
                bfw.write("</" + root + ">");
                bfw.newLine();
            }
            bfw.flush();   //将缓冲区内的数据写入文件中
            fw.close();
        } catch (IOException ex) {
            sMsg = this.getClass().getName() + " WriteXML IOException Error." + ex.getMessage();
        } catch (Exception ex) {
            sMsg = this.getClass().getName() + " WriteXML Exception Error." + ex.getMessage();
        }
        return sMsg;
    }

    //create xml lines to ArrayList
    public ArrayList createXML(String StartIndent, String indent, String[] aTrunk, String[] aLeaf, String[] aContent) {

        ArrayList aList = new ArrayList();
        String sIndent = StartIndent;
        String[] aTrunkSuffix = new String[aTrunk.length];
        String[] aLeafSuffix = new String[aLeaf.length];
        try {
            //inital array
            for (int i = 0; i < aTrunk.length; i++) {
                for (int j = 0; j < i; j++) {
                    sIndent = indent + sIndent;  //add space
                }
                aTrunk[i] = sIndent + "<" + aTrunk[i] + ">";
                aTrunkSuffix[i] = aTrunk[i].replaceFirst("<", "</");
            }
            sIndent = indent + sIndent;
            for (int i = 0; i < aLeaf.length; i++) {
                aLeafSuffix[i] = "</" + aLeaf[i] + ">";
                aLeaf[i] = sIndent + "<" + aLeaf[i] + ">";
            }
            int k = 0;
            while (k < aContent.length) {
                for (int i = 0; i < aTrunk.length; i++) {
                    aList.add(aTrunk[i]);
                }
                for (int i = 0; i < aLeaf.length; i++) {
                    aList.add(aLeaf[i] + aContent[k++] + aLeafSuffix[i]);
                }
                for (int i = aTrunkSuffix.length - 1; i > -1; i--) {
                    aList.add(aTrunkSuffix[i]);
                }
            }//end while
            return aList;
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

    //边生成边写XML文件
    public String writeXML(String sFile, String sXmlVer, String root, ArrayList aList) {
        String sMsg = "";
        long lTime = System.currentTimeMillis();
        try {
            File f = new File(sFile);
            if (f.exists()) {
                //if exist then delete()
                f.delete();
            }
            FileWriter fw = new FileWriter(sFile, true);
            BufferedWriter bfw = new BufferedWriter(fw);
            bfw.write("<?" + sXmlVer + "?>");
            bfw.newLine();
            if (root.length() > 0) {
                bfw.write("<" + root + ">");
                bfw.newLine();
            }
            //write txt
            for (int i = 0; i < aList.size(); i++) {
                bfw.write((String) aList.get(i));
                bfw.newLine();
            }
            if (root.length() > 0) {
                //去掉元素后面的属性
                bfw.write("</" + root.substring(0, root.indexOf(" ")) + ">");
            }
            //将缓冲区内的数据写入文件中
            bfw.flush();
            fw.close();
        } catch (IOException ex) {
            sMsg = this.getClass().getName() + " WriteXML IOException Error." + ex.getMessage();
        } catch (Exception ex) {
            sMsg = this.getClass().getName() + " WriteXML Exception Error." + ex.getMessage();
        }
        return sMsg;
    }

    public boolean isExist(String filename) {
        try {
            File file = new File(filename);
            return file.exists();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 功能:利用nio来快速复制文件
     */
    public void copyFile(String srcFile, String destFile)
            throws java.io.IOException {
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        FileChannel fcin = fis.getChannel();
        FileChannel fcout = fos.getChannel();
        fcin.transferTo(0, fcin.size(), fcout);
        fcin.close();
        fcout.close();
        fis.close();
        fos.close();
    }

    /**
     * 忽略拷贝文件时发生的错误，可能是文件不存在
     */
    public boolean copyFileIgnore(String file1, String file2) {
        try {
            File file_in = new File(file1);
            File file_out = new File(file2);
            FileInputStream in1 = new FileInputStream(file_in);
            FileOutputStream out1 = new FileOutputStream(file_out);
            byte[] bytes = new byte[1024];
            int c;
            while ((c = in1.read(bytes)) != -1) {
                out1.write(bytes, 0, c);
            }
            in1.close();
            out1.close();
            //if sucess then return true
            return true;
        } catch (Exception e) {
            //if fail then return false
            return false;
        }

    }

    /**
     *
     * @param dir1
     * @param dir2
     * @throws IOException
     */
    public void copyDir(String dir1, String dir2) throws IOException {
        (new File(dir2)).mkdir();
        File[] file = (new File(dir1)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].getName().compareTo("Thumbs.db") != 0) {
                if (file[i].isFile()) {
                    copyFile(dir1 + "\\" + file[i].getName(), dir2 + "\\" + file[i].getName());
                } else if (file[i].isDirectory()) {
                    copyDir(dir1 + "\\" + file[i].getName(), dir2 + "\\" + file[i].getName());
                }
            }
        }
    }

    /**
     * 功能:利用nio快速复制目录
     *
     * @param srcDirectory
     * @param destDirectory
     */
    public void copyDirectory(String srcDirectory, String destDirectory)
            throws java.io.IOException {
        // 得到目录下的文件和目录数组
        File srcDir = new File(srcDirectory);
        File[] fileList = srcDir.listFiles();
        // 循环处理数组
        if (fileList == null) {
            throw new java.io.FileNotFoundException();
        }
        (new File(destDirectory)).mkdir();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()) {
                // 数组中的对象为文件
                // 如果目标目录不存在，创建目标目录
                File descDir = new File(destDirectory);
                if (!descDir.exists()) {
                    descDir.mkdir();
                } // 复制文件到目标目录
                if (fileList[i].getName().compareTo("Thumbs.db") != 0) {    //windows bug
                    copyFile(srcDirectory + "/" + fileList[i].getName(), destDirectory + "/" + fileList[i].getName());
                }
            } else {
                // 数组中的对象为目录
                // 如果该子目录不存在就创建（其中也包含了对多级目录的处理）
                File subDir = new File(destDirectory + "/" + fileList[i].getName());
                if (!subDir.exists()) {
                    subDir.mkdir();
                }
                // 递归处理子目录
                copyDirectory(srcDirectory + "/" + fileList[i].getName(), destDirectory + "/" + fileList[i].getName());
            }
        }
    }

    /**
     * 列出目录下的所有目录，去除prefix路径~虚拟路径
     * @param dir
     * @param prefix
     * @return
     */
    public List fileDir(String dir, String prefix){
        File f = new File(dir);
        File[] files = f.listFiles();
        if (files==null){
            return null;
        }
        int count = files.length;
        List list = new ArrayList(count);
        for (int i=0;i<count;i++){
            if (files[i].isDirectory()){
                list.add(String.valueOf(files[i]).substring(prefix.length()));
                list.add(String.valueOf(files[i].listFiles().length));
            }
        }
        return list;
    }

    /**
     * 列出目录下前缀为prefix，后缀为suffix的文件
     * @param dir
     * @param prefix
     * @param suffix
     * @return
     */
    public List<String> fileList(String dir, String prefix, String suffix){
        File f = new File(dir);
        File[] files = f.listFiles();
        if (files==null){
            return null;
        }
        int count = files.length;
        List _list = new ArrayList(count);
        for (int i=0;i<count;i++){
            if (!files[i].isDirectory()){
                if(files[i].getName().startsWith(prefix) && files[i].getName().endsWith(suffix)){
                    _list.add(dir+"/"+files[i].getName());
                }
            }
        }
        return _list;
    }

    /**
     * 列出目录下的所有文件
     * @param dir
     * @return
     */
    public List fileList(String dir) {
        File f = new File(dir);
        File[] files = f.listFiles();
        if (files == null) {
            return null;
        }
        int count = files.length;
        List list = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            if (!files[i].isDirectory()) {
                list.add(files[i]);
            }
        }
        return list;
    }

    /**
     * 列出目录下的所有目录
     *
     * @param dir 路径
     */
    public List fileDir(String dir) {
        File f = new File(dir);
        File[] files = f.listFiles();
        if (files == null) {
            return null;
        }
        int count = files.length;
        List list = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            if (files[i].isDirectory()) {
                list.add(files[i]);
            }
        }
        return list;
    }

    /**
     * 获取目录下所有文件
     * @param dir
     * @return
     */
    public List dirFileList(String dir) {
        File f = new File(dir);
        File[] files = f.listFiles();
        if (files == null) {
            return null;
        }
        int count = files.length;
        List list = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            list.add(files[i]);
        }
        return list;
    }

    /**
     * 移动文件
     *
     * @param srcFile  eg: c:\windows\abc.txt
     * @param destPath eg: c:\temp
     * @return success
     */
    public boolean moveFile(String srcFile, String destPath) {
        //将要移动的文件或文件夹
        File file = new File(srcFile);
        //目标文件夹
        File dir = new File(destPath);
        //移动到新文件夹
        return file.renameTo(new File(dir, file.getName()));
    }
}
