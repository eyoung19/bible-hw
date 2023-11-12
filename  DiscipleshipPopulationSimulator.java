import java.util.ArrayList;
import java.util.List;

public class DiscipleshipPopulationSimulator {

    static final int MAX_AGE = 72;
    static final int TRAINING_START_AGE = 18;
    static final int YEARS_PER_CYCLE = 3;
    static final int DISCIPLE_GROWTH_RATE = 2;
    static final int AGE_OF_BIRTH = 30;
    static final long INITIAL_POPULATION = 7700000000L; 

    static class Human {
        int age;
        boolean isDisciple;

        Human(int age, boolean isDisciple) {
            this.age = age;
            this.isDisciple = isDisciple;
        }
    }

    public static void main(String[] args) {
        long totalPopulation = INITIAL_POPULATION;
        List<Human> humans = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            humans.add(new Human(TRAINING_START_AGE, true)); 
        }

        int years = 0;
        while (totalPopulation > humans.stream().filter(h -> h.isDisciple).count()) {
            
            totalPopulation = updatePopulation(humans, totalPopulation);
            years++;
        }

        System.out.println("Years needed: " + years);
    }

    private static long updatePopulation(List<Human> humans, long totalPopulation) {
        int newDisciples = 0;
        int births = 0;
        int deaths = 0;

        for (Human human : humans) {
            if (human.age == MAX_AGE) {
                deaths++;
            } else {
                human.age++;
                if (human.isDisciple && human.age >= TRAINING_START_AGE && human.age % YEARS_PER_CYCLE == 0) {
                    newDisciples += DISCIPLE_GROWTH_RATE;
                }
                if (human.age == AGE_OF_BIRTH) {
                    births++;
                }
            }
        }

        humans.removeIf(h -> h.age == MAX_AGE); 
        for (int i = 0; i < newDisciples; i++) {
            humans.add(new Human(TRAINING_START_AGE, true)); 
        }
        for (int i = 0; i < births; i++) {
            humans.add(new Human(0, false)); 
        }

        return totalPopulation - deaths + births;
    }
}
