package petriNet;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;

public class MathOperator {

    public MathOperator(){
        super();
    }


    /**
     * addVector - devuelve la suma de dos vectores
     * @param vector1 primer operando de la suma
     * @param vector2 segundo operando de la suma
     * @return devuelve el vector que resulta de la suma
     */
    public static int[] addVector(int[] vector1, int[] vector2) throws InvalidAlgorithmParameterException{
    //public int[] addVector(int[] vector1, int[] vector2){

        if(vector1.length != vector2.length)
            throw new InvalidAlgorithmParameterException("Fallo en funcion addVector");

        int size = vector1.length;
        int[] result = new int[size];

        for(int i = 0; i < size; i++)
            result[i] = vector1[i] + vector2[i];

        return result;
    }

    /**
     * innerPordVector - realiza el producto punto entre dos vectores
     * @param vector1 primer operando del producto punto
     * @param vector2 segundo operando del producto punto
     * @return devuelve el vector que resulta del producto punto
     */
    public static int[] innerProdVector(int[] vector1, int[] vector2) throws InvalidAlgorithmParameterException{
    //public int[] innerProdVector(int[] vector1, int[] vector2){

        if(vector1.length != vector2.length){
            throw new InvalidAlgorithmParameterException("Fallo en funcion innerProdVector");
        }


        int size = vector1.length;
        int[] result = new int[size];

        for(int i = 0; i < size; i++)
            result[i] = vector1[i] * vector2[i];

        return result;
    }

    /**
     * andVector - realiza la and elemento a elemento entre dos vectores
     * @param vector1 primer operando de la operacion and
     * @param vector2 segundo operando de la operacion and
     * @return retorna el resultado de la and entre ambos vectores
     */
    public static int[] andVector(int[] vector1, int[] vector2) throws InvalidAlgorithmParameterException {
    //public int[] andVector(int[] vector1, int[] vector2){

        if(vector1.length != vector2.length){
            throw new InvalidAlgorithmParameterException("Fallo en funcion andVector");
        }

        int size = vector1.length;
        int[] result = new int[size];

        for(int i = 0; i < size; i++)
            result[i] = vector1[i] & vector2[i];

        return result;
    }

    /**
     * matrixAdd - realiza la suma entre dos matrices
     * @param vector1 primer operando de la suma
     * @param vector2 segundo operando de la suma
     * @return retormna una matriz que resulta de la suma entre ambos parametros
     */
    public static int[][] matrixAdd(int[][] vector1, int[][] vector2) throws InvalidAlgorithmParameterException{
    //public int[][] matrixAdd(int[][] vector1, int[][] vector2){

        if(vector1.length != vector1.length)
            throw new InvalidAlgorithmParameterException("Fallo en funcion matrixAdd - tamaño de filas distinto");

        for(int i = 0; i < vector1.length; i++)
            if(vector1[i].length != vector2[i].length)
                throw new InvalidAlgorithmParameterException("Fallo en funcion matrixAdd - " +
                                                            "el tamaño de la columna" + i + "es distinto");

        int size1 = vector1.length;
        int size2 = vector1[0].length;
        int[][] result = new int[size1][size2];

        for(int i = 0; i < size1; i++)
            for(int j = 0; j < size2; j++)
                result[i][j] = vector1[i][j] + vector2[i][j];

        return result;
    }

    /**
     * matrixProd - realiza el producto entre dos matrices
     * @param vector1 primer operando del producto
     * @param vector2 segundo operando del producto
     * @return retorna una matriz entre el producto de vector1 y vector2
     */
    public static int[][] matrixProd(int[][] vector1, int[][] vector2) throws InvalidAlgorithmParameterException{
    //public int[][] matrixProd(int[][] vector1, int[][] vector2){

        if(vector1.length != vector2.length)
            throw new InvalidAlgorithmParameterException("Fallo en matrixProd - tamaño de filas distinto");

        for(int i = 0; i < vector1.length; i++)
            if(vector1[i].length != vector2[i].length)
                throw new InvalidAlgorithmParameterException("Fallo en funcion matrixProd - " +
                                                            "el tamaño de la columna" + i + "es distinto");


        int size1 = vector1.length;
        int size2 = vector1[0].length;

        int[][] result = new int[size1][size2];

        for(int i = 0; i < size1; i++)
            for(int j = 0; j < size2; j++)
                result[i][j] = vector1[i][j] * vector2[i][j];

        return result;
    }

