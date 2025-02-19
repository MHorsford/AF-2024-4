package br.ufpa.afd;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class AFDConfiguration {
    private List<String> states;
    private String initialState;
    private List<String> finalStates;
    private List<String> alphabet;
    private Map<String, Map<String, String>> transitions;
    private List<Test> tests;

    public static AFDConfiguration fromClassPathResource(String resourcePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = AFDConfiguration.class.getResourceAsStream(resourcePath);
        if (inputStream ==null){
            throw new IOException("Resource not found: " + resourcePath);
        }
        return mapper.readValue(inputStream, AFDConfiguration.class);
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public List<String> getStates() {
        return states;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setFinalStates(List<String> finalStates) {
        this.finalStates = finalStates;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public void setAlphabet(List<String> alphabet) {
        this.alphabet = alphabet;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public void setTransitions(Map<String, Map<String, String>> transitions) {
        this.transitions = transitions;
    }

    public Map<String, Map<String, String>> getTransitions() {
        return transitions;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public List<Test> getTests() {
        return tests;
    }

    public static class Test{
        private String input;
        private String expected;

        public void setInput(String input) {
            this.input = input;
        }

        public String getInput() {
            return input;
        }

        public void setExpected(String expected) {
            this.expected = expected;
        }

        public String getExpected() {
            return expected;
        }
    }
}
