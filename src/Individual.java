import java.util.LinkedList;
import java.util.Random;

public class Individual implements Comparable<Individual> {

    private double fitness;
    private double mutationChange = 0.05;
    private LinkedList<Double> genes;

    Individual(LinkedList<Double> gene){
        genes = new LinkedList<>();
        this.genes = gene;
        this.fitness = 0;
    }

    Individual(){
        genes = new LinkedList<>();
        this.fitness = 0;
    }

    public void mutate(){
        Random r = new Random();
        int m = r.nextInt(100);
        for (int i = 0; i < genes.size(); i++) {
            if(r.nextDouble() <= mutationChange){
                if(r.nextBoolean()){
                    if(genes.get(i) + (double)m < 500){
                        double temp = genes.get(i);
                        genes.set(i, temp + (double)m);
                    }else{

                    }double temp = genes.get(i);
                    genes.set(i, temp - (double)m);
                }else{
                    if(genes.get(i) - (double)m >= -500){
                        double temp = genes.get(i);
                        genes.set(i, temp - (double)m);
                    }else{

                    }double temp = genes.get(i);
                    genes.set(i, temp + (double)m);
                }
            }
        }
    }

    public void setGenes(int index, double gene){
        this.genes.set(index, gene);
    }

    public void debugIndividual(){
        System.out.println("Individual genes: " + genes.toString());
        System.out.println("Gene length: " + genes.size());
    }

    public double getFitness(){
        return this.fitness;
    }

    public int geneSize(){
        return genes.size();
    }

    public void calculateFitness(){
        SchwefelFunction f = new SchwefelFunction();
        this.fitness = f.Schwefel(this);
    }

    LinkedList<Double> getGenes(){
        return this.genes;
    }

    public void setFitness(double fitness){
        this.fitness = fitness;
    }

    public int compareTo(Individual o) {
        if (this.fitness < o.fitness)
            return -1;
        else if (this.fitness > o.fitness)
            return 1;
        return 0;
    }
}
