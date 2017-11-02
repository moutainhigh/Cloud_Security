package com.ctbri.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendEmailThread  implements Runnable{
	private String rcptto = null;// 收件人
	private String subject = null; // 主题
	private String content = null; // 邮件内容

	public SendEmailThread(String rcptto,String subject, String content) {
		this.rcptto = rcptto;
		this.subject = subject;
		this.content = content;
		
	}

	public void run() {
		// 发送邮件 三个步骤
		// 1、 session
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.host", "mail.ctbri.com.cn");
		//properties.setProperty("mail.smtp.host", "smtp.163.com");
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(properties);

		// 编写Message
		Message message = new MimeMessage(session);
		try {
			// 设置发件人
			message.setFrom(new InternetAddress("yichuanmei@ctbri.com.cn"));
			//message.setFrom(new InternetAddress("You_Xue_Tong@163.com"));
			// 设置收件人
			message.setRecipient(RecipientType.TO, new InternetAddress(rcptto));
			// 设置邮件主题
			message.setSubject(subject);
			// 设置内容
			message
					.setContent(content,"text/html;charset=utf-8");
			// 通过Transport发送
			Transport transport = session.getTransport();
			transport.connect("yichuanmei@ctbri.com.cn", "123456");
			//transport.connect("You_Xue_Tong@163.com", "youxuetong");
			transport.sendMessage(message, message.getAllRecipients());
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
}
