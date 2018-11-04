package es.source.code.activity.Bean;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;

/**
 * Created by Administrator on 2017/4/10.
 */

public class SendMailUtil {
//    //qq
//    private static final String HOST = "smtp.qq.com";
//    private static final String PORT = "587";
//    private static final String FROM_ADD = "zhuqiyou3075@163.com"; //发送方邮箱
//    private static final String FROM_PSW = "zhuqiyou";//发送方邮箱授权码

    //163
    private static final String HOST = "smtp.163.com";//
    private static final String PORT = "25";
    private static final String FROM_ADD = "zhuqiyou3075@163.com";
    private static final String FROM_PSW = "ZQY943388";
    private static final String TO_ADD = "2022600565@qq.com";
//    private static final String TO_ADD = "2584770373@qq.com";

    public static void send(final File file,String subject,String content){
        final HelpMailInfo mailInfo = creatMail(subject,content);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendFileMail(mailInfo,file);
            }
        }).start();
    }

    public static void send(String subject,String content){
        final HelpMailInfo mailInfo = creatMail(subject,content);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean b = sms.sendTextMail(mailInfo);
                Log.i("发送",Boolean.toString(b));
            }
        }).start();
    }

    @NonNull
    private static HelpMailInfo creatMail(String subject,String content) {
        final HelpMailInfo mailInfo = new HelpMailInfo();
        mailInfo.setMailServerHost(HOST);
        mailInfo.setMailServerPort(PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(FROM_ADD); // 你的邮箱地址
        mailInfo.setPassword(FROM_PSW);// 您的邮箱密码
        mailInfo.setFromAddress(FROM_ADD); // 发送的邮箱
        mailInfo.setToAddress(TO_ADD); // 发到哪个邮件去
        mailInfo.setSubject(subject); // 邮件主题
        mailInfo.setContent(content); // 邮件文本
        return mailInfo;
    }
}
