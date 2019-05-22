package Networking;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class ReadFile {
private String path;


public ReadFile(String file_path) {
	
	
	path = file_path;
	
	
	
}
public String[] OpenFile() throws IOException{
	
	
	FileReader fr = new FileReader(path);
	FileReader tr = new FileReader(path);

	BufferedReader textReader = new BufferedReader(fr);
	BufferedReader reader = new BufferedReader(tr);
int lines=0;

	while (reader.readLine() != null) lines++;

	int numberOfLines =lines;
	
	
	
	

	String [] textData = new String[numberOfLines];
	int i;
	for(i=0;i<numberOfLines;i++) {
		
		
		textData[i] = textReader.readLine();
	
	}
	
	
	textReader.close();
	return textData;
	
}

int readLines() throws IOException{
	
	FileReader file = new FileReader(path);
	BufferedReader bf = new BufferedReader(file);
	String line;
	int number = 0;
	line = bf.readLine();
	while((line !=null)) {
		line = bf.readLine();

		
		number++;
		
	}bf.close();
	return number;
	
	
	
	
	
	
}
}
//public static void main(String[] args) throws IOException{
		
		
		//String file_name = "C:\\Users\\zabuz\\eclipse-workspace\\Read_Write_txt\\Networking\\ok.txt";
		
		/*
		try {
			
			WriteFile data = new WriteFile(file_name,true);	
			data.writeToFile("this is another line of text");

		}
		catch(IOException e) {
			
			System.out.println(e.getMessage());
		}
		
		
		*/
		
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
	
}
}*/
