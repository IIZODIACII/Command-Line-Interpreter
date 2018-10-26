import java.io.IOException;
import java.util.Scanner;


public class Linux {

	public static void main(String[] args) {
Scanner Input=new Scanner(System.in);
Parser par=new Parser();
Terminal ter=new Terminal();		
String command="";
System.out.println("Welcome");
while(!command.equals("exit")){
command=Input.nextLine();
	if(!par.parse(command)){System.out.println("Enter a valid Input Please");continue;}
	for(int i=0;i<par.cmd.size();i++){
if(par.cmd.elementAt(i).equals("clear")){
	
	if(par.args.elementAt(i).elementAt(0).equals(""))ter.ClearScreen();
	else System.out.println("It takes no args");
}		
else if(par.cmd.elementAt(i).equals("cd"))ter.ChangeDir(par.args.elementAt(i).elementAt(0));		
else if(par.cmd.elementAt(i).equals("mkdir")){
if(ter.MkDir(par.args.elementAt(i).elementAt(0))==0)System.out.println("Please Enter A directory");	
	
}		
else if(par.cmd.elementAt(i).equals("rmdir")){
try {
	if(ter.RmvDir(par.args.elementAt(i).elementAt(0))==0)System.out.println("Please Enter A directory");
} catch (IOException e) {
	e.printStackTrace();
}	
	
	
}		
else if(par.cmd.elementAt(i).equals("cat")){
	try{
		ter.cat(par.args.elementAt(i).elementAt(0));
	}
	catch(IOException e){
		e.printStackTrace();
	}
}
else if(par.cmd.elementAt(i).equals("more")){
	try{
		ter.more(par.args.elementAt(i).elementAt(0));
	}
	catch(IOException e){
		e.printStackTrace();
	}
}
else if(par.cmd.elementAt(i).equals("help"))ter.help();
else if(par.cmd.elementAt(i).equals("args"))ter.args(par.args.elementAt(i).elementAt(0));
else if(par.cmd.elementAt(i).equals("date"))ter.date();
else if(par.cmd.elementAt(i).equals("cp")){
	try{
		int size=par.args.elementAt(i).size();
		if(ter.Copy(par.args.elementAt(i).elementAt(0),
				size>1?par.args.elementAt(i).elementAt(1):"")!=1)
			System.out.println("Enter a valid Input");
		
	}
	catch(IOException e){e.printStackTrace();}
}


	}
}	
		
		
		
	Input.close();	
	}

}
