package br.ufpa.afnd;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class AFNDConfiguration {
    private List<String> states;
    private String initialState;
    private List<String> finalStates;
    private List<String> alphabet;
    private Map<String, Map<String, List<String>>> transitions;
    private List<Test> tests;

    public static AFNDConfiguration fromClassPathResource(String resourcePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = AFNDConfiguration.class.getResourceAsStream(resourcePath);
        return mapper.readValue(inputStream, AFNDConfiguration.class);
    }

    public String getTargetWord() {
        return "computador"; // Pode ser dinâmico se necessário
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(List<String> finalStates) {
        this.finalStates = finalStates;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(List<String> alphabet) {
        this.alphabet = alphabet;
    }

    public Map<String, Map<String, List<String>>> getTransitions() {
        return transitions;
    }

    public void setTransitions(Map<String, Map<String, List<String>>> transitions) {
        this.transitions = transitions;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public static class Test {
        private String input;
        private List<Integer> expectedPositions;

        public String getInput() {
            return input;
        }

        public void setInput(String input) {
            this.input = input;
        }

        public List<Integer> getExpectedPositions() {
            return expectedPositions;
        }

        public void setExpectedPositions(List<Integer> expectedPositions) {
            this.expectedPositions = expectedPositions;
        }
    }
}