package cn.edu.hdu.chat.util;
import java.util.Properties;

/**
 * 查询Properties 文件 key值@date 2014.10.15
 * @author wangbowen
 * @version 1.0
 *
 */
public class PropertiesUtil{

  private static  Properties properties;
   
   /**
    *  
    * @param file 文件名
    */
    public static void loadProperties(String file) {  
        try {  
        	if(file!=null){
                properties = new Properties();
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();  
                properties.load(classLoader.getResourceAsStream(file));
        	}
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    /**
     * 
     * @param key  
     * @return  
     */
    public static String getValue(String key){
    	if(key != null){
    		return properties.getProperty(key);
    	}
    	return null;
    }
   
}