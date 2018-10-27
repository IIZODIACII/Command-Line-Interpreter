import org.apache.commons.io.FileUtils;
import java.io.*;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;


public class Terminal {
    private String DefDir = System.getProperty("user.dir");

    public String get_DefDir() {
        return DefDir;
    }

    public void Clear() {
        for (int i = 0; i < 50; ++i) {
            System.out.println();
        }
    }

    public int CD(String path) {
        if (path.equals("")) {
            System.setProperty("user.dir", DefDir);
        } else if (path.equals("..")) {
            String temp = System.getProperty("user.dir");
            int idx = temp.lastIndexOf("\\");
            temp = temp.substring(0, idx);
            System.setProperty("user.dir", temp);
        } else {
            File dir;
            if (!path.contains("\\")) {
                dir = new File(System.getProperty("user.dir") + "\\" + path).getAbsoluteFile();
            } else {
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

    public int LS(String from, String to) throws IOException /* Should be caught when its called in the parser*/ {
        // If the excp. is caught it means that 2nd arg. is invalid
        // If returned 0 it means that the first arg. is invalid
        if (from.equals("") && to.equals("")) {
            File obj = new File(System.getProperty("user.dir"));
            ArrayList<String> files = new ArrayList<String>(Arrays.asList(obj.list()));

            for (int i = 0; i < files.size(); ++i) {
                System.out.println(files.get(i));
            }

        } else if (from.equals("") && !to.equals("")) {
            File obj = new File(System.getProperty("user.dir"));
            ArrayList<String> files = new ArrayList<String>(Arrays.asList(obj.list()));

            if (!to.contains("\\"))
                to = System.getProperty("user.dir") + "\\" + to;
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(to), "utf-8"));

            for (int i = 0; i < files.size(); ++i) {
                out.write(String.valueOf(files.get(i)) + System.lineSeparator());
            }
            out.close();
        } else if (!from.equals("") && to.equals("")) {
            if (!from.contains("\\"))
                from = System.getProperty("user.dir") + "\\" + from;

            File obj = new File(from);
            if (!obj.exists())
                return 0;
            ArrayList<String> files = new ArrayList<String>(Arrays.asList(obj.list()));

            for (int i = 0; i < files.size(); ++i) {
                System.out.println(files.get(i));
            }
        } else {
            if (!from.contains("\\"))
                from = System.getProperty("user.dir") + "\\" + from;

            if (!to.contains("\\"))
                to = System.getProperty("user.dir") + "\\" + to;

            File obj = new File(from);
            if (!obj.exists())
                return 0;

            ArrayList<String> files = new ArrayList<String>(Arrays.asList(obj.list()));

            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(to), "utf-8"));

            for (int i = 0; i < files.size(); ++i) {
                out.write(String.valueOf(files.get(i)) + System.lineSeparator());

            }
            out.close();
        }

