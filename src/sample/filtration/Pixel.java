package sample.filtration;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * класс, получающий пиксели из всего изображения
 */
public class Pixel {
    //обрабатываемое изображение
    private BufferedImage image;
    private int weidth;
    private int heigth;
    //матрица пикселей изображения
    private Color[][] pixles;

    public Pixel(BufferedImage image){
        this.image = image;
        weidth = this.image.getWidth();
        heigth = this.image.getHeight();
        pixles = new Color[heigth][weidth];
        buildMatrix();
    }

    public Color[][] getPixles(){
        return pixles;
    }

    public int getWeidth(){
        return weidth;
    }

    public int getHeigth(){
        return heigth;
    }


    /**
     * заполнить матрицу пикселей
     */
    public void buildMatrix(){
        for(int i = 0; i < heigth; i++){
            for(int j = 0; j < weidth; j++){
                Color c = new Color(image.getRGB(j, i));
                pixles[i][j] = new Color(image.getRGB(i, j));
            }
        }

//        for(int i = 0; i < heigth; i++) {
//            for (int j = 0; j < weidth; j++) {
//                System.out.println("S.No: "  +
//                        " Red: " + pixles[i][j].getRed() +
//                        "  Green: " + pixles[i][j].getGreen() +
//                        " Blue: " + pixles[i][j].getBlue());
//            }
//        }
    }
}
