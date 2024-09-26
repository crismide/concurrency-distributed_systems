// CSD Mar 2013 Juansa Sendra

public class LimitedTable extends RegularTable { //max 4 in dinning-room
    public LimitedTable(StateManager state) {super(state);}
    int numFilos;
    public synchronized void enter(int id) throws InterruptedException {
        
        while(numFilos>=4){
             state.wenter(id);
             wait();
        }
        
        numFilos++; 
        state.enter(id);
        notifyAll();
        
    }
        
    public synchronized void exit(int id)  {
        numFilos--;
        
        notifyAll();
    }
    
}
