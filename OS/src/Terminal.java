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

    public void Cat(String path) throws IOException {
        BufferedReader fileContent = new BufferedReader(new FileReader(path));
        String line;
        while ((line = fileContent.readLine()) != null) {
            System.out.println(line);
        }
        fileContent.close();
    }

    public void more(String path) throws IOException {
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
    }

    public void Date() {
        String timeStamp = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println(timeStamp);
    }

    public void Args(String command) {
        switch (command) {
            case "":
                return;
            case "ClearScreen":
                System.out.println("Number of args is 0");
                break;
            case "ShowDir":
                System.out.println("Number of args is 1:path");
                break;
            case "ChangeDir":
                System.out.println("Number of args is 1:path");
                break;
            case "ListCon":
                System.out.println("Number of args is 2:from , to");
                break;
            case "Copy":
                System.out.println("Number of args is 2:from , to ");
                break;
            case "MkDir":
                System.out.println("Number of args is 1: path");
                break;
            case "RmvDir":
                System.out.println("Number of args is 1: path");
                break;
            case "cat":
                System.out.println("Number of args is 1: path");
                break;
            case "pwd":
                System.out.println("Number of args is 0");
                break;
            case "args":
                System.out.println("Number of args is 1: command");
                break;
            case "help":
                System.out.println("Number of args is 0");
                break;
            default:
                break;

        }
    }

    public void Help() {
        System.out.println("cls     : clears the screen ");
        System.out.println("cd      : changes the directory of the Terminal ");
        System.out.println("ls      : Displays all the files in the current Directory");
        System.out.println("cp      : copy a file from one place to another ");
        System.out.println("mkdir   : makes a new directory at a specific path ");
        System.out.println("rmdir   : deletes a directory ");
        System.out.println("cat     : shows the content of a file");
        System.out.println("pwd     : displays current directory");
        System.out.println("args    : List all command arguments");
        System.out.println("help    : displays all commands with a breif description");
    }

    public void Exit() {
        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            Terminal obj = new Terminal();
            obj.CD("..");
            obj.Mv("d", "n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}