// CSD feb 2013 Juansa Sendra

public class Pool4 extends Pool { //kids cannot enter if there are instructors waiting to exit
    private int nInstructores;
    private int nKidsNadando;
    private int nInstructoresWaiteando;
    public void init(int ki, int cap) { 
    }
    
    public synchronized void kidSwims() throws InterruptedException {
         while((log.nk/log.ni)*nInstructores == nKidsNadando || nInstructores+nKidsNadando >= (log.nk+log.ni)/2 || nInstructoresWaiteando>=1 ) {
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
        while (nKidsNadando != 0 || (log.nk/log.ni)*nInstructores== nKidsNadando){
            nInstructoresWaiteando++;
            log.waitingToRest(); 
            wait();

        } 
        nInstructoresWaiteando--;
        nInstructores--;
        log.resting(); 
        notifyAll(); 
    }
}
