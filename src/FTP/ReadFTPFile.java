package FTP;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class ReadFTPFile {
	
	 public static final byte[] input2byte(InputStream inStream)  
	            throws IOException {  
	        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
	        byte[] buff = new byte[100];  
	        int rc = 0;  
	        while ((rc = inStream.read(buff, 0, 100)) > 0) {  
	            swapStream.write(buff, 0, rc);  
	        }  
	        byte[] in2b = swapStream.toByteArray();  
	        return in2b;  
	    } 

	/**
	 * 去 服务器的FTP路径下上读取文件
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public String readConfigFileForFTP(String ftpUserName, String ftpPassword,
			String ftpPath, String ftpHost, int ftpPort, String fileName) {
		StringBuffer resultBuffer = new StringBuffer();
		FileInputStream inFile = null;
		InputStream in = null;
		FTPClient ftpClient = null;
		System.out.println("开始读取绝对路径" + ftpPath + "文件!");
		try {
			ftpClient = FTPUtil.getFTPClient(ftpHost, ftpPassword, ftpUserName,
					ftpPort);
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			//ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpPath);
			ftpClient.enterLocalPassiveMode(); 
			FTPFile[] a = ftpClient.listFiles();
			System.out.println(a);
			in = ftpClient.retrieveFileStream(fileName);
			byte b[] = input2byte(in);
		    System.out.println(b);
			//System.out.println(in.read());
		} catch (FileNotFoundException e) {
			System.out.println("没有找到" + ftpPath + "文件");
			e.printStackTrace();
			return "下载配置文件失败，请联系管理员.";
		} catch (SocketException e) {
			System.out.println("连接FTP失败.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("文件读取错误。");
			e.printStackTrace();
			return "配置文件读取失败，请联系管理员.";
		}
		if (in != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String data = null;
			try {
				while ((data = br.readLine()) != null) {
					resultBuffer.append(data + "\n");
				}
			} catch (IOException e) {
				System.out.println("文件读取错误。");
				e.printStackTrace();
				return "配置文件读取失败，请联系管理员.";
			}finally{
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{
			System.out.println("in为空，不能读取。");
			return "配置文件读取失败，请联系管理员.";
		}
		return resultBuffer.toString();
	}
}
