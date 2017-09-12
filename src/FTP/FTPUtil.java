package FTP;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;


public class FTPUtil {

	/**
	 * 去 服务器的FTP路径下上读取文件
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public InputStream readConfigFileForFTP(String ftpUserName,
			String ftpPassword, String ftpPath, String ftpHost, int ftpPort,
			String fileName) {
		StringBuffer resultBuffer = new StringBuffer();
		FileInputStream inFile = null;
		InputStream in = null;
		FTPClient ftpClient = null;
		System.out.println("开始读取绝对路径" + ftpPath + "文件!");
		try {
			ftpClient = FTPUtil.getFTPClient(ftpHost, ftpPassword, ftpUserName,
					ftpPort);
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpPath);
			in = ftpClient.retrieveFileStream(fileName);
			return in;
		} catch (FileNotFoundException e) {
			System.out.println("没有找到" + ftpPath + "文件");
			e.printStackTrace();
		} catch (SocketException e) {
			System.out.println("连接FTP失败.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("文件读取错误。");
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 获取FTPClient对象
	 * @param ftpHost FTP主机服务器
	 * @param ftpPassword FTP 登录密码
	 * @param ftpUserName FTP登录用户名
	 * @param ftpPort FTP端口 默认为21
	 * @return
	 */
	public static FTPClient getFTPClient(String ftpHost, String ftpPassword,
			String ftpUserName, int ftpPort) {
		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
			ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				System.out.println();
				System.out.println("未连接到FTP，用户名或密码错误。");
				ftpClient.disconnect();
			} else {
				System.out.println("FTP连接成功。");
			}
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("FTP的IP地址可能错误，请正确配置。");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("FTP的端口错误,请正确配置。");
		}
		return ftpClient;
	}
}
