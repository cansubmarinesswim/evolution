package com.evo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Stats{

    private Simulation simulation;
    private static String statsHeader = "Epoch;TotalLivingAnimals;TotalDeadAnimals;TotalPlants;MeanEnergy;MeanLifespan;MeanChildrenCount;DominatingGenome";
    private static String statsFormat = "%d;%d;%d;%d;%.2f;%.2f;%.2f;%s";
    private ArrayList<String> simulationStats;

    public Stats(Simulation simulation) {
        this.simulation = simulation;
        this.simulationStats = new ArrayList<>();
    }

    public void trackSimulationProgress(){
        String epochInfo = String.format(statsFormat,
                getEpoch(),
                getTotalLivingAnimals(),
                getTotalDeadAnimals(),
                getTotalPlants(),
                getMeanEnergy(),
                getMeanLifespan(),
                getMeanChildren(),
                getDominatingGenome());
        this.simulationStats.add(epochInfo);
    }

    public void saveSimulationReport(String fileName){
        writeReportToFile(this.simulationStats, fileName);
    }

    public void saveSimulationReport(int from, int to, String fileName){
        ArrayList<String> sublist = (ArrayList<String>) this.simulationStats.stream()
                .skip(from-1)
                .limit(to-from)
                .collect(Collectors.toList());
        System.out.println(sublist);
        writeReportToFile(sublist, fileName);
    }

    private void writeReportToFile(ArrayList<String> list, String fileName){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            writer.write(statsHeader);
            writer.newLine();
            for(String line: list){
                writer.write(line);
                writer.newLine();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public String getDominatingGenome() {
		/*
		Get the most common genome within all living animals
		 */
        return this.simulation.world.getDominatingGenome();
    }

    public int getEpoch(){
        return this.simulation.world.epoch;
    }
    public int getTotalLivingAnimals(){
        return this.simulation.world.livingAnimals.size();
    }

    public int getTotalDeadAnimals(){
        return this.simulation.world.deadAnimals.size();
    }

    public int getTotalPlants(){
        return this.simulation.world.totalPlantCount;
    }

    public float getMeanEnergy(){
        List<Animal> livingAnimals = this.simulation.world.livingAnimals;
        if (livingAnimals.size() > 0) {
            float totalEnergy = livingAnimals.stream()
                    .mapToInt(e -> e.getCurrentEnergy())
                    .sum();
            return totalEnergy / livingAnimals.size();
        }
        return 0;
    }

    public float getMeanLifespan(){
        List<Animal> deadAnimals = this.simulation.world.deadAnimals;
        if (deadAnimals.size()>0){
            return  (float) deadAnimals.stream()
                    .mapToInt(e -> e.deathEpoch - e.birthEpoch)
                    .sum() / this.simulation.world.epoch;
        }
        return 0;
    }

    public float getMeanChildren(){
        List<Animal> livingAnimals = this.simulation.world.livingAnimals;
        if (livingAnimals.size() > 0) {
            float totalChildren = livingAnimals.stream()
                    .mapToInt(e -> e.getChildren().size())
                    .sum();
            return totalChildren / livingAnimals.size();
        }
        return 0;
    }
}
