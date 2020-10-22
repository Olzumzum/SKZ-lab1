package sample.filtration;

import sample.workFiles.FileAdapter;

import java.awt.image.BufferedImage;
import java.io.File;

public class Filtration {
    //класс для получения информации о исходном изображении
    private final Pixel pixel;
    //коэффициент нормирования, чтобы средняя интенсивность осталась неизменной
    //div = 1, тк матрица нормированная (это для Гауссовского)
    //div = 0 выставлять нельзя
    //обычно div = сумме всех элементов матрицы свертки (Гаусс)
    private final double div = 1;



//    тестовая матрица заполненная по Гауссовскому распределению
//    private double[][] GAUSS = {
//            {0.000789, 0.006581, 0.013347, 0.006581, 0.000789},
//            {0.006581, 0.054901, 0.111345, 0.054901, 0.006581},
//            {0.013347, 0.111345, 0.225821, 0.111345, 0.013347},
//            {0.006581, 0.054901, 0.111345, 0.054901, 0.006581},
//            {0.000789, 0.006581, 0.013347, 0.006581, 0.000789}
//    };

    private final double[][] GAUSS = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
    };

    public Filtration(BufferedImage inputImage) {
        pixel = new Pixel(inputImage);
    }


    private double[][] filtration(double[][] convolution) {
        //если не была передана свертка, используем тестовую
        if (convolution == null)
            convolution = GAUSS;

        //матрица пикселей входного изображения
        int[][] pixels = pixel.getPixels();

        //получить размер свертки и окна
        int sizeConv = convolution.length;
        //ширина входного изображения
        int weidth = 0;
        //высота входного изображения
        int heigth = 0;

        if (pixel != null) {
            weidth = pixel.getWidth();
            heigth = pixel.getHeight();
        }

        //матрица для отфильтрованного входного изображения
        double[][] newPixels = new double[weidth][heigth];

        for (int i = 0; i < heigth; i++) {
            for (int j = 0; j < weidth; j++) {
                //если это не пиксели у грани
                if ((i - (sizeConv / 2) >= 0 && i + (sizeConv / 2) <= pixels.length) &&
                        (j - (sizeConv / 2) >= 0 && j + (sizeConv / 2) <= pixels.length)) {
                    //окно - маленькая часть изображения для применения фильтра
                    double[][] w = getWindow(pixels, i, j, sizeConv);

                    //получить изменненое значение пикселя
                    newPixels[i][j] = getValueAnchor(w, convolution);
                } else {
                    newPixels[i][j] = pixels[i][j];
                }
            }
        }
        return newPixels;
    }

    /**
     * вернуть отфильтрованное изображение
     *
     * @param convolution
     * @return
     */
    public File getFilteredImage(double[][] convolution) {
        //получить отфильтрованное значение пикселей
        double[][] filteredImagePixels = filtration(convolution);
        //передать его на обработку и сохранение
        File file = new FileAdapter().getFile(filteredImagePixels);
        return file;
    }

    /**
     * Вернуть значение якоря
     * Якорь - центральный пиксель, для которого высчитывается значение
     *
     * @param w           - окно
     * @param convolution - матрица свертки
     * @return значение пикселя
     */
    private double getValueAnchor(double[][] w, double[][] convolution) {
        //умножаем окно на свертку, складываем результат, записываем в пиксель
        double sum = 0;
        for (int k = 0; k < convolution.length; k++) {
            for (int n = 0; n < convolution.length; n++) {
                sum += w[k][n] * convolution[k][n];
            }
        }

        double resultValuePixel = (sum * (1 / div));
        return resultValuePixel;
    }

    /**
     * вернуть окно для умножения на свертку
     *
     * @param pixels     - массив пикселей входного изображения
     * @param i          - строка, с которой начинать получать элементы окна
     * @param j          - столбец, с которого начинать получать элементы окна
     * @param kernelSize - размер окна
     * @return w - вернуть окно
     */
    private double[][] getWindow(int[][] pixels, int i, int j, int kernelSize) {
        double[][] w = new double[kernelSize][kernelSize];

        int step = kernelSize / 2;

        //получить пиксели для окна
        for (int k = 0, row = i - step; k < kernelSize && row < pixels.length; k++) {
            for (int n = 0, col = j - 1; n < kernelSize && col < pixels.length; n++) {
                w[k][n] = pixels[row][col++];
            }
        }

        return w;
    }


}
