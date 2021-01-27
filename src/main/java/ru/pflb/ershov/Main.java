package ru.pflb.ershov;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Long.parseLong;

public class Main {
    private final static String PATH_OUT_FILE = "file_out_program.pdf";
    private static Double coefficient = 1.5;

    public static void manipulatePdf(String src, String dest, Integer integer) throws DocumentException, IOException {
        PdfReader reader = new PdfReader(src);
        FileOutputStream stream = new FileOutputStream(dest);
        PdfStamper stamper = new PdfStamper(reader, stream);
        for (int i = 0; i<integer; i++) {
            TextField tf = new TextField(stamper.getWriter(),
                    new Rectangle(0, 0, 0, 0), "text");
            tf.setBorderColor(BaseColor.WHITE);
            tf.setBorderWidth(0);
            tf.setTextColor(BaseColor.WHITE);
            tf.setFontSize(0);
            tf.setText("Text field");
            PdfFormField field = tf.getTextField();
            stamper.addAnnotation(field, 1);
        }
        stamper.close();
        reader.close();
        stream.close();
    }
    public static int getIntSize(String arg2, Long fileSize){
        double size =  Integer.parseInt(arg2)*coefficient - fileSize;
        return (int) size;
    }
    public static void main(String[] args) throws IOException {

        try{
            String arg1 = args[0];
            String arg2 = args[1];
            Long longSize = parseLong(arg2);
            File file = new File(arg1);
            Long fileSize = file.length()/1024;
            int sizeInt = getIntSize(arg2, fileSize);

            manipulatePdf(arg1, PATH_OUT_FILE, sizeInt);
            File file1 = new File(PATH_OUT_FILE);
            Long fileOutSize = file1.length()/1024;

            while (fileOutSize<=(longSize-longSize*0.01)){
                System.out.println(fileOutSize+" -- " +parseLong(arg2));
                double percent = (double) (longSize-fileOutSize)/longSize;
                System.out.println(percent);
                coefficient = coefficient+percent+0.06;
                sizeInt = getIntSize(arg2, fileSize);
                manipulatePdf(arg1,PATH_OUT_FILE,sizeInt);
                fileOutSize = file1.length()/1024;
            }
        }
        catch (Exception e){
            System.out.println("Wrong arguments or something went wrong");
        }
    }
}
