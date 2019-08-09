package com.wu.shirotest;

import com.swetake.util.Qrcode;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class qrtest {
    @Test
    public void qrtest() throws IOException {
        boolean[][] bs = null;
        File file = new File("C:\\Users\\Administrator\\Desktop\\code.jpg");
        String content = "https://www.baidu.com";
        Qrcode qrCode = new Qrcode();
//        容错率 l:7 M:15 Q:25  R:30
        qrCode.setQrcodeErrorCorrect('M');
        qrCode.setQrcodeEncodeMode('B');
        qrCode.setQrcodeVersion(20);
        bs = qrCode.calQrcode(content.getBytes("utf-8"));
        int width=400;
        int height=400;
        BufferedImage bufferedImage = new BufferedImage(width, height, 1);

        Graphics2D gg = bufferedImage.createGraphics();
        gg.setBackground(Color.white);
        gg.fillRect(0,0,width,height);
        gg.setColor(Color.black);
        for (int i = 0,len=bs.length;i <len ; i++) {
            for (int j = 0; j <len ; j++) {
                if (bs[i][j]){
                    gg.fillRect(i*3,j*3,3,3);
                }
            }
        }
//        中间带图片start
        File fileImg=new File("C:\\Users\\Administrator\\Desktop\\1.png");
        Image img=ImageIO.read(fileImg);
        gg.drawImage(img,100,100,70,70,null);
        gg.dispose();
        bufferedImage.flush();
//        中间带图片end
        ImageIO.write(bufferedImage, "jpg", file);
        System.out.println("生成成功");
    }
}
