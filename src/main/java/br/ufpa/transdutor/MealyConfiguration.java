package br.ufpa.transdutor;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class MealyConfiguration {
    private List<String> states;
    private String initialState;
    private List<String> alphabet;
    private Map<String, Map<String, Transition>> transitions;
    private List<Test> tests;

    public static MealyConfiguration fromClassPathResource(String resourcePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = MealyConfiguration.class.getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        return mapper.readValue(inputStream, MealyConfiguration.class);
    }

    public static class Transition {
        private String nextState;
        private String output;

        public String getNextState() { return nextState; }
        public void setNextState(String nextState) { this.nextState = nextState; }
        public String getOutput() { return output; }
        public void setOutput(String output) { this.output = output; }
    }

    public static class Test {
        private String input;
        private String expected;

        public String getInput() { return input; }
        public void setInput(String input) { this.input = input; }
        public String getExpected() { return expected; }
        public void setExpected(String expected) { this.expected = expected; }
    }

    // Getters e Setters
    public List<String> getStates() { return states; }
    public void setStates(List<String> states) { this.states = states; }
    public String getInitialState() { return initialState; }
    public void setInitialState(String initialState) { this.initialState = initialState; }
    public List<String> getAlphabet() { return alphabet; }
    public void setAlphabet(List<String> alphabet) { this.alphabet = alphabet; }
    public Map<String, Map<String, Transition>> getTransitions() { return transitions; }
    public void setTransitions(Map<String, Map<String, Transition>> transitions) { this.transitions = transitions; }
    public List<Test> getTests() { return tests; }
    public void setTests(List<Test> tests) { this.tests = tests; }
}