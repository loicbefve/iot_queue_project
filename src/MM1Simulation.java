import java.util.List;
import java.util.Vector;

public class MM1Simulation {
    double lambda;
    double mu;
    ListeEvents liste;
    Queue q;
    double t;


    public MM1Simulation(double lambda, double mu) {
        this.lambda = lambda;
        this.mu = mu;
        q = new Queue();
        liste = new ListeEvents();
    }

    public double expo(double taux) {
        return -Math.log(Math.random()) / taux;
    }

    public void simulate(double simLength) {
        //Variable pour temps moyen:
        Vector<Double> arrivalTime = new Vector<>();
        Vector<Double> waiting_time = new Vector<>();
        //Création de l'événement s1 arrivée de client à t=0
        Event s1 = new Event(0,0);

        //On l'ajoute à la liste des évenements:
        liste.addEvent(s1);

        //Déclaration des variables:

        while (t < simLength) {
            System.out.println(liste.events);
            System.out.println(q.lesClients);
            //On lit le prochain évenement
            Event current_event = liste.events.get(0);

            //On le retire de la liste
            liste.events.remove(0);

            //On avance le temps à la date de cet évenement:
            t = current_event.getInstant();

            //S'il s'agit d'une arrrivée
            if (current_event.type == 0) {

                //On génère l'arrivée suivante et on l'ajoute à la liste:
                Event next_event = new Event(0, t+expo(lambda));
                liste.addEvent(next_event);
                arrivalTime.add(next_event.getInstant());

                //On calcule la durée de service du client
                double duree_service = expo(mu);
                System.out.println(duree_service);

                //On ajoute le client dans la file d'attente:
                q.lesClients.add(new Client(t, duree_service));

                //Si le client est seul:
                if (q.lesClients.size() == 1) {
                    //On créer l'event de départ car else ne le fera pas:
                    Event depart_client_unique = new Event(1, t+duree_service);
                    //On l'ajoute aux events:
                    liste.addEvent(depart_client_unique);

                    //On calcul le temps d'attente:
                    double waiting = depart_client_unique.getInstant() - arrivalTime.get(0);
                    waiting_time.add(waiting);
                    arrivalTime.remove(0);
                }

                //S'il s'agit d'un départ:
            } else {

                //On supprime le client de la file d'attente:
                q.lesClients.remove(0);

                //Si la file n'est pas vide:
                if (!q.lesClients.isEmpty()){
                    //On génère l'évenement correspondant au départ du client suivant:
                    Event depart_next_client = new Event(1, t+expo(mu));

                    //On le met dans la liste d'events
                    liste.addEvent(depart_next_client);

                    //On calcul le temps d'attente:
                    double waiting = depart_next_client.getInstant() - arrivalTime.get(0);
                    waiting_time.add(waiting);
                    arrivalTime.remove(0);
                }
            }
        }

        System.out.println(meanWait(waiting_time));

    }

    public double meanWait(Vector<Double> waitingTime){
        double sum = 0;
        for (double i : waitingTime){
            sum += i;
        }
        return sum/waitingTime.size();
    }

}

