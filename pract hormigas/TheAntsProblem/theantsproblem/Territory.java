
package theantsproblem;
import java.util.concurrent.locks.*;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

public class Territory {
    private int tam; // Matrix size
    private boolean occupied[][];
    String description = "Using Barriers";
    private Log log;
    //Condition Lleno;
    ReentrantLock lock= new ReentrantLock();
    Condition[][] condList;
    protectedCyclicBarrier barrera;
    
    public String getDesc() {
        return description;
    }

    public Territory(int tamT, Log l) {
        tam = tamT;
        occupied = new boolean[tam][tam];
        log = l;
        condList = new Condition [tam] [tam];
        barrera = new CyclicBarrier(15);
        // Initializing the matrix
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                occupied[i][j] = false;
                condList [i][j]= lock.newCondition();
            }
        }
        
       // Lleno = lock.newCondition();
        
    }

    public int getTam() {
        return tam;
    }
    
    public void putAnt(Ant h, int x, int y) {
        try{
            barrera.await();
                try{
                    lock.lock();
                        while (occupied[x][y]) {
                            try {
                                // Write in the log: ant waiting
                                log.writeLog(LogItem.PUT, h.getid(), x, y, LogItem.WAITINS,
                                        "Ant " + h.getid() + " waiting for [" + x + "," + y + "]");
                                condList[x][y].await();
                            } catch (InterruptedException e) {
                            }
                    }
                    occupied[x][y] = true;
                    h.setPosition(x, y);
                    // Write in the log: ant inside territory
                    log.writeLog(LogItem.PUT, h.getid(), x, y, LogItem.OK, "Ant " + h.getid() + " : [" + x + "," + y + "]  inside");
                }
            
                finally{lock.unlock();}
        }
        catch(InterruptedException e){return;}
        catch(BrokenBarrierException e){return;}
    }

    public  void takeAnt(Ant h) {   
        int x = h.getX();
        int y = h.getY();
        
         
        try{
            lock.lock();
            occupied[x][y] = false;
            // Write in the log: ant outside territory
            log.writeLog(LogItem.TAKE, h.getid(), x, y, LogItem.OUT, "Ant " + h.getid() + " : [" + x + "," + y + "] out");
            //Lleno.signalAll();
            condList[x][y].signalAll();
            }
        
        finally{lock.unlock();}
    }

    public void moves(Ant h, int x1, int y1, int step) {
        int x = h.getX();
        int y = h.getY();
        try{
            lock.lock();
           
            while (occupied[x1][y1]) {
                try {
                    // Write in the log: ant waiting
                    log.writeLog(LogItem.MOVE, h.getid(), x1, y1, LogItem.WAIT,
                            "Ant " + h.getid() + " waiting for [" + x1 + "," + y1 + "]");
                    //sitio.await();
                    condList[x1][y1].await();
                } catch (InterruptedException e) {
                }
            }
        
            occupied[x][y] = false;
            occupied[x1][y1] = true;
            h.setX(x1);
            h.setY(y1);
            // Write in the log: ant moving
            log.writeLog(LogItem.MOVE, h.getid(), x1, y1, LogItem.OK,
                    "Ant " + h.getid() + " : [" + x + "," + y + "] -> [" + x1 + "," + y1 + "] step:" + step);
            //sitio.signalAll();
            condList[x][y].signalAll();
         }
         finally{lock.unlock();}
    }
}
