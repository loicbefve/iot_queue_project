
public class MD1Simulation {
    double lambda;
    double mu;
    ListeEvents liste;
    Queue q;
    double t;


    public MD1Simulation(double lambda, double mu) {
        this.lambda = lambda;
        this.mu = mu;
        q = new Queue();
        liste = new ListeEvents();
    }

    public double expo(double taux) {
        return -Math.log(Math.random()) / taux;
    }

    public double simulate(double simLength) {

        //Variable pour temps moyen:
        int nb_client = 0;
        double waiting_time = 0.;
        int n_event = 1;



        //Création de l'événement s1 arrivée de client à t=0
        Event s1 = new Event(0,0);

        //On l'ajoute à la liste des évenements:
        liste.addEvent(s1);

        while (t < simLength) {
/*            System.out.println(liste.events);
            System.out.println(q.lesClients);*/
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
                n_event += 1;

                //On calcule la durée de service du client
                double duree_service = expo(mu);

                //On ajoute le client dans la file d'attente:
                q.lesClients.add(new Client(t, duree_service));

                //Si le client est seul:
                if (q.lesClients.size() == 1) {
                    //On créer l'event de départ car else ne le fera pas:
                    Event depart_client_unique = new Event(1, t+duree_service);
                    //On l'ajoute aux events:
                    liste.addEvent(depart_client_unique);
                    n_event += 1;

                }

                //S'il s'agit d'un départ:
            } else {

                Client leaving_client = q.lesClients.get(0);

                waiting_time += t - leaving_client.getTempsArrivee();
                nb_client+=1;

                //On supprime le client de la file d'attente:
                q.lesClients.remove(0);


                //Si la file n'est pas vide:
                if (!q.lesClients.isEmpty()){
                    //On génère l'évenement correspondant au départ du client suivant:
                    Event depart_next_client = new Event(1, t+q.lesClients.get(0).getDureeService()/*expo(mu)*/);

                    //On le met dans la liste d'events
                    liste.addEvent(depart_next_client);
                    n_event += 1;

                }
            }

        }

        double ro = lambda/mu;

        System.out.println("\n\nDébut de la simulation M/M/1: mu=" + this.mu + ", lambda =" + this.lambda + ",tsim =" + simLength);
        System.out.println("La charge pour cette simulation est de:" + ro);
        System.out.println("Fin de la simulation...");
        System.out.println(nb_client + " clients ont été traités");
        System.out.println("Le temps moyen d'attente est de:" + waiting_time/nb_client);
        System.out.println("Le temps théorique d'attente devrait être:" + ((mu/(1-ro)) - (mu*ro/(2*(1-ro)))) );
        System.out.println("Le nombre moyen de clients est de:" + n_event/simLength);
        System.out.println("Le nombre moyen de clients théorique devrait être:" + (((2*ro)-(ro*ro))/(2*(1 - ro))));

//       System.out.println(waiting_time/nb_client);
//        System.out.println(n_event/simLength);

//        System.out.println(((2*ro) - (ro*ro))/(2*(1-ro)));
//        System.out.println("\n");




        return waiting_time / nb_client;
    }

}