        return 1;
    }

    public int CP(String src, String des) throws IOException {

        if (src.equals(""))
            return 0;

        if (des.equals(""))
            return -1;

        if (!src.contains("\\"))
            src = System.getProperty("user.dir") + "\\" + src;
        if (!des.contains("\\"))
            des = System.getProperty("user.dir") + "\\" + des;

        File s = new File(src);

        if (!s.exists())
            return 0;

        File d = new File(des);

        if (s.isDirectory()) {
            if (!d.exists())
                d.mkdir();
            FileUtils.copyDirectory(s, d);
        } else {
            FileChannel sr = new FileInputStream(s).getChannel();
            FileChannel de = new FileOutputStream(d).getChannel();
            de.transferFrom(sr, 0, sr.size());
        }

        return 1;
    }

    public int MkDir(String path) {
        if (path.equals(""))
            return 0;

        File f = new File(path);
        f.mkdir();
        return 1;
    }

    public int Rm(String path) throws IOException {
        if (path.equals(""))
            return 0;
        if (!path.contains("\\"))
            path = System.getProperty("user.dir") + "\\" + path;

        File file = new File(path);
        if (file.isDirectory())
            return 0;

        file.delete();

        return 1;
    }

    public int RmDir(String path) throws IOException {
        if (path.equals(""))
            return 0;
        if (!path.contains("\\"))
            path = System.getProperty("user.dir") + "\\" + path;

        File des = new File(path);

        if (des.isDirectory()) {
            FileUtils.deleteDirectory(new File(path));
        } else
            return 0;
        return 1;
    }

    public int pwd(String path) throws IOException {
        if (path.equals("")) {
            System.out.print("Current Dir: ");
            System.out.print(System.getProperty("user.dir"));
            System.out.println();
        } else {
            Writer file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
            file.write(System.getProperty("user.dir"));
            file.close();
        }
        return 1;
    }

    public int Mv(String src, String des) throws IOException {
        if (src.equals("") || des.equals("")) {
            return 0;
        }
        if (!src.contains("\\"))
            src = System.getProperty("user.dir") + "\\" + src;
        if (!des.contains("\\"))
            des = System.getProperty("user.dir") + "\\" + des;


        File from = new File(src);
        File to = new File(des);

        if (from.isDirectory() && to.isDirectory())
            FileUtils.moveDirectoryToDirectory(from, to, !to.exists());

        else
          FileUtils.moveToDirectory(from, to, !to.exists());

        return 1;
    }

    public String cat(String path_1,String path_2,String path_3) throws IOException {
       String fileContent = readFileContent(path_1) + readFileContent(path_2);
       writeToFile(path_3,fileContent);
       System.out.println(fileContent);
       return readFileContent(path_3);
    }



    public String more(String path) throws IOException {
        BufferedReader fileContent = new BufferedReader(new FileReader(path));
        int step = 10;
        Scanner reader = new Scanner(System.in);

        String line;
        while ((line = fileContent.readLine()) != null) {
            System.out.println(line);
            if (step == 0) {
                System.out.println("how many lines do u want to read more");
                step = reader.nextInt();
            }
            step--;
        }
        return readFileContent(path);
    }

    public String Date() {
        String timeStamp = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println(timeStamp);
        return timeStamp;
    }

    public String Args(String command) {
      String output = "" ;
        switch (command) {
            case "":
                return output;
            case "cls":
                output = "Number of args is 0";
                break;
            case "ShowDir":
              output = "Number of args is 1:path";
                break;
            case "ChangeDir":
              output = "Number of args is 1:path";
                break;
            case "ListCon":
              output = "Number of args is 2:from , to";
                break;
            case "Copy":
              output = "Number of args is 2:from , to ";
                break;
            case "MkDir":
              output = "Number of args is 1: path";
                break;
            case "RmvDir":
              output = "Number of args is 1: path";
                break;
            case "cat":
              output = "Number of args is 1: path";
                break;
            case "pwd":
              output = "Number of args is 0";
                break;
            case "args":
              output = "Number of args is 1: command";
                break;
            case "help":
              output = "Number of args is 0";
                break;
            default:
                break;

        }
        System.out.println(output);
        return output;
    }

    public String Help() {
        String output = ("cls     : clears the screen\ncd      : changes the directory of the Terminal\nls      : Displays all the files in the current Directory\ncp      : copy a file from one place to another\nmkdir   : makes a new directory at a specific path\nrmdir   : deletes a directory\ncat     : shows the content of a file\npwd     : displays current directory\nargs    : List all command arguments\nhelp    : displays all commands with a breif description");
        System.out.println(output);
        return output;
    }

    public String readFileContent (String path) throws IOException {
        Scanner scanner = new Scanner( new File(path), "UTF-8" );
        String fileContent = scanner.useDelimiter("\\A").next();
        scanner.close(); // Put this call in a finally block
        return fileContent;
    }

    public void writeToFile(String filePath,String data) throws  IOException{
        PrintWriter out = new PrintWriter(filePath);
        out.println(data);
        out.close();
    }

    public void Exit() {
        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            Terminal obj = new Terminal();
//            obj.CD("..");
//            obj.Mv("d", "n");
          System.out.println( obj.cat("/home/sadat/Desktop/1.txt","/home/sadat/Desktop/2.txt","/home/sadat/Desktop/3.txt") ) ;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}