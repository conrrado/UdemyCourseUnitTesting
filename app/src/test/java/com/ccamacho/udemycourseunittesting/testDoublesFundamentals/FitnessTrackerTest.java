package com.ccamacho.udemycourseunittesting.testDoublesFundamentals;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FitnessTrackerTest {

    private FitnessTracker fitnessTrackerTest;

    @Before
    public void setup() throws Exception {
        fitnessTrackerTest = new FitnessTracker();
    }

    // Esse teste falha, porque os testes não são executados em ordem que estão implementados
    //      e a classe FitnessTracker usa um singleton para armazenar valor, quer dizer que o
    //      valor não é reiniciado de um teste para outro
//    @Test
//    public void step_totalIncremented() throws Exception {
//        fitnessTrackerTest.step();
//        assertThat(fitnessTrackerTest.getTotalSteps(), is(1));
//    }

    @Test
    public void runStep_totalIncrementedByCorrectRatio() throws Exception {
        fitnessTrackerTest.runStep();
        assertThat(fitnessTrackerTest.getTotalSteps(), is(2));
    }
}
