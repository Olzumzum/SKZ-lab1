package sample.workFiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileAdapter {

    static BufferedImage image;


    public void setImage(BufferedImage image){
        FileAdapter.image = image;
    }

    public File getFile(Color[][] data) {

        BufferedImage bufferedImage = new BufferedImage(120,120, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D graphics = bufferedImage.createGraphics();
//        graphics.setComposite(AlphaComposite.Src);
//        for (int i = 0, count = 0; i < data.length; i++) {
//            for (int j = 0; j < data.length; j++){
//                graphics.setPaint(data[i][j]);
//                graphics.fillRect ( 0, 0, 120, 120 );
//            }
//        }



        writeBufferedImage(image);

//        byte[]bDate = new byte[data.length * data.length];
//        for (int i = 0, count = 0; i < data.length; i++) {
//            for (int j = 0; j < data.length; j++){
//                bDate[count++] = (byte) data[i][j].getRGB();
//            }
//        }
//
//        try {
//            FileOutputStream fos = new FileOutputStream(savePath);
//            fos.write(bDate);
//            fos.flush();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    /**
     * удалить временный файл, созданный отфильтрованным объектом
     */
    public void deleteTemporaryFile(){
        String savePath = System.getProperty("user.dir") +
                System.getProperty("file.separator") + "src" + System.getProperty("file.separator") +
                "res" + System.getProperty("file.separator") + "txt.jpeg";

        File file = new File(savePath);

        if (file.delete()) {
            System.out.println(file.getName() + " deleted");
        } else {
            System.out.println(file.getName() + " not deleted");
        }
    }

    private File writeBufferedImage(BufferedImage bi){
        String savePath = System.getProperty("user.dir") +
                System.getProperty("file.separator") + "src" + System.getProperty("file.separator") +
                "res" + System.getProperty("file.separator") + "temporary.jpg" ;

        File file = new File(savePath);
        try {
            ImageIO.write(bi, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    }
