package petriNet;

import java.util.ArrayList;

public class MathOperator {

    /*
     *
     * FIXME Hya metodos donde si las prioridades son 0 o muy grandes no funciuona
     *  Arrgelar o documentar bien para no hacer cagadas
     *
     *
     */

    public MathOperator(){
        super();
    }

    public static int[] addVector(int[] vector1, int[] vector2){
    //public int[] addVector(int[] vector1, int[] vector2){
        int size = vector1.length;
        int[] result = new int[size];

        for(int i = 0; i < size; i++)
            result[i] = vector1[i] + vector2[i];

        return result;
    }

    public static int[] innerProdVector(int[] vector1, int[] vector2){
    //public int[] innerProdVector(int[] vector1, int[] vector2){

        int size = vector1.length;
        int[] result = new int[size];

        for(int i = 0; i < size; i++)
            result[i] = vector1[i] * vector2[i];

        return result;
    }

    public static int[] andVector(int[] vector1, int[] vector2){
    //public int[] andVector(int[] vector1, int[] vector2){
        int size = vector1.length;
        int[] result = new int[size];

        for(int i = 0; i < size; i++)
            result[i] = vector1[i] & vector2[i];

        return result;
    }

    public static int[][] matrixAdd(int[][] vector1, int[][] vector2){
    //public int[][] matrixAdd(int[][] vector1, int[][] vector2){


        int size = vector1[0].length;
        int[][] result = new int[size][size];

        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
            result[i][j] = vector1[i][j] + vector2[i][j];

        return result;
    }

    public static int[][] matrixProd(int[][] vector1, int[][] vector2){
    //public int[][] matrixProd(int[][] vector1, int[][] vector2){

        int size = vector1[0].length;


        int[][] result = new int[size][size];

        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                result[i][j] = vector1[i][j] * vector2[i][j];

        return result;
    }

    public static int[] vectmatProd(int [][] matrix, int[] vector){
    //public int[] vectmatProd(int [][] matrix, int[] vector){
        int matrix_size = matrix[0].length;
        int vector_size = vector.length;
        int [] result = new int[matrix_size];

        for(int i = 0; i < matrix_size; i++)
            for(int j = 0; j < vector_size; j++)
                result[i] += matrix[i][j] * vector[j];

        return result;
    }

    public static int getMaxIndex(int[] vector){
    //public int getMaxIndex(int[] vector){
        int size = vector.length;
        int max_index = 0;
        int max_value = 0;

        for(int i = 0; i < size; i++){
            if(vector[i] > max_value){
                max_value = vector[i];
                max_index = i;
            }
        }
        return max_index;
    }

    public static int getMinIndex(int[] vector){
    //public int getMinIndex(int[] vector){
        int size = vector.length;
        int min_index = 0;
        int min_value = vector[0];// Se supone el primero como mas alto
                                  // de esta forma no se rompe con ningun tipo de prioridad
        for(int i = 0; i < size; i++){
            if(vector[i] < min_value){
                min_value = vector[i];
                min_index = i;
            }
        }
        return min_index;
    }

    public static int getMaxValue(int[] vector){
    //public int getMaxValue(int[] vector){
        int size = vector.length;
        int max_value = 0;

        for(int i = 0; i < size; i++){
            if(vector[i] > max_value){
                max_value = vector[i];
            }
        }
        return max_value;
    }

    /*
        Si hay dos o mas elementos iguales al amyor valor retorna una lista con sus posiciones
    */
    public static int [] getMaxIndexVect(int[] vector){
    //public int [] getMaxIndexVect(int[] vector){
        ArrayList<Integer> maxIndexes = new ArrayList<Integer>();
        int size = vector.length;
        int max_value = vector[0];
        for (int value : vector) {
            if (value > max_value)
                max_value = value;
        }
        for (int i = 0; i < size; i++){
            if (vector[i] == max_value)
                maxIndexes.add(i);
        }
        int [] retval = new int[maxIndexes.size()];
        for (int i=0; i< maxIndexes.size(); i++)
            retval[i] = maxIndexes.get(i);
        return retval;
    }

    public static int getMinValue(int[] vector){
    //public int getMinValue(int[] vector){
        int size = vector.length;
        int min_value = vector[0]; //lo mismo que ne getMinIndex
        for (int value : vector) {
            if (value < min_value) {
                min_value = value;
            }
        }
        return min_value;
    }
}
