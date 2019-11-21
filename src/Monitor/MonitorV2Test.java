package Monitor;
import static org.junit.jupiter.api.Assertions.*;

import Politica.PoliticMode;
import ThreadLauncher.Disparador;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import petriNet.MathOperator;
import petriNet.PetriNet;

public class MonitorV2Test {

    /**
     *  Disparo n veces T-invariantes, por ende el marcado final debe ser igual al inicial
     * @throws InterruptedException
     */
    /*
    @Test
    void T_InvariantTest() throws InterruptedException {
        PetriNet rdp          = new PetriNet("/home/dabratte/repos/Concurrente/src/petriNet/prod_cons_parameters.json");
        MonitorV2 monitor     = new MonitorV2(rdp.getNtransitions(), PoliticMode.RANDOM, rdp);
        Disparador productor  = new Disparador( new int [] {0,1}, 5, monitor);
        Disparador consumidor = new Disparador( new int [] {2,3}, 5, monitor);
        Thread thprod         = new Thread( productor);
        Thread thcons         = new Thread( consumidor);

        thprod.start();
        thcons.start();
        Thread.sleep(10000);
        assertArrayEquals(new int [] {1,0,1,1,0}, rdp.getMark_vector());
    }
    */
    /*
    @Test
    void P_InvarianTest{

    }
    */
    /*
     @Test
    void ProductorOneShotTest() throws InterruptedException {
         PetriNet rdp          = new PetriNet("/home/dabratte/repos/Concurrente/src/petriNet/prod_cons_parameters.json");
         MonitorV2 monitor     = new MonitorV2(rdp.getNtransitions(), PoliticMode.RANDOM, rdp);
         Disparador productor  = new Disparador( new int [] {0}, 1, monitor);
         Thread thprod         = new Thread( productor);

         thprod.start();
         Thread.sleep(2000);
         assertArrayEquals(new int [] {0,1,0,1,0}, rdp.getMark_vector());
     }
    */
    /*
    @Test
    void ProductorMultipleShotTest() throws InterruptedException {
        PetriNet rdp          = new PetriNet("/home/dabratte/repos/Concurrente/src/petriNet/prod_cons_parameters.json");
        MonitorV2 monitor     = new MonitorV2(rdp.getNtransitions(), PoliticMode.RANDOM, rdp);
        Disparador productor  = new Disparador( new int [] {0,1}, 10, monitor);
        Thread thprod         = new Thread( productor);

        thprod.start();
        Thread.sleep(2000);
        assertArrayEquals(new int [] {1,0,1,1,0}, rdp.getMark_vector());
    }
    */
}
