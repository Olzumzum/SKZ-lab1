package sample.workFiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class FileAdapter {

    String savePath = System.getProperty("user.dir") +
            System.getProperty("file.separator") + "src" + System.getProperty("file.separator") +
            "res" + System.getProperty("file.separator") + "temporary.jpg";


    static BufferedImage image;


    public void setImage(BufferedImage image) {
        FileAdapter.image = image;
    }

    public File getFile(Color[][] data) {
        int[] pixels = createPixelArray(image);
        BufferedImage bufferedImage = createImageFromPixels(pixels, image.getHeight(), image.getWidth());

        saveBufferedImage(bufferedImage);

        File file = new File(savePath);


        return file;
    }


    /**
     * создать массив пикселей на основе
     * полученного изображения
     *
     * @param image входное изображение
     * @return массив пикселей, соответствующий входному
     * изобрашению
     */
    private int[] createPixelArray(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[height * width];

        for (int row = 0, count = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                pixels[count++] = image.getRGB(row, col);
            }

        }
        return pixels;
    }

    /**
     * Преобразовать массив пикселей в изображение
     * @param pixels - входной массив пикселей
     * @param height - высота итогового изображения
     * @param width = ширина итогового изображения
     * @return
     */
    private BufferedImage createImageFromPixels(int[] pixels, int height, int width) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int row = 0, count = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                bufferedImage.setRGB(row, col, pixels[count++]);
            }

        }
        return bufferedImage;
    }


    /**
     * удалить временный файл, созданный отфильтрованным объектом
     */
    public void deleteTemporaryFile() {
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

    private File saveBufferedImage(BufferedImage bi) {

        File file = new File(savePath);
        try {
            ImageIO.write(bi, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


}
