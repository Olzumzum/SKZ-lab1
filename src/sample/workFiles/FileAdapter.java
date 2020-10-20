package sample.workFiles;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileAdapter {

    public File getFile(Color[][] data) {
        String savePath = System.getProperty("user.dir") +
                System.getProperty("file.separator") + "src" + System.getProperty("file.separator") +
                "res" + System.getProperty("file.separator") + "txt.jpeg";


        byte[]bDate = new byte[data.length * data.length];
        for (int i = 0, count = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++){
                bDate[count++] = (byte) data[i][j].getRGB();
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(savePath);
            fos.write(bDate);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(savePath);
    }

    /**
     * удалить временный файл, созданный отфильтрованным объектом
     */
    public void deleteTemporaryFile(){
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




    }
