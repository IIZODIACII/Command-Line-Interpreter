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
String output="";	
command=Input.nextLine();
	if(!par.parse(command)){System.out.println("Enter a valid Input Please");continue;}
	for(int i=0;i<par.cmd.size();i++){
if(par.cmd.elementAt(i).equals("clear")){
	
	if(par.args.elementAt(i).elementAt(0).equals(""))ter.Clear();
	else System.out.println("It takes no args");
}		
else if(par.cmd.elementAt(i).equals("cd"))ter.CD(par.args.elementAt(i).elementAt(0));		
else if(par.cmd.elementAt(i).equals("mkdir")){
if(ter.MkDir(par.args.elementAt(i).elementAt(0))==0)System.out.println("Please Enter A directory");	
	
}		
else if(par.cmd.elementAt(i).equals("rmdir")){
try {
	if(ter.RmDir(par.args.elementAt(i).elementAt(0))==0)System.out.println("Please Enter A directory");
} catch (IOException e) {
	e.printStackTrace();
}	
	
	
}		
  else if(par.cmd.elementAt(i).equals("cat")){
	try{int size=par.args.size();if(size<3)System.out.println("Missing Arguments");
	else output=ter.cat(par.args.elementAt(i).elementAt(0),
				par.args.elementAt(i).elementAt(1),
				par.args.elementAt(i).elementAt(2));
	}
	catch(IOException e){
		e.printStackTrace();
	}
}

else if(par.cmd.elementAt(i).equals("more")){
	try{
		output=ter.more(par.args.elementAt(i).elementAt(0));
	}
	catch(IOException e){
		e.printStackTrace();
	}
}
else if(par.cmd.elementAt(i).equals("help"))output=ter.Help();
else if(par.cmd.elementAt(i).equals("args"))output=ter.Args(par.args.elementAt(i).elementAt(0));
else if(par.cmd.elementAt(i).equals("date"))output=ter.Date();
else if(par.cmd.elementAt(i).equals("cp")){
	try{
		int size=par.args.elementAt(i).size();
        if(ter.CP(par.args.elementAt(i).elementAt(0),
				size>1?par.args.elementAt(i).elementAt(1):"")!=1)
			System.out.println("Enter a valid Input");
		
	}
	catch(IOException e){e.printStackTrace();}
}
else if(par.cmd.elementAt(i).equals("ls")){
	int size=par.args.elementAt(i).size();
try{
	if(ter.LS(par.args.elementAt(i).elementAt(0),
			size>1?par.args.elementAt(i).elementAt(1):"")!=1)
		System.out.println("Enter a valid Input");
	
}
catch(IOException e){e.printStackTrace();}
	
}
if(!par.red.elementAt(i).equals(""))
	try {
		ter.writeToFile(par.red.elementAt(i),output);
	} catch (IOException e){e.printStackTrace();}
else System.out.println(output);
	
	
	}



}	
		
		
		
	Input.close();	
	}

}

