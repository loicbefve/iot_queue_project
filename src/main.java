import java.util.Scanner;

public class main{
    public static void main(String[] arg){

        Scanner user_input = new Scanner( System.in );

        System.out.println("Quelle file voulez vous lancer? (MM1 ou MD1):");

        String sim_type = user_input.next();

        System.out.println("Quel mu?:");

        double mu = Double.parseDouble(user_input.next());

        System.out.println("Quel lambda?:");

        double lambda = Double.parseDouble(user_input.next());

        System.out.println("Quel temps de simulation?:");

        double t = Double.parseDouble(user_input.next());


//        MM1 simulation

        if( sim_type.toUpperCase().equals("MM1")){

            MM1Simulation s = new MM1Simulation(lambda, mu);
            s.simulate(t);
        }

//         MD1 Simulation

        else {
            MD1Simulation s = new MD1Simulation(lambda, mu);
            s.simulate(t);
        }

        /*double mu = 1.;
        double lambda = 0;

        for( double i = 1 ; i <= 100 ; i = i + 1){

            lambda = i/100;


            MD1Simulation s = new MD1Simulation(lambda, mu);
            s.simulate(100000);

        }*/


    }
}