    /**
     * vectmatProd - realiza el producto entre una matriz y un vector
     * @param matrix
     * @param vector
     * @return retorna un vector resultado del producto entre la matriz y el vector pasados como parametro
     */
    public static int[] vectmatProd(int [][] matrix, int[] vector) throws InvalidAlgorithmParameterException{
    //public int[] vectmatProd(int [][] matrix, int[] vector){

        for(int j = 0; j < matrix.length; j++)
            if(matrix[j].length != vector.length)
                throw new InvalidAlgorithmParameterException("La fila "+j+" es de distino tamaño que el vector");

        int matrix_size = matrix.length;
        int vector_size = vector.length;
        int [] result = new int[matrix_size];

        for(int i = 0; i < matrix_size; i++)
            for(int j = 0; j < vector_size; j++)
                result[i] += matrix[i][j] * vector[j];

        return result;
    }

    /**
     * getMaxIndex - encuentra el indice del mayor elemento del vector.
     * Supone como mayor al primer elemento del vector
     * @param vector vector de enteros al que se le realizara la busqueda
     * @return indice del mayor elemento. Si se le pasa como parametro [0,10,6,23], devolvera 3
     */
    public static int getMaxIndex(int[] vector){
    //public int getMaxIndex(int[] vector){
        int size = vector.length;
        int max_index = 0;
        int max_value = vector[0];

        for(int i = 0; i < size; i++){
            if(vector[i] > max_value){
                max_value = vector[i];
                max_index = i;
            }
        }
        return max_index;
    }

    /**
     * getMixIndex - encuentra el indice del menor elemento del vector.
     * Supone como menor al primer elemento del vector
     * @param vector vector de enteros al que se le realizara la busqueda
     * @return indice del menorelemento. Si se le pasa como parametro [0,10,6,23], devolvera 0
     */
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

    /**
     * getMaxValue - encuentra el mayor elemento del vector.
     * Supone como mayor al primer elemento del vector
     * @param vector vector de enteros al que se le realizara la busqueda
     * @return El mayor elemento. Si se le pasa como parametro [0,10,6,23], devolvera 3
     */
    public static int getMaxValue(int[] vector){
    //public int getMaxValue(int[] vector){
        int size = vector.length;
        int max_value = vector[0];

        for(int i = 0; i < size; i++){
            if(vector[i] > max_value){
                max_value = vector[i];
            }
        }
        return max_value;
    }

    /**
     * Busca los indices en donde se encuentran los mayores elementos. Puede haber mas de un maxi,mo
     * @param vector
     * @return retorna un vector con las posiciones de los maximos. Por ejemplo si le pasamos
     *         [0,10,5,2,6,1,10] retorna [1,6]
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

    /**
     * getMaxValue - encuentra el menor elemento del vector.
     * Supone como menor al primer elemento del vector
     * @param vector vector de enteros al que se le realizara la busqueda
     * @return El menor elemento. Si se le pasa como parametro [1,10,6,23], devolvera 1
     */
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

    /**
     * Metodo para saber si existen numeros negativos en un vector
     * Corta la ejecucion al encontrar el primer numero negativo
     * @param vector vector de enteros sobre el que se realiza la busqueda
     * @return true: si existe al menos un numero negativo
     *         false: en caso contrario
     */
    public static boolean HasNegative(int[] vector){

        int size = vector.length;
        boolean isNegative = false;

        for(int i = 0; i < size; i++) {
            if (vector[i] < 0) {
                isNegative = true;
                break;
            } else
                isNegative = false;

        }
        
        return isNegative;
    }

    public static int[] cero(int [] vect){
        int [] ceroVect = new int[vect.length];

        for (int i = 0; i<vect.length; i++)
            ceroVect[i] = (vect[i] == 0) ? 1 : 0;

        return ceroVect;
    }

    public static int[] uno(int [] vect){
        int [] unoVect = new int[vect.length];

        for (int i = 0; i<vect.length; i++)
            unoVect[i] = (vect[i] != 0) ? 1 : 0;

        return unoVect;
    }

    public static int sign(int [] vect){
        int signVal = 1;

        for (int i = 0; i<vect.length; i++)
            if (vect[i] < 0)
                signVal = 0;

        return signVal;
    }
}
