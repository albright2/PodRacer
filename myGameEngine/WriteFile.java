package myGameEngine;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;




public class WriteFile {

	private String path;
	private boolean append_to_file=false;
	
	public WriteFile(String file_path) {
		
		path = file_path;
		
		
	}
	
	public WriteFile(String file_path,boolean append_value) {
		
		path = file_path;
		
		append_to_file =append_value;
	}
	

	
	public void writeToFile(String textLine)throws IOException{
		
		FileWriter write = new FileWriter(path,append_to_file);
		PrintWriter print_line = new PrintWriter(write);
		
		print_line.printf("%s"+"%n", textLine);
		print_line.close();
		
		
		
	}
		
	public static void main(String[] args) throws IOException{
		
					System.out.println("gothere");

		String file_name = "C:\\Users\\zabuz\\eclipse-workspace\\Read_Write_txt\\Networking\\ok.txt";
		
		
		try {
			
			WriteFile data = new WriteFile(file_name,true);	
			
			data.writeToFile("this is another line of text");
			
		}
		catch(IOException e) {
			
			System.out.println(e.getMessage());
		}
		
		
		
		
		//uncomment to read a file
		/////////////////////////////////////////////
	
	/*
	try{
	
	ReadFile file = new ReadFile(file_name);
	String [] aryLines = file.OpenFile();
	
	int i;
	for(i=0;i<aryLines.length;i++) {
		
		System.out.println(aryLines[i]);
		
	}
	
	}
	catch(IOException e) {System.out.println(e.getMessage());}
	
	*/
	
	
	
	
	
	
		
		
		
		
		
		
		
		
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
