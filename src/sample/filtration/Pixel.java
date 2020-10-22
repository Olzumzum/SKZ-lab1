package sample.filtration;

import java.awt.image.BufferedImage;

/**
 * класс, получающий пиксели из всего изображения
 */
public class Pixel {
    //обрабатываемое изображение
    private final BufferedImage image;
    private final int width;
    private final int height;
    //матрица пикселей изображения
    private final int[][] pixels;

    public Pixel(BufferedImage image){
        this.image = image;
        width = this.image.getWidth();
        height = this.image.getHeight();
        pixels = new int[height][width];
        createPixelArray();
    }

    public int[][] getPixels(){
        return pixels;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }


    /**
     * заполнить матрицу пикселей
     */
    public void createPixelArray(){
        for (int row = 0, count = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                pixels[row][col] = image.getRGB(row, col);
            }
        }

    }
}
