package com.example.mail;

import java.io.File;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * 邮件服务类
 * 
 * @author sjcui
 *
 */

@Component
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * 发送纯文本邮件
	 * 
	 * @param sendFrom
	 * @param sendTo
	 * @param titel
	 * @param content
	 */
	public void sendSimpleMail(String sendFrom, String sendTo, String titel, String content) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(sendFrom);
			message.setTo(sendTo.contains(";") ? sendTo.split(";") : new String[]{sendTo});
			message.setSubject(titel);
			message.setText(content);
			mailSender.send(message);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 发送富文本邮件
	 * 
	 * @param sendFrom
	 * @param sendTo
	 * @param titel
	 * @param content
	 * @param attachmentMap
	 */
	public void sendAttachmentsMail(String sendFrom, String sendTo, String titel, String content,
			Map<String, String> attachmentMap) {

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // 默认为false，显示原始html代码，无效果
			helper.setFrom(sendFrom);
			helper.setTo(sendTo.contains(";") ? sendTo.split(";") : new String[]{sendTo});
			helper.setSubject(titel);
			helper.setText(content);

			if (attachmentMap != null) {
				attachmentMap.entrySet().stream().forEach(entrySet -> {

					File file = new File(entrySet.getValue());
					if (file.exists()) {
						try {
							helper.addAttachment(entrySet.getKey(), new FileSystemResource(file));
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					}
				});
			}

			mailSender.send(mimeMessage);
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}

}
