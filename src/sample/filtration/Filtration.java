package sample.filtration;

import sample.workFiles.FileAdapter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Filtration {
    //матрица свертки
    private double[][] convolution;
    //входное изображение
    private BufferedImage inputImage;
    //класс для получения информации о исходном изображении
    private Pixel pixel;
    //матрица пикселей входного изображения
    private Color[][] pixels;
    //коэффициент нормирования, чтобы средняя интенсивность осталась неизменной
    //div = 1, тк матрица нормированная (это для Гауссовского)
    //div = 0 выставлять нельзя
    //обычно div = сумме всех элементов матрицы свертки
    private double div = 1;
    //коэффициент, используемый вместе с div для нормализации
    private double offset = 0;


    //тестовая матрица заполненная по Гауссовскому распределению
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
        pixels = pixel.getPixles();
    }

    private Color[] filtration(double[][] convolution) {
        //если не была передана свертка, используем тестовую
        if (convolution == null)
            convolution = GAUSS;

        //получить размер свертки и окна
        int size = convolution.length;
        //ширина входного изображения
        int weidth = 0;
        //высота входного изображения
        int heigth = 0;

        if (pixel != null) {
            weidth = pixel.getWeidth();
            heigth = pixel.getHeigth();
        }

        int newPixelSize = pixel.getHeigth() * pixel.getWeidth();
        Color[] newPixels = new Color[newPixelSize];

        int count = 0;

        for (int i = 1; i < heigth -1 ; i++) {                                          //!!!!! СДЕЛАТЬ ЧТО-НИБУДЬ С КРАЯМИ
            for (int j = 1; j < weidth -1 ; j++) {
                //окно - маленькая часть изображения для применения фильтра
                double[][] w = getWindow(i, j, size);

                //получить изменненое значение пикселя
                newPixels[count++] = new Color(getValueAnchor(w, convolution));
            }
        }

        return newPixels;
    }

    public File getFilteredImage(double[][] convolution) {
        //получить отфильтрованное значение пикселей
        Color[] filteredPixels = filtration(convolution);

        FileAdapter adapter = new FileAdapter();
        File file = adapter.getFile(pixel.getPixles());


//        int s = filteredPixels.length;
//
//        byte[] f = new byte[s];
//
//        for (int i = 0; i < 13900; i++){                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 13900
//            try {
//              f[i] = (byte) filteredPixels[i].getRGB();
//            }catch (NullPointerException ex){
//                System.out.println("I = " + i);
//            }
//        }
//
//        try {
//            Path path = Paths.get("C:\\Users\\evdok\\Downloads");
//            Files.write(path, f);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        BufferedImage image = new BufferedImage(120, 120, BufferedImage.TYPE_INT_ARGB);
//
//        WritableRaster raster = image.getRaster();
//        raster.setPixels(0, 0, 120, 120, m);

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
     *
     * @param i          - строка, с которой начинать получать элементы окна
     * @param j          - столбец, с которого начинать получать элементы окна
     * @param kernelSize - размер окна
     * @return w - вернуть окно
     */
    private double[][] getWindow(int i, int j, int kernelSize) {
        double[][] w = new double[kernelSize][kernelSize];

        int step = kernelSize / 2;

        //получить пиксели для окна
        for (int k = 0, row = i - step; k < kernelSize; k++) {
            for (int n = 0, col = j - 1; n < kernelSize; n++) {
                w[k][n] = pixels[row][col++].getRGB();
//                System.out.println(w[k][n]);
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
