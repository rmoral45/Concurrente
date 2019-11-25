package MyLogger;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Clase destinada a crear un wrapper para el Logger del tp
 * Se utilizara el patron de diseño Singleton. para restringir a la JVM a una instanciacion de esta clase.
 * Esto se hace para poder loguear todos los eventos en un solo archivo, haciendo uso del mismo FileHandler.
 * En todas las clases en las cuales se quieran loguear eventos, se instanciará un MyLoggerWrapper y se llamará al
 * método getInstance. Aquí pueden pasar dos cosas:
 *              - Si ya existe una instancia de MyLoggerWrapper, se retorna dicha instancia.
 *              - Si no existe una, se llama al metodo prepareLogger que instancia un FileHandler,
 *                setea formato de texto y agrega dicho handler al logger.
 */
public class MyLoggerWrapper {

    public static final Logger myLogger = Logger.getLogger(MyLoggerWrapper.class.getName());

    private static MyLoggerWrapper myLoggerWrapper = null;

    public static MyLoggerWrapper getInstance() throws IOException {
        if(myLoggerWrapper == null) {
            prepareLogger();
            myLoggerWrapper = new MyLoggerWrapper();
        }
        return myLoggerWrapper;
    }

    private static void prepareLogger() throws IOException {
        FileHandler myFileHandler = new FileHandler("/home/ramiro/repos/Concurrente/src/MyLogger/project.log");
        myFileHandler.setFormatter(new SimpleFormatter());
        myLogger.addHandler(myFileHandler);
        myLogger.setUseParentHandlers(false);
        myLogger.setLevel(Level.FINEST);
    }
}
