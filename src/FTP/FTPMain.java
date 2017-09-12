package FTP;

import java.io.InputStream;
import java.util.Properties;


public class FTPMain {
	

	public static void main(String[] args) {
		int ftpPort = 0;
		String ftpUserName = "";
		String ftpPassword = "";
		String ftpHost = "";
		String ftpPath = "";
		String writeTempFielPath = "";
		try {
			
			//ftp://192.168.20.189/09_soft/%BF%AA%B7%A2%CF%E0%B9%D8/maven/apache-maven-2.2.1-bin.zip
			
			
			ftpUserName = "anonymous";
			ftpPassword = "";
			ftpHost = "192.168.20.189";
			ftpPort = 21;
			ftpPath = "/09_soft/SVN/";
			writeTempFielPath = "";
			ReadFTPFile read = new ReadFTPFile();
			String result = read.readConfigFileForFTP(ftpUserName, ftpPassword, ftpPath, ftpHost, ftpPort, "site-1.6.18.zip");
			System.out.println("读取配置文件结果为：" + result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
