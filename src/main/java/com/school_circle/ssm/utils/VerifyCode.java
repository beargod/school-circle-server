package com.school_circle.ssm.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import java.util.UUID;

/**
 * Created by BigGod on 2017-04-24.
 */
public class VerifyCode {
    private String verifyCode;
    private String verifyCodeUrl;

    // 图片的宽度。
    private static int width = 160;
    // 图片的高度。
    private static int height = 40;
    // 验证码字符个数
    private static int codeCount = 5;
    // 验证码干扰线数
    private static int lineCount = 150;

    private static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N',  'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z',  '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    public static void delete(String fileName){
        File file = new File(getFileName(fileName));
        file.delete();
    }

    public static VerifyCode build() throws IOException {
        int x = 0, fontHeight = 0, codeY = 0;
        int red = 0, green = 0, blue = 0;

        x = width / (codeCount + 2);//每个字符的宽度
        fontHeight = height - 2;//字体的高度
        codeY = height - 4;

        // 图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 生成随机数
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体
        ImgFontByte imgFont = new ImgFontByte();
        Font font = imgFont.getFont(fontHeight);
        g.setFont(font);

        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width / 8);
            int ye = ys + random.nextInt(height / 8);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }

        // randomCode记录随机产生的验证码
        StringBuffer randomCode = new StringBuffer();
        // 随机产生codeCount个字符的验证码。
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * x, codeY);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        String fileName = getFileName();
        // 将四位数字的验证码保存到Session中。
        restoreVerifyCode(buffImg,fileName);
        return new VerifyCode(randomCode.toString(),fileName);
    }

    private static String getFileName(){
        String root=System.getProperty("user.dir");//项目根目录路径
        return root+"/static/img/verifyCode/"+UUID.randomUUID()+".jpg";
    }

    private static String getFileName(String name){
        String root=System.getProperty("user.dir");//项目根目录路径
        return root+"/static/img/verifyCode/"+name+".jpg";
    }

    private static void restoreVerifyCode(BufferedImage image,String fileName) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ImageIO.write(image,"jpg",fos);
    }

    private VerifyCode(String verifyCode, String verifyCodeUrl) {
        this.verifyCode = verifyCode;
        this.verifyCodeUrl = verifyCodeUrl;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public String getVerifyCodeUrl() {
        return verifyCodeUrl;
    }

    public static void main(String[] args){

            delete("604beb2a-5e8c-4e67-9c19-d44cefdda13e");

    }
}
