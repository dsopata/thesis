package pl.tspga.rest.tspgarest;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.tspga.rest.tspgarest.ga.GeneticAlgorithmService;
import pl.tspga.rest.tspgarest.ga.Vertex;

import java.util.List;

@Component
public class AsyncComponent {


    @Async
    public void startGaAlgorithmAsync(List<Vertex> initialVertexList) {

        System.out.println("async method thread: " + Thread.currentThread().getId());

        GeneticAlgorithmService.startGeneticAlgorithm(initialVertexList);
    }

}
