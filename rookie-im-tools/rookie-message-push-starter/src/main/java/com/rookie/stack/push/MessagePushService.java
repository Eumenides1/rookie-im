package com.rookie.stack.push;

public interface MessagePushService {
    /**
     * 发送简单文本消息
     */
    void sendText(String to, String subject, String content);

    /**
     * 使用模板发送消息
     */
    void sendTemplate(String to, String subject, String templateName, Object model);

    /**
     * 发送 HTML 格式的邮件
     *
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param html    HTML 格式的邮件内容
     */
    void sendHtmlEmail(String to, String subject, String html);
}
