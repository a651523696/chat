package test;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class ClearLastUpdated {
	public static void main(String [] args){
		String path = "C:\\Users\\Administrator\\.m2\\repository";
		File file = new File(path);
		deleteFile(file);
	}
	public static void deleteFile(File file){
		if(file.isFile()){
			if(file.getName().endsWith("lastUpdated")){
				System.out.println("delete:" + file.getName());
				file.delete();
			}
			return;
		}
		File [] childs = file.listFiles();
		for(File child:childs){
			deleteFile(child);
		}
	}
}
