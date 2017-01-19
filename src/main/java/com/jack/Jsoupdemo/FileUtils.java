package com.jack.Jsoupdemo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

	public static void writeIntoFile(String content, String path) {
		// 先过滤掉文件名
		int index = path.lastIndexOf("/");
		String dir = path.substring(0, index);
		File fileDir = new File(dir);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		// 再创建路径下的文件
		File file = null;
		try {
			file = new File(path);
			file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 写入文件
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
			fileWriter.write(content);
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileWriter != null)
					fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
