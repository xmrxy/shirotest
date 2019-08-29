package com.wu.shirotest;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.Test;

import java.io.File;

public class TrainedDataTest {


    @Test
    public void test() {

        try {
            Tesseract instance = new Tesseract();
            //如果未将tessdata放在根目录下需要指定绝对路径       
            instance.setDatapath("C:\\Users\\Administrator\\Desktop");
//            instance.setDatapath("src\\main\\resources\\traineddata\\chi_sim.traineddata");
            // 如果需要识别英文之外的语种，需要指定识别语种，并且需要将对应的语言包放进项目中       
            instance.setLanguage("chi_sim");
            // 指定识别图片       
            File imgDir = new File("C:\\Users\\Administrator\\Desktop\\4.png");
            long startTime = System.currentTimeMillis();
            String ocrResult = null;
            ocrResult = instance.doOCR(imgDir);
            // 输出识别结果       
            System.out.println("OCR Result: \n" + ocrResult + "\n 耗时：" + (System.currentTimeMillis() - startTime) + "ms");
        } catch (TesseractException e) {
            e.printStackTrace();
        }

    }
}
