package ThreadLauncher;

public class ThreadTrigger {

    private int [] sequence;
    private int nshoots;

    public ThreadTrigger(){
        super();
    }

    public ThreadTrigger(int[] sequence, int nshoots){
        this.sequence = sequence;
        this.nshoots = nshoots;
    }

    public int getNshoots() {
        return nshoots;
    }

    public int[] getSequence() {
        return sequence;
    }

    public void setNshoots(int nshoots) {
        this.nshoots = nshoots;
    }

    public void setSequence(int[] sequence) {
        this.sequence = sequence;
    }
}
