public class mathOperator {

    public mathOperator(){
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
}
