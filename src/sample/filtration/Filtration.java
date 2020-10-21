package sample.filtration;

import sample.workFiles.FileAdapter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Filtration {
    //матрица свертки
    private double[][] convolution;
    //входное изображение
    private BufferedImage inputImage;
    //класс для получения информации о исходном изображении
    private Pixel pixel;
    //коэффициент нормирования, чтобы средняя интенсивность осталась неизменной
    //div = 1, тк матрица нормированная (это для Гауссовского)
    //div = 0 выставлять нельзя
    //обычно div = сумме всех элементов матрицы свертки
    private double div = 1;
    //коэффициент, используемый вместе с div для нормализации
    private double offset = 0;


//    тестовая матрица заполненная по Гауссовскому распределению
    private double[][] GAUSS = {
            {0.000789, 0.006581, 0.013347, 0.006581, 0.000789},
            {0.006581, 0.054901, 0.111345, 0.054901, 0.006581},
            {0.013347, 0.111345, 0.225821, 0.111345, 0.013347},
            {0.006581, 0.054901, 0.111345, 0.054901, 0.006581},
            {0.000789, 0.006581, 0.013347, 0.006581, 0.000789}
    };

    private boolean checkConvolution(double[][] convolution){

        for(int i = 0; i < convolution.length; i++){

        }
    }

//    private double[][] GAUSS = {
//            {0.111, 0.111, 0.111},
//            {0.111, 0.111, 0.111},
//            {0.111, 0.111, 0.111}
//    };

    public Filtration(BufferedImage inputImage) {
        this.inputImage = inputImage;
        pixel = new Pixel(inputImage);
    }

    private int[][] filtration(double[][] convolution) {
        //если не была передана свертка, используем тестовую
        if (convolution == null)
            convolution = GAUSS;

        //матрица пикселей входного изображения
        int[][] pixels = pixel.getPixels();

        //получить размер свертки и окна
        int size = convolution.length;
        //ширина входного изображения
        int width = 0;
        //высота входного изображения
        int height = 0;

        if (pixel != null) {
            height = pixel.getWidth();
            width = pixel.getHeight();
        }

        //матрица для отфильтрованного входного изображения
        byte[] inputBytes = b(pixel.getPixels());
        byte[] outputBytes = new byte[inputBytes.length];

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                double rSum = 0, gSum = 0, bSum = 0, kSum = 0;

                for (int i = 0; i < convolution.length; i++)
                {
                    for (int j = 0; j < convolution.length; j++)
                    {
                        int pixelPosX = x + (i - (convolution.length / 2));
                        int pixelPosY = y + (j - (convolution.length / 2));
                        if ((pixelPosX < 0) ||
                                (pixelPosX >= width) ||
                                (pixelPosY < 0) ||
                                (pixelPosY >= height)) continue;

                        byte r = inputBytes[3 * (width * pixelPosY + pixelPosX) + 0];
                        byte g = inputBytes[3 * (width * pixelPosY + pixelPosX) + 1];
                        byte b = inputBytes[3 * (width * pixelPosY + pixelPosX) + 2];

                        double kernelVal = convolution[i][j];

                        rSum += r * kernelVal;
                        gSum += g * kernelVal;
                        bSum += b * kernelVal;

                        kSum += kernelVal;
                    }
                }

                if (kSum <= 0) kSum = 1;

                //Контролируем переполнения переменных
                rSum /= kSum;
                if (rSum < 0) rSum = 0;
                if (rSum > 255) rSum = 255;

                gSum /= kSum;
                if (gSum < 0) gSum = 0;
                if (gSum > 255) gSum = 255;

                bSum /= kSum;
                if (bSum < 0) bSum = 0;
                if (bSum > 255) bSum = 255;

                //Записываем значения в результирующее изображение
                outputBytes[3 * (width * y + x) + 0] = (byte)rSum;
                outputBytes[3 * (width * y + x) + 1] = (byte)gSum;
                outputBytes[3 * (width * y + x) + 2] = (byte)bSum;
            }
        }

        int[][] newPixels = new int[height][width];
        for(int i = 0, count = 0; i < height; i++){
            for (int j = 0; j< width; j++){
                newPixels[i][j] = outputBytes[count];
            }
        }
        return newPixels;
    }

    byte[] b(int[][] mas){
        byte[] newMas = new byte[mas.length * mas.length];

        for(int i = 0, count = 0; i < mas.length; i++){
            for(int j = 0; j < mas.length; j++, count++){
                newMas[count] = (byte) mas[i][j];
            }
        }
        return newMas;
    }

    /**
     * вернуть отфильтрованное изображение
     * @param convolution
     * @return
     */
    public File getFilteredImage(double[][] convolution) {
        //получить отфильтрованное значение пикселей
        int[][] filteredImagePixels = filtration(convolution);
        //передать его на обработку и сохранение
        File file = new FileAdapter().getFile(filteredImagePixels);
        return file;
    }

    /**
     * Вернуть значение якоря
     * Якорь - центральный пиксель, для которого высчитывается значение
     * @param w           - окно
     * @param convolution - матрица свертки
     * @return значение пикселя
     */
    private int getValueAnchor(double[][] w, double[][] convolution) {
        //умножаем окно на свертку, складываем результат, записываем в пиксель
        double sum = 0;
        for (int k = 0; k < convolution.length; k++) {
            for (int n = 0; n < convolution.length; n++) {
                sum += w[k][n] * convolution[k][n];
            }
        }

        int resultValuePixel = (int) (sum * (1 / div));
        return resultValuePixel;
    }

    /**
     * вернуть окно для умножения на свертку
     *@param pixels - массив пикселей входного изображения
     * @param i          - строка, с которой начинать получать элементы окна
     * @param j          - столбец, с которого начинать получать элементы окна
     * @param kernelSize - размер окна
     * @return w - вернуть окно
     */
    private double[][] getWindow(int[][] pixels, int i, int j, int kernelSize) {
        double[][] w = new double[kernelSize][kernelSize];

        int step = kernelSize / 2;

        if (i < step) i++;
        if(j < step) j++;

        //получить пиксели для окна
        for (int k = 0, row = i - step; k < kernelSize && row < pixels.length; k++) {
            for (int n = 0, col = j - 1; n < kernelSize && col < pixels.length; n++) {
                w[k][n] = pixels[row][col++];
            }
        }

        return w;
    }


    /**
     * установить матрицу свертки
     *
     * @param convolution - матрица свертки
     */
    public void setConvolution(double[][] convolution) {
        this.convolution = convolution;
    }


}
