import java.util.Vector;


public class Parser {
public Vector<String>cmd;
public Vector<Vector<String> >args;
public Vector<String>red;
public boolean Check(String cmd){//to check whether the current command is valid or not
String []commands={"clear","cd","ls","cp","mv","rm","mkdir","rmdir","cat","more","pwd","exit"};//didn't complete the commands yet :)	
	for(String i:commands)if(i.equals(cmd))return true;
	
return false;	
}	
public boolean parse(String Input){
cmd=new Vector<String>();
args=new Vector<Vector<String>>();
red=new Vector<String>();
String []sp1=Input.split("\\|");//the commands will be given like cd (1 arg)|pwd(no args)|cp (2 args)
// so sp1 has every command with it's args
// and sp2 has splitted the command and arguments
	for(int i=0;i<sp1.length;i++){

		String []sp2=sp1[i].split(" ");		
        if(!Check(sp2[0]))return false;//if the given command is invalid 
        Vector<String>v=new Vector<String>();		
/* we need to keep cmd and args synchornized like
 cd arg
 pwd (no args) we need to enter empty string  
 cp (2 args)       
        
 */
        cmd.addElement(sp2[0]);

        for(int j=1;j<sp2.length;j++)
        if(sp2[j].equals(">")||sp2[j].equals(">>")){red.addElement(sp2[j+1]);break;}
        else v.addElement(sp2[i]);
        
        if(v.size()==0)v.addElement("");
        if(red.size()<cmd.size())red.addElement("");
        
        args.addElement(v);

	}
	
	
	
	
return true;	
}	
	
	public static void main(String[] args) {

		
		
		
	}

}
