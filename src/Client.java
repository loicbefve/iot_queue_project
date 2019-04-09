public class Client {
    protected double tempsArrivee;
    protected double dureeService;

    public Client(double arrivalTime, double serviceTime){
        this.tempsArrivee = arrivalTime;
        this.dureeService = serviceTime;
    }

    public double getDureeService() {
        return dureeService;
    }

    public double getTempsArrivee() {
        return tempsArrivee;
    }
}
