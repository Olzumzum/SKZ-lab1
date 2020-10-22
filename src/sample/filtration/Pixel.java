package sample.filtration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * класс, получающий пиксели из всего изображения
 */
public class Pixel {
    //обрабатываемое изображение
    private BufferedImage image;
    private int width;
    private int height;
    //матрица пикселей изображения
//    private byte[][] pixels;

    public Pixel(BufferedImage image) {
        this.image = image;
        width = this.image.getWidth();
        height = this.image.getHeight();
//        pixels = new byte[height][width];
//        createPixelArray();
    }

    public byte[] getPixels() {
        return createPixelArray();
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
    public byte[] createPixelArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] byteArray = new byte[0];
        try {
            ImageIO.write(image, "jpg", baos);
            baos.flush();
            byteArray = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    private int nod(int value){
        List<Integer> del = new ArrayList<>();
        for(int i = 1; i < value; i++){
            if ((value % i) == 0)
                del.add(i);
        }
        for(int i = del.size()-1; i >= 0; i--){
            int el = del.get(i);
            if(el*el == value) return el;
            if((el*el + (el-1)) > value && (el*el) < value) return el;
        }
        return 0;
    }
}
