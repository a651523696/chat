package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateHtml {
	public static void main(String [] args) throws IOException{
		File file = new File("html.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("out.txt")));
		String line = null;
		while((line = br.readLine()) != null){
			line = line.trim().replaceAll("\"", "\\\\\"");
			line = "\""+line+"\"+";
			bw.write(line);
			bw.newLine();
		}
		br.close();
		bw.close();
	}
}
