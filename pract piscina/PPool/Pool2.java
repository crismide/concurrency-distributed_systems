// CSD feb 2015 Juansa Sendra

public class Pool2 extends Pool{ //max kids/instructor
    private int nInstructores;
    private int nKidsNadando;
    public void init(int ki, int cap) { 
    }
    
    public synchronized void kidSwims() throws InterruptedException {
         while((log.nk/log.ni)*nInstructores == nKidsNadando) {
             log.waitingToSwim();
             wait();
            } 
            log.swimming();
            nKidsNadando++;
            notifyAll();
        }
    public synchronized void kidRests()      {log.resting(); nKidsNadando--; notifyAll();}
    public synchronized void instructorSwims()   {log.swimming(); nInstructores++; notifyAll();  }
    public synchronized void instructorRests()throws InterruptedException {
        while (nKidsNadando != 0 || (log.nk/log.ni)*nInstructores == nKidsNadando){
            log.waitingToRest(); 
            wait();
        } 
        nInstructores--;
        log.resting(); 
        notifyAll(); 
    }
}
