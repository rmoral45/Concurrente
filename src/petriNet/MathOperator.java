package petriNet;
public class MathOperator {
//chupalapija
    public MathOperator(){
        super();
    }

    public static int[] addVector(int size, int[] vector1, int[] vector2){

        int[] result = new int[size];

        for(int i = 0; i < size; i++)
            result[i] = vector1[i] + vector2[i];

        return result;
    }

    public static int[] innerProdVector(int size, int[] vector1, int[] vector2){

        int[] result = new int[size];

        for(int i = 0; i < size; i++)
            result[i] = vector1[i] * vector2[i];

        return result;
    }

    public static int[] andVector(int size, int[] vector1, int[] vector2){

        int[] result = new int[size];

        for(int i = 0; i < size; i++)
            result[i] = vector1[i] & vector2[i];

        return result;
    }

    public static int[][] matrixAdd(int size, int[][] vector1, int[][] vector2){

        int[][] result = new int[size][size];

        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
            result[i][j] = vector1[i][j] + vector2[i][j];

        return result;
    }

    public static int[][] matrixProd(int size, int[][] vector1, int[][] vector2){

        int[][] result = new int[size][size];

        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                result[i][j] = vector1[i][j] * vector2[i][j];

        return result;
    }

    public static int[] vectmatProd(int matrix_size, int vector_size, int [][] matrix, int[] vector){

        int [] result = new int[matrix_size];

        for(int i = 0; i < matrix_size; i++)
            for(int j = 0; j < vector_size; j++)
                result[i] += matrix[i][j] * vector[j];

        return result;
    }

    public static int getMaxIndex(int[] vector){

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

        int size = vector.length;
        int min_index = 0;
        int min_value = 1000000;

        for(int i = 0; i < size; i++){
            if(vector[i] < min_value){
                min_value = vector[i];
                min_index = i;
            }
        }
        return min_index;
    }

    public static int getMaxValue(int[] vector){

        int size = vector.length;
        int max_value = 0;

        for(int i = 0; i < size; i++){
            if(vector[i] > max_value){
                max_value = vector[i];
            }
        }
        return max_value;
    }

    public static int getMinValue(int[] vector){

        int size = vector.length;
        int min_value = 1000000;

        for(int i = 0; i < size; i++){
            if(vector[i] < min_value){
                min_value = vector[i];
            }
        }
        return min_value;
    }

}
