package sample.filtration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * класс, получающий пиксели из всего изображения
 */
public class Pixel {
    //обрабатываемое изображение
    private BufferedImage image;
    private int width;
    private int height;
    //матрица пикселей изображения
    private byte[][] pixels;

    public Pixel(BufferedImage image) {
        this.image = image;
        width = this.image.getWidth();
        height = this.image.getHeight();
        pixels = new byte[height][width];
        createPixelArray();
    }

    public byte[][] getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    /**
     * заполнить матрицу пикселей
     */
    public void createPixelArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        byte[] byteArray = new byte[width * height];
        try {
            ImageIO.write(image, "jpg", baos);
            baos.flush();
            byte[] byteArray = baos.toByteArray();
            baos.close();

            System.out.println("Длинна " + byteArray.length + " и " + pixels.length);
            for (int row = 0, count = 0; row < height; row++) {
                for (int col = 0; col < width; col++, count++) {
//                pixels[row][col] = image.getRGB(row, col);
                    pixels[row][col] = byteArray[count];
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
