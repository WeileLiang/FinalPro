package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileUtil {
	public static void openFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists())
			return;

		final Runtime runtime = Runtime.getRuntime();
		Process process = null;//
		final String cmd = "rundll32 url.dll FileProtocolHandler file://" + file.getAbsolutePath();
		try {
			process = runtime.exec(cmd);
		} catch (final Exception e) {
			System.out.println("Error exec!");
		}
	}

	public static File createFile(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		return file;
	}

	public static void writeContent2File(File file, String content) {
		if (file == null || !file.exists())
			return;

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			writer.write(content);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public static void deleteFile(String filePath) {
		File file = new File(filePath);

		if (file.exists())
			file.delete();
	}

}
