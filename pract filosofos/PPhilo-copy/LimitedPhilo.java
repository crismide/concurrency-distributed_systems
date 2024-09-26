// CSD Mar 2013 Juansa Sendra

public class LimitedPhilo extends Philo {
    public LimitedPhilo(int id, int cycles, int delay, Table table) {super(id, cycles, delay, table);
        try {
                for (int i=0; i<cycles; i++) {
                        table.enter(id);
                        table.takeR(id); delay(); table.takeL(id);
                        table.eat(id); randomDelay();
                        table.dropR(id); table.dropL(id);
                        table.ponder(id); randomDelay();
                        table.exit(id);
                    }
        } 
        catch (InterruptedException ex) {}
    }
}