package sample.filtration;

import sample.workFiles.FileAdapter;

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
//    private double[][] GAUSS = {
//            {0.000789, 0.006581, 0.013347, 0.006581, 0.000789},
//            {0.006581, 0.054901, 0.111345, 0.054901, 0.006581},
//            {0.013347, 0.111345, 0.225821, 0.111345, 0.013347},
//            {0.006581, 0.054901, 0.111345, 0.054901, 0.006581},
//            {0.000789, 0.006581, 0.013347, 0.006581, 0.000789}
//    };

    private double[][] GAUSS = {
            {0.111, 0.111, 0.111},
            {0.111, 0.111, 0.111},
            {0.111, 0.111, 0.111}
    };

    public Filtration(BufferedImage inputImage) {
        this.inputImage = inputImage;
        pixel = new Pixel(inputImage);
    }

    private void checkConvolution(double[][] convolution) {
        double sum = 0;
        for (int i = 0; i < convolution.length; i++) {
            for (int j = 0; j < convolution.length; j++)
                sum += convolution[i][j];
        }

        System.out.println("Свертка равна " + sum);
        if(sum == 0){

        }
        else {
            //нормируем
        }
    }

    private byte[][] filtration(double[][] convolution) {
        //если не была передана свертка, используем тестовую
        if (convolution == null)
            convolution = GAUSS;

        //матрица пикселей входного изображения
        byte[] pixels = pixel.getPixels();

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

        for(int i = 0; i < pixels.length; i++){

        }

        return newPixels;
    }

    private byte[][] trans(int[][] date){
        byte[][] newMas = new byte[date.length][date.length];
        for(int i = 0; i < date.length; i++){
            for(int j = 0; j < date.length; j++){
                newMas[i][j] = (byte) date[i][j];
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
        byte[][] filteredImagePixels = filtration(convolution);
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
    private byte getValueAnchor(byte[][] w, double[][] convolution) {
        //умножаем окно на свертку, складываем результат, записываем в пиксель
        byte sum = 0;
        for (int k = 0; k < convolution.length; k++) {
            for (int n = 0; n < convolution.length; n++) {
                sum += w[k][n] * convolution[k][n];
            }
        }

        byte resultValuePixel = (byte) (sum * (1 / div));
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
    private byte[][] getWindow(byte[] pixels, int i, int j, int kernelSize) {
        byte[][] w = new byte[kernelSize][kernelSize];

        int step = kernelSize / 2;

//        if (i < step) i++;
//        if(j < step) j++;

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
