package com.jack.Jsoupdemo;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;

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

	// 传入zhiHuPicBean，创建文件夹，并下载图片
	public static boolean downLoadPics(ZhiHuPicBean zhiHuPicBean, String filePath) throws Exception {
		boolean isSuccess = true;
		// 文件路径+标题
		String dir = filePath + zhiHuPicBean.getQuestion();
		// 创建
		File fileDir = new File(dir);
		fileDir.mkdirs();
		// 获取所有图片路径集合
		List<String> zhiHuPics = zhiHuPicBean.getZhihuPicUrl();
		// 初始化一个变量，用来显示图片编号
		int i = 1;
		// 循环下载图片
		for (String zhiHuPic : zhiHuPics) {
			URL url = new URL(zhiHuPic);
			// 打开网络输入流
			DataInputStream dis = new DataInputStream(url.openStream());
			String newImageName = dir + "/" + "图片" + i + ".jpg";
			// 建立一个新的文件
			FileOutputStream fos = new FileOutputStream(new File(newImageName));
			byte[] buffer = new byte[1024];
			int length;
			System.out.println("正在下载......第 " + i + "张图片......请稍后");
			// 开始填充数据
			while ((length = dis.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
			dis.close();
			fos.close();
			System.out.println("第 " + i + "张图片下载完毕......");
			i++;
		}
		return isSuccess;
	}
}
