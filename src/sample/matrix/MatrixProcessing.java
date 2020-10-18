package sample.matrix;

import javafx.scene.control.TextField;

import java.util.HashSet;
import java.util.Set;

public class MatrixProcessing {
    /**
     * проверка введеных значений в матрицу
     *
     * @param field - поле, куда введено значение
     * @return - флаг, является ли значение поля числовым
     */
    private static boolean checkMatrix(TextField field) {
        char[] value = field.getText().toCharArray();
        for (char v : value) {
            if (!Character.isDigit(v)) {
                if (v != '.')
                    return false;
            }
        }
        return true;
    }

    /**
     * получить данные с экрана из таблицы фильтров
     */
    public static double[][] getFilterMatrix(Set<TextField> setField) {

        double[][] matrixFilter = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String name = "value" + i + j;
                for (TextField el : setField) {
                    if (name.compareTo(el.getId()) == 0) {
                        if (!checkMatrix(el))
                            throw new NumberFormatException("Ошибка ввода данных в таблицу");

                        matrixFilter[i][j] = Double.parseDouble(el.getText());
                    }
                }

            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println(matrixFilter[i][j]);
            }
        }

        return matrixFilter;
    }

}
