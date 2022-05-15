package sample;

public class Cell {
    private int xCore;
    private int yCore;
    private String aliveOrDead;
    private String next;

    public Cell(int xCore, int yCore) {
        this.xCore = xCore;
        this.yCore = yCore;
        aliveOrDead="Dead";
        this.next="Dead";
    }

    public String getAliveOrDead() {
        return aliveOrDead;
    }

    public void setAliveOrDead(String aliveOrDead) {
        this.aliveOrDead = aliveOrDead;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void change(){
        this.aliveOrDead=this.next;
    }

    public void toOpposite(){
        if(this.aliveOrDead.equals("Dead"))
            this.aliveOrDead="Alive";
        else
            this.aliveOrDead="Dead";
    }

    public int getXCore() {
        return xCore;
    }

    public void setXCore(int xCore) {
        this.xCore = xCore;
    }

    public int getYCore() {
        return yCore;
    }

    public void setYCore(int yCore) {
        this.yCore = yCore;
    }


}
