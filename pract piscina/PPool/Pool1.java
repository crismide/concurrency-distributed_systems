
// CSD feb 2015 Juansa Sendra

public class Pool1 extends Pool {   //no kids alone
    private int nInstructores;
    private int nKidsNadando;
    public void init(int ki, int cap) {}
    
    public synchronized void kidSwims() throws InterruptedException      
        {
  
            while(nInstructores==0)
                {
                    log.waitingToSwim();
                    wait();
                    
                }
               
              
            log.swimming();
            nKidsNadando++;
        }
    public synchronized void kidRests()      {log.resting(); nKidsNadando--; notifyAll();}
    public synchronized void instructorSwims()   {log.swimming(); nInstructores++; notifyAll();  }
    public synchronized void instructorRests()throws InterruptedException {
	while (nKidsNadando != 0 && nInstructores==1)
		{log.waitingToRest(); wait();} 
	nInstructores--;
	log.resting(); 
	notifyAll(); 
	}
}
