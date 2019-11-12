package petriNet;
public class MathOperator {
//chupalapija
    public MathOperator(){
        super();
    }

    public int[] addVector(int size, int[] vector1, int[] vector2){

        int[] result = new int[size];

        for(int i = 0; i < size; i++)
            result[i] = vector1[i] + vector2[i];

        return result;
    }

    public int[] innerProdVector(int size, int[] vector1, int[] vector2){

        int[] result = new int[size];

        for(int i = 0; i < size; i++)
            result[i] = vector1[i] * vector2[i];

        return result;
    }

    public int[] andVector(int size, int[] vector1, int[] vector2){

        int[] result = new int[size];

        for(int i = 0; i < size; i++)
            result[i] = vector1[i] & vector2[i];

        return result;
    }

    public int[][] matrixAdd(int size, int[][] vector1, int[][] vector2){

        int[][] result = new int[size][size];

        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
            result[i][j] = vector1[i][j] + vector2[i][j];

        return result;
    }

    public int[][] matrixProd(int size, int[][] vector1, int[][] vector2){

        int[][] result = new int[size][size];

        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                result[i][j] = vector1[i][j] * vector2[i][j];

        return result;
    }

    public int[] vectmatProd(int matrix_size, int vector_size, int [][] matrix, int[] vector){

        int [] result = new int[vector_size];

        for(int i = 0; i < vector_size; i++)
            for(int j = 0; j < matrix_size; j++)
                result[i] += matrix[i][j] * vector[j];

        return result;
    }
}