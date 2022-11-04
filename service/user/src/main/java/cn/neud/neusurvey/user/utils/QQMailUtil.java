package cn.neud.neusurvey.user.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Random;

public class QQMailUtil {
    public static String EMAIL = "1579571282@qq.com";
    public static String HOST = "smtp.qq.com";
    public static String PORT = "587";
    public static String PASSWORD = "ktxwbttefooghcfj";

    public static void main(String[] args) throws EmailException {
        sendEmail("1306346266@qq.com");
    }
    public static void sendEmail(String account) throws EmailException {
        SimpleEmail mail = new SimpleEmail();
        // 设置邮箱服务器信息
        mail.setSslSmtpPort(PORT);
        mail.setHostName(HOST);
        // 设置密码验证器
        mail.setAuthentication(EMAIL, PASSWORD);
        // 设置邮件发送者
        mail.setFrom(EMAIL);
        String email = account;
        // 设置邮件接收者
        System.out.println(email);
//            String[] strings = email.split("@");
//            String email_str = strings[0] + "@qq.com";
        mail.addTo(email);
        // 设置邮件编码
        mail.setCharset("UTF-8");
        // 设置邮件主题
        mail.setSubject("尚医同验证码");
        String code = "1";
        // 设置邮件内容
        mail.setMsg("您的验证码为："+code+"，打死也不要告诉别人呀！！");
        // 设置邮件发送时间
        mail.setSentDate(new Date());
        // 发送邮件
        mail.send();
        System.out.println("yes");
    }
    /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     */
    public static MimeMessage createSimpleMail(Session session, String receiveMail) throws Exception {
        //  获取6位随机验证码（英文）
        String[] letters = new String[]{
                "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m",
                "A", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(letters[(int) Math.floor(Math.random() * letters.length)]);
        }

        //获取6位随机验证码（中文），根据项目需要选择中英文
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);

        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress("邮件发出者"));
        // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveMail));
        // 邮件的标题
        message.setSubject("验证码");
        // 邮件的文本内容
        message.setContent("您的验证码：" + verifyCode + "，如非本人操作，请忽略！请勿回复此邮箱", "text/html;charset=UTF-8");

        // 返回创建好的邮件对象
        return message;
    }
}
