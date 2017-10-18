package com.cn.ctbri.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class SFTPUtil {
	private static String host;
	private static int port;
	private static String username;
	private static String password;
	private static String serviceIconDirectory;
	private static String serviceDetailDirectory;
	private static String advertisementDirectory;
	
	private static String imageShow;
	
	static {
		
		InputStream in = SFTPUtil.class.getClassLoader().getResourceAsStream("upload.properties");
		Properties properties = new Properties();
		try {
			properties.load(in);
			host = properties.getProperty("host");
			port = Integer.parseInt(properties.getProperty("port"));
			username = properties.getProperty("username");
			password = properties.getProperty("password");
			serviceIconDirectory = properties.getProperty("serviceIconDirectory");
			serviceDetailDirectory = properties.getProperty("serviceDetailDirectory");
			advertisementDirectory = properties.getProperty("advertisementDirectory");
			imageShow= properties.getProperty("imageShow");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * sftp连接
	 */
	public static ChannelSftp connect() {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			Session sshSession = jsch.getSession(username, host, port);
			System.out.println("Session created.");
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			System.out.println("Session connected.");
			System.out.println("Opening Channel.");
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			System.out.println("Connected to " + host + ".");
		} catch (Exception e) {
			System.out.println("连接:[" + host + "]ftp服务器异常");
		}
		return sftp;
	}

	/**
	 * 上传文件 流 本地文件路径 remotePath 服务器路径
	 * @throws IOException 
	 */
	public static boolean upload(MultipartFile file,String remotName, int folderFlag){
		ChannelSftp sftp = connect();
		try {
				String rpath=null;
				if (folderFlag==1){
					rpath = serviceIconDirectory; // 服务器需要创建的路径
				}else if (folderFlag==2){
					rpath = serviceDetailDirectory; // 服务器需要创建的路径
				}else if (folderFlag==3) {
					rpath = advertisementDirectory;
				}
				
				try {
					createDir(rpath, sftp);
				} catch (Exception e) {
					System.out.println("创建路径失败：" + rpath);
				}
				// this.sftp.rm(file.getName());
				sftp.cd(rpath);
				sftp.put(file.getInputStream(), remotName, new ProgressMonitor(file.getSize()),ChannelSftp.OVERWRITE);
				return true;
		} catch (FileNotFoundException e) {
			System.out.println("上传文件没有找到");
		} catch (SftpException e) {
			System.out.println("上传ftp服务器错误");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 上传文件 流 本地文件路径 remotePath 服务器路径
	 * @throws IOException 
	 */
	public static boolean upload(InputStream ins,String remotName, int folderFlag){
		ChannelSftp sftp = connect();
		try {
				String rpath=null;
				if (folderFlag==1){
					rpath = serviceIconDirectory; // 服务器需要创建的路径
				}else if (folderFlag==2){
					rpath = serviceDetailDirectory; // 服务器需要创建的路径
				}else if (folderFlag==3) {
					rpath = advertisementDirectory;
				}
				
				try {
					createDir(rpath, sftp);
				} catch (Exception e) {
					System.out.println("创建路径失败：" + rpath);
				}
				// this.sftp.rm(file.getName());
				sftp.cd(rpath);
				sftp.put(ins, remotName,ChannelSftp.OVERWRITE);
				return true;
//		} catch (FileNotFoundException e) {
//			System.out.println("上传文件没有找到");
		} catch (SftpException e) {
			e.printStackTrace();
			System.out.println("上传ftp服务器错误");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 创建一个文件目录
	 */
	public static void createDir(String createpath, ChannelSftp sftp) {
		try {
			if (isDirExist(createpath,sftp)) {
				sftp.cd(createpath);
			}
			String pathArry[] = createpath.split("/");
			StringBuffer filePath = new StringBuffer("/");
			for (String path : pathArry) {
				if (path.equals("")) {
					continue;
				}
				filePath.append(path + "/");
				if (isDirExist(filePath.toString(),sftp)) {
					sftp.cd(filePath.toString());
				} else {
					// 建立目录
					sftp.mkdir(filePath.toString());
					// 进入并设置为当前目录
					sftp.cd(filePath.toString());
				}
			}
			sftp.cd(createpath);
		} catch (SftpException e) {
			System.out.println("创建路径错误：" + createpath);
		}
	}

	/**
	 * 判断目录是否存在
	 */
	public static boolean isDirExist(String directory,ChannelSftp sftp) {
		boolean isDirExistFlag = false;
		try {
			SftpATTRS sftpATTRS = sftp.lstat(directory);
			isDirExistFlag = true;
			return sftpATTRS.isDir();
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().equals("no such file")) {
				isDirExistFlag = false;
			}
		}
		return isDirExistFlag;
	}

	public static String getImageShow() {
		return imageShow;
	}
	
/*	public static void main(String[] args){
		ChannelSftp sftp = connect(username, host, port, password);
		upload(new File("G:\\360Downloads\\Software\\FlashFXP_5.4.3939.exe"),"test111.txt",directory,sftp);
		sftp.quit();
		
	}*/
}
