import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Terminal {
    private String DefDir = System.getProperty("user.dir");

    public void ClearScreen(){
        for (int i = 0; i < 50; ++i){
            System.out.println();
        }
    }
    public int ShowDir(String path) throws IOException /* Should be caught when its called in the parser*/{
            if (path == "") {
                System.out.print("Current Dir: ");
                System.out.print(System.getProperty("user.dir"));
                System.out.println();
            }
            else{
                Writer file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
                file.write(System.getProperty("user.dir"));
                file.close();
            }
            return 1;
    }

    public int ChangeDir(String path){
        if (path == ""){
            System.setProperty("user.dir",DefDir);
        }
        else {
            File dir;
            if (!path.contains("\\")){
                dir = new File(System.getProperty("user.dir") + "\\" + path).getAbsoluteFile();
            }
            else{
                dir = new File(path).getAbsoluteFile();
            }
            if (dir.exists()) {
                System.setProperty("user.dir", dir.getAbsolutePath());
            } else {
                return 0;
            }
        }
        return 1;
    }

    public int ListCon(String from, String to)throws IOException /* Should be caught when its called in the parser*/{
        // If the excp. is caught it means that 2nd arg. is invalid
        // If returned 0 it means that the first arg. is invalid
        if (from == "" && to == ""){
            File obj = new File(System.getProperty("user.dir"));
            ArrayList<String> files = new ArrayList<String>(Arrays.asList(obj.list()));

            for (int i = 0; i < files.size(); ++i){
                System.out.println(files.get(i));
            }

        }
        else if (from == "" && to !="" ){
            File obj = new File(System.getProperty("user.dir"));
            ArrayList<String> files = new ArrayList<String>(Arrays.asList(obj.list()));

            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(to), "utf-8"));

            for (int i = 0; i < files.size(); ++i){
                out.write(String.valueOf(files.get(i)) + System.lineSeparator());
            }
            out.close();
        }

        else if (from != "" && to =="" ){
            File obj = new File(from);
            if (!obj.exists())
                return 0;
            ArrayList<String> files = new ArrayList<String>(Arrays.asList(obj.list()));

            for (int i = 0; i < files.size(); ++i){
                System.out.println(files.get(i));
            }
        }
        else{
            File obj = new File(from);
            if (!obj.exists())
                return 0;

            ArrayList<String> files = new ArrayList<String>(Arrays.asList(obj.list()));

            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(to), "utf-8"));

            for (int i = 0; i < files.size(); ++i){
                out.write(String.valueOf(files.get(i)) + System.lineSeparator());

            }
            out.close();
        }

        return 1;
    }


    public int Copy(String src, String des)throws IOException{

        if (!src.contains("\\"))
            src = System.getProperty("user.dir") + "\\" + src;
        if (!des.contains("\\"))
            des = System.getProperty("user.dir") + "\\" + des;

        File s = new File(src);

        if (!s.exists())
            return 0;

        File d = new File (des);

        if (s.isDirectory()) {
            if (!d.exists())
                d.mkdir();
            FileUtils.copyDirectory(s, d); // download --> http://commons.apache.org/proper/commons-io/download_io.cgi
        }
        else{
            FileChannel sr = new FileInputStream(s).getChannel();
            FileChannel de = new FileOutputStream(d).getChannel();
            de.transferFrom(sr, 0, sr.size());
        }

        return 1;
    }


    public int MkDir(String path){
        if(path == "")
            return 0;

        File f = new File(path);
        f.mkdir();
        return 1;
    }

    public int RmvDir(String path) throws IOException{
        if (path == "")
            return 0;
        File des = new File(path);
        if (des.isDirectory())
            FileUtils.deleteDirectory(new File(path));
        else
            des.delete();

        return 1;
    }

    public static void main(String[] args) {
        System.out.printf("\n");
        Terminal obj = new Terminal();
        try {
            obj.ChangeDir("C:\\Users\\amr_x\\Desktop");
            obj.Copy("know.txt", "new.txt");

        }

        catch(IOException exp){
            System.out.println("EXP");;
        }
    }

}