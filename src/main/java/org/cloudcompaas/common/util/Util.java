package org.cloudcompaas.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Arrays;

/**
 * @author angarg12
 *
 */
public class Util {
	public static byte[] toBytes(Object obj) throws java.io.IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		bos.close();
		byte[] data = bos.toByteArray();
		return data;
	}

	public static Object fromBytes(byte[] bytes) throws java.io.IOException {
		Object object = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		try {
			object = ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		} 
		return dir.delete(); 
	}

	public static boolean arrayContains(Object[] element, Object[][] array) {
		for (int j = 0; j < array.length; j++) {
			if (Arrays.equals(element, array[j]))
				return true;
		}
		return false;
	}
	
	public static File copyFileToTemp(InputStream is, String dirName, String fileName) {
		File path = new File(System.getProperty("java.io.tmpdir")+"/"+dirName);
		File outputfile = new File(path+"/"+fileName);
		try{
			if(path.exists() == false){
				path.mkdir();
			}
			if(outputfile.exists() == false){
				outputfile.createNewFile();
			}
			BufferedInputStream bis = new BufferedInputStream(is);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputfile));
			int FILE_CHUNK_SIZE = 10000;
			byte[] bytearray = new byte[FILE_CHUNK_SIZE];
			int count = 0;
			while((count = bis.read(bytearray, 0, FILE_CHUNK_SIZE)) != -1){
				bos.write(bytearray, 0, count);
			}
			bos.flush();
			bos.close();
		}catch(Exception e){
			e.printStackTrace();
			// TODO error msg
			return null;
		}
		return outputfile;
	}
	
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for(int i = 0; i < b.length; i++){
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString();
	}
	
	public static boolean isNumeric(String str){
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  formatter.parse(str, pos);
	  return str.length() == pos.getIndex();
	}

}
