package com.example.demo.utils;

public interface SendMailService {

    /**
     * 发送文本信息
     *
     * @param to      接受方
     * @param subject 主题
     * @param content 内容
     */
    void sendTxtMail(String to, String subject, String content);

    /**
     * 发送带模版的邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    void sendHtmlMail(String to, String subject, MailContentModel model);

    /**
     * 发送带附件的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param attachmentPath
     */
    void sendAttachmentMail(String to, String subject, String content, String attachmentPath);

    /**
     * 发送图片邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param rscPath 图片路径
     * @param rscId
     */
    void sendImageResourceMail(String to, String subject, String content,
                               String rscPath, String rscId);
}
