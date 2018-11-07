package pl.tspga.rest.tspgarest.ga;


public class GenethicAlgorithm {

    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;

    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.populationSize(), true);

        int elitismOffset = 0;
        if (elitism) {
            newPopulation.saveTour(0, pop.getFittest());
            elitismOffset = 1;
        }

        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            Chromosome parent1 = tournamentSelection(pop);
            Chromosome parent2 = tournamentSelection(pop);
            Chromosome child = crossover(parent1, parent2);
            newPopulation.saveTour(i, child);
        }

        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            mutate(newPopulation.getTour(i));
        }

        return newPopulation;
    }

    public static Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        Chromosome tempParent1 = parent1;
        Chromosome tempParent2 = parent2;

        Chromosome child = new Chromosome();

        int startPos = (int) (Math.random() * parent1.chromosomeSize());
        int endPos = (int) (Math.random() * parent1.chromosomeSize());

        for (int i = 0; i < child.chromosomeSize(); i++) {
            if (startPos < endPos && i > startPos && i < endPos) {
                child.setVertex(i, parent1.getVertex(i));
            }
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setVertex(i, parent1.getVertex(i));
                }
            }
        }

        for (int i = 0; i < parent2.chromosomeSize(); i++) {
            if (!child.containsVertex(parent2.getVertex(i))) {
                for (int ii = 0; ii < child.chromosomeSize(); ii++) {
                    if (child.getVertex(ii) == null) {
                        child.setVertex(ii, parent2.getVertex(i));
                        break;
                    }
                }
            }
        }
        return child;
    }

    private static void mutate(Chromosome chromosome) {
        for(int chromePos1 = 1; chromePos1 < chromosome.chromosomeSize() - 1; chromePos1++){
            if(Math.random() < mutationRate){
                int tourPos2 = (int) (1+((chromosome.chromosomeSize() - 2) * Math.random()));

                Vertex vertex1 = chromosome.getVertex(chromePos1);
                Vertex vertex2 = chromosome.getVertex(tourPos2);

                chromosome.setVertex(tourPos2, vertex1);
                chromosome.setVertex(chromePos1, vertex2);
            }
        }
    }

    private static Chromosome tournamentSelection(Population pop) {
        Population tournament = new Population(tournamentSize, false);
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.populationSize());
            tournament.saveTour(i, pop.getTour(randomId));
        }
        Chromosome fittest = tournament.getFittest();
        return fittest;
    }
}