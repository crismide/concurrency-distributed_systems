// CSD feb 2015 Juansa Sendra

public class Pool3 extends Pool{ //max capacity
    private int nInstructores;
    private int nKidsNadando;
    public void init(int ki, int cap) { 
    }
    
    public synchronized void kidSwims() throws InterruptedException {
         while((log.nk/log.ni)*nInstructores == nKidsNadando || nInstructores+nKidsNadando >= (log.nk+log.ni)/2 ) {
             log.waitingToSwim();
             wait();
            } 
            log.swimming();
            nKidsNadando++;
            notifyAll();
        }
        
    public synchronized void kidRests()      {
        log.resting();
        nKidsNadando--;
        notifyAll();
    }
    
    public synchronized void instructorSwims() throws InterruptedException  {
        while (nInstructores+nKidsNadando >= (log.nk+log.ni)/2 ){  
            log.waitingToSwim();
            wait();
        }
        log.swimming(); 
        nInstructores++; 
        notifyAll();  
    }
        
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
