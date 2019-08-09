package com.wu.shirotest.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.swetake.util.Qrcode;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class QrCodeController {

    @RequestMapping(value = "/qrcodeHtml")
    public ModelAndView qrCodeHtml(ModelAndView modelAndView) {
        modelAndView.setViewName("qrcode");
        return modelAndView;
    }

    @RequestMapping(value = "/qrCode")
    @ResponseBody
    public Object qrCodeTest(@RequestParam("url") String url,
                             @RequestParam("imgPath") String imgPath,
                             @RequestParam("savePath") String savePath) {
        String result = null;
        Map map = new HashMap();
        if (url == null || "".equals(url) || savePath == null || "".equals(savePath)) {
            map.put("result", "0");
        }
        int code = getQrCode(url.trim(), imgPath.trim(), savePath.trim());
        if (code == 1) {
            map.put("result", "1");
        } else {
            map.put("result", "0");
        }
        return map;
    }

    private int getQrCode(String url, String imgPath, String savePath) {
        try {
            boolean[][] bs = null;
            File file = new File(savePath + "/code.jpg");
            String content = url;
            Qrcode qrCode = new Qrcode();
//        容错率 l:7 M:15 Q:25  R:30
            qrCode.setQrcodeErrorCorrect('Q');
            qrCode.setQrcodeEncodeMode('B');
            qrCode.setQrcodeVersion(20);

            bs = qrCode.calQrcode(content.getBytes("utf-8"));

            int width = 400;
            int height = 400;
            BufferedImage bufferedImage = new BufferedImage(width, height, 1);

            Graphics2D gg = bufferedImage.createGraphics();
            gg.setBackground(Color.white);
            gg.fillRect(0, 0, width, height);
            gg.setColor(Color.black);

            for (int i = 0, len = bs.length; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    if (bs[i][j]) {
                        gg.fillRect(i * 3, j * 3, 3, 3);
                    }
                }
            }
            if (imgPath != null || !"".equals(imgPath)) {
//        中间带图片start
                File fileImg = new File(imgPath);
                Image img = ImageIO.read(fileImg);
                gg.drawImage(img, 100, 100, 70, 70, null);
                gg.dispose();
                bufferedImage.flush();
//        中间带图片end
            }
            ImageIO.write(bufferedImage, "jpg", file);
            System.out.println("生成成功");
            System.out.println(savePath);
            Thread.sleep(1000);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @RequestMapping(value = "/qrCode2")
    @ResponseBody
    public Object qrCode2(@RequestParam("url") String url,
                          @RequestParam("imgPath") String imgPath,
                          @RequestParam("savePath") String savePath) {
        Map map = new HashMap();
        if (url == null || "".equals(url) || savePath == null || "".equals(savePath)) {
            map.put("result", "0");
        }
        int code = getQrCode2(url.trim(), imgPath.trim(), savePath.trim());
        if (code == 1) {
            map.put("result", "1");
        } else {
            map.put("result", "0");
        }
        return map;
    }

    private int getQrCode2(String url, String imgPath, String savePath) {
        try {
            int width = 400;
            int heigth = 400;
            Map<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, heigth, hints);

            BufferedImage image = new BufferedImage(width, heigth, 1);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < heigth; j++) {
                    image.setRGB(i, j, bitMatrix.get(i, j) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            File file = new File(savePath + "/code.jpg");
            System.out.println("imgPath====" + imgPath);
//        中间带图片start
            Graphics2D gg = image.createGraphics();
            File fileImg = new File(imgPath);
            Image img = ImageIO.read(fileImg);
            gg.drawImage(img, 170, 170, 50, 50, null);
            gg.dispose();
            image.flush();
//        中间带图片end
            ImageIO.write(image, "jpg", file);
            System.out.println(savePath);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
