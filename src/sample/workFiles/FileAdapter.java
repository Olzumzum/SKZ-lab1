package sample.workFiles;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class FileAdapter {

    private final String SAVE_PATH = System.getProperty("user.dir") +
            System.getProperty("file.separator") + "src" + System.getProperty("file.separator") +
            "res" + System.getProperty("file.separator") + "temporary.jpg";

    public File getFile(double[][] data) {
        double[] pixels = createPixelArray(data, data.length, data.length);
        BufferedImage bufferedImage = createImageFromPixels(pixels, data.length, data.length);

        saveBufferedImage(bufferedImage);

        return new File(SAVE_PATH);
    }


    /**
     * создать массив пикселей на основе
     * полученного изображения
     *
     * @param image входное изображение
     * @return массив пикселей, соответствующий входному
     * изобрашению
     */
    private double[] createPixelArray(double[][] image, int width, int height) {

        double[] pixels = new double[height * width];

        for (int row = 0, count = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                pixels[count++] = image[row][col];
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
    private BufferedImage createImageFromPixels(double[] pixels, int height, int width) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int row = 0, count = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                bufferedImage.setRGB(row, col, (int) pixels[count++]);
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

        File file = new File(SAVE_PATH);
        try {
            ImageIO.write(bi, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


}
