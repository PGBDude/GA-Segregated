import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Population mainPopulation = new Population("Main group");
        Population pop1 = new Population("Second group");
        Population pop2 = new Population("Third group");
        Population pop3 = new Population("Fourth group");
        int generations = 1000;
        double lastPopFitness;
        int optimaCounter = 0;

        //Reading the Data in the file and creating the initial population
        int popSize = 1000;
        int groupSize = popSize / 4;
        ArrayList<Individual> individuals = new ArrayList();
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
                    individuals.add(individual);
                }
            }catch(Exception e){
                System.out.println(e);
            }

            for (int i = 0; i < groupSize; i++) {
                mainPopulation.addIndividual(individuals.get(i));
                pop1.addIndividual(individuals.get(groupSize + i));
                pop2.addIndividual(individuals.get((groupSize * 2)+ i));
                pop3.addIndividual(individuals.get((groupSize * 3)+ i));
            }

            //Algorithm loop
            lastPopFitness = mainPopulation.getPopFitness();
            while(generations-- > 0){

                mainPopulation.calculateIndividualFitness();
                mainPopulation.calculatePopFitness();
                pop1.calculateIndividualFitness();
                pop1.calculatePopFitness();
                pop2.calculateIndividualFitness();
                pop2.calculatePopFitness();
                pop3.calculateIndividualFitness();
                pop3.calculatePopFitness();

                //population.debbuging();

                mainPopulation = getOffspring(mainPopulation, groupSize);
                pop1 = getOffspring(pop1, groupSize);
                pop2 = getOffspring(pop2, groupSize);
                pop3 = getOffspring(pop3, groupSize);

                mainPopulation.mutatePopulation();
                pop1.mutatePopulation();
                pop2.mutatePopulation();
                pop3.mutatePopulation();

                Population.generation += 1;
                mainPopulation.debbuging();
                //pop1.debbuging();
                //pop2.debbuging();
                //pop3.debbuging();
                LinkedList<Population> populations;
                Random r = new Random();
                switch(r.nextInt(3)){
                    case 0:
                        populations = exchange(mainPopulation, pop1, r.nextInt(groupSize-(groupSize/2) + (groupSize/4)));
                        mainPopulation = populations.get(0);
                        pop1 = populations.get(1);
                        break;
                    case 1:
                        populations = exchange(mainPopulation, pop2, r.nextInt(groupSize-(groupSize/2) + (groupSize/4)));
                        mainPopulation = populations.get(0);
                        pop2 = populations.get(1);
                        break;
                    case 2:
                        populations = exchange(mainPopulation, pop3, r.nextInt(groupSize-(groupSize/2) + (groupSize/4)));
                        mainPopulation = populations.get(0);
                        pop3 = populations.get(1);
                        break;
                }

                mainPopulation.calculatePopFitness();

                if(lastPopFitness == mainPopulation.getPopFitness()){
                    optimaCounter += 1;
                }else{
                    optimaCounter = 0;
                }
                if(optimaCounter == 10){
                    System.out.println("10 generations had the same fitness! Local optima found after " + Population.generation + " generations!");
                    mainPopulation.logToFile();
                    break;
                }
                lastPopFitness = mainPopulation.getPopFitness();
            }
        }
    }

    public static Population getOffspring(Population population, int groupSize){
        ArrayList<Individual> temp = new ArrayList();
        Population offspring = new Population(population.getName());
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
        for (int i = 0; i < groupSize; i++) {
            offspring.addIndividual(temp.get(i));
        }
        return offspring;
    }

    public static LinkedList<Population> exchange(Population pop1, Population pop2, int nrIndividuals){
        LinkedList<Population> populations = new LinkedList<>();
        ArrayList<Individual> indivizi1 = new ArrayList<>();
        ArrayList<Individual> indivizi2 = new ArrayList<>();
        Individual temp;
        Individual temp2;


        indivizi1 = pop1.getIndividuals();
        indivizi2 = pop2.getIndividuals();

        Collections.sort(indivizi1);
        Collections.sort(indivizi2);

        for (int i = 0; i < nrIndividuals; i++) {
            Random r = new Random();
            int index = r.nextInt(indivizi1.size());

            temp = indivizi1.get(index);
            temp2 = indivizi2.get(index);

            indivizi1.remove(index);
            indivizi2.remove(index);

            indivizi2.add(temp);
            indivizi1.add(temp2);
        }

        Population population1 = new Population(indivizi1, pop1.getName());
        Population population2 = new Population(indivizi2, pop2.getName());
        populations.add(population1);
        populations.add(population2);

        return populations;
    }
}
