public class Event {
    protected int type;
    protected double instant;

    public Event(int type, double time){
        this.type = type; // 0 = arrivée, 1 = départ
        this.instant = time;
    }

    public int getType() {
        return type;
    }

    public double getInstant() {
        return instant;
    }
}
