import java.io.*;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
            System.out.println("i have commented the actual line please fix me");
//            FileUtils.deleteDirectory(new File(path));
        else
            des.delete();

        return 1;
    }


    //

    public void cat(String path_1 , String path_2 , String path_3   ) throws IOException {
      String FileContent = getFileContent(path_1);
      FileContent += "\n" + getFileContent(path_2);

    }

    public String getFileContent(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner( new File(path) );
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();
        return text;
    }

    public void writeToFile(String path) throws IOException {

    }

    public void more(String path)throws IOException {
        BufferedReader fileContent = new BufferedReader(new FileReader(path));
        int step = 10;
        Scanner reader = new Scanner(System.in);

        String line;
        while((line = fileContent.readLine()) != null)
        {
            System.out.println(line);
            if (step == 0){
                System.out.println("how many lines do u want to read more");
                step = reader.nextInt();
            }
            step --;
        }
    }

    public void pwd() {
        System.out.println(System.getProperty("user.dir"));
    }

    public void date(){
        String timeStamp = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println(timeStamp);
    }

    public void args(String command){
        switch (command){
            case "": return;
            case "ClearScreen": System.out.println("Number of args is 0");break;
            case "ShowDir": System.out.println("Number of args is 1:path");break;
            case "ChangeDir": System.out.println("Number of args is 1:path");break;
            case "ListCon": System.out.println("Number of args is 2:from , to");break;
            case "Copy": System.out.println("Number of args is 2:from , to ");break;
            case "MkDir": System.out.println("Number of args is 1: path");break;
            case "RmvDir": System.out.println("Number of args is 1: path");break;
            case "cat": System.out.println("Number of args is 1: path");break;
            case "pwd": System.out.println("Number of args is 0");break;
            case "args": System.out.println("Number of args is 1: command");break;
            case "help": System.out.println("Number of args is 0");break;
            default: break;

        }
    }

    public void help (){
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





    public static void main(String[] args) throws IOException {
        System.out.printf("\n");
        Terminal terminal = new Terminal();
//        terminal.ChangeDir("\"/home/sadat/Desktop/\"");
//            obj.Copy("know.txt", "new.txt");
//       System.setProperty("user.dir","\"/home/sadat/Desktop/\"");
        terminal.help();

    }

}