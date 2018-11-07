package pl.tspga.rest.tspgarest.ga;

public class Population {

    Chromosome[] chromosomes;

    public Population(int populationSize, boolean initialise) {
        chromosomes = new Chromosome[populationSize];
        if (initialise) {
            for (int i = 0; i < populationSize(); i++) {
                Chromosome newChromosome = new Chromosome();
                newChromosome.generateIndividual();
                saveTour(i, newChromosome);
            }
        }
    }

    public void saveTour(int index, Chromosome chromosome) {
        chromosomes[index] = chromosome;
    }

    public Chromosome getTour(int index) {
        return chromosomes[index];
    }

    public Chromosome getFittest() {
        Chromosome fittest = chromosomes[0];
        for (int i = 0; i < populationSize(); i++) {
            if (fittest.getFitness() < getTour(i).getFitness()) {
                fittest = getTour(i);
            }
        }
        return fittest;
    }

    public int populationSize() {
        return chromosomes.length;
    }

}