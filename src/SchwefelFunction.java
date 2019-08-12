import java.util.LinkedList;

public class SchwefelFunction {

    public double Schwefel(Individual individ) {
        double sum = 0;
        double genesSum = 0;
        LinkedList<Double> genes = individ.getGenes();

        for (int j = 0; j < individ.geneSize(); j++) {

            genesSum += genes.get(j);
            sum += genes.get(j) * Math.sin(Math.sqrt(Math.abs(genes.get(j))));
        }

        double f = 418.9829 *  individ.geneSize();
        f -= sum;
        return f;
    }
}
