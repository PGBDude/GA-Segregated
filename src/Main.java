import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Population population = new Population();
        int generations = 1000;
        double lastPopFitness;
        int optimaCounter = 0;

        //Reading the Data in the file and creating the initial population
        int popSize = 1000;
        if(popSize < 10000){
            try{
                Scanner sc = new Scanner(new File("src/date.txt"));
                for (int i = 0; i < popSize; i++) {
                    LinkedList<Double> genes = new LinkedList<>();
                    String gene = sc.nextLine();
                    StringTokenizer tokenizer = new StringTokenizer(gene, " ");
                    while(tokenizer.hasMoreTokens()){
                        genes.add(Double.parseDouble(tokenizer.nextToken()));
                    }
                    Individual individual = new Individual(genes);
                    individual.calculateFitness();
                    population.addIndividual(individual);
                }
            }catch(Exception e){
                System.out.println(e);
            }

            //Algorithm loop
            lastPopFitness = population.getPopFitness();
            while(generations-- > 0){

                ArrayList<Individual> temp = new ArrayList();
                Population offspring = new Population();
                population.calculateIndividualFitness();
                population.calculatePopFitness();
                //population.debbuging();
                for (int i = 0; i < population.popSize(); i++) {
                    Individual parent1 = population.randomSelect();
                    Individual parent2 = population.randomSelect();
                    ArrayList<Individual> children = population.Crossover(parent1, parent2, parent1.geneSize());
                    children.get(0).calculateFitness();
                    children.get(1).calculateFitness();
                    temp.add(children.get(0));
                    temp.add(children.get(1));
                }
                Collections.sort(temp);
                for (int i = 0; i < popSize; i++) {
                    offspring.addIndividual(temp.get(i));
                }
                population = offspring;
                population.mutatePopulation();
                Population.generation += 1;
                population.debbuging();
                if(lastPopFitness == population.getPopFitness()){
                    optimaCounter += 1;
                }else{
                    optimaCounter = 0;
                }
                if(optimaCounter == 10){
                    System.out.println("10 generations had the same fitness! Local optima found after " + Population.generation + " generations!");
                    break;
                }
                lastPopFitness = population.getPopFitness();
            }
        }
    }
}
