package sample.workFiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileAdapter {

    static BufferedImage image;


    public void setImage(BufferedImage image){
        FileAdapter.image = image;
    }

    public File getFile(Color[][] data) {

        BufferedImage bufferedImage = createImageFromPixels(image);

        saveBufferedImage(bufferedImage);

        return null;
    }

    /**
     * создать изображение на основе полученного
     * @param image
     * @return
     */
    private BufferedImage createImageFromPixels(BufferedImage image){
        BufferedImage bufferedImage = new BufferedImage(120,120, BufferedImage.TYPE_INT_RGB);

        int width = image.getWidth();
        int height = image.getHeight();
        int[] result = new int[height * width];

        int count = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[count] = image.getRGB(row, col);
                bufferedImage.setRGB(row, col, result[count]);
            }
            count++;
        }

        return bufferedImage;
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

    private File saveBufferedImage(BufferedImage bi){
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
