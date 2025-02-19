package br.ufpa.transdutor;

import java.util.Map;

public class MealyTransducer {
    private MealyConfiguration config;
    private String currentState;

    public MealyTransducer(MealyConfiguration config) {
        this.config = config;
        reset();
    }

    public void reset() {
        currentState = config.getInitialState();
    }

    public String processInput(String input) {
        reset();
        if (input.isEmpty()) {
            return "";
        }
        String[] symbols = input.split(",");
        StringBuilder output = new StringBuilder();
        for (String symbol : symbols) {
            symbol = symbol.trim();
            if (!config.getAlphabet().contains(symbol)) {
                throw new IllegalArgumentException("Invalid symbol: " + symbol);
            }
            Map<String, MealyConfiguration.Transition> stateTransitions = config.getTransitions().get(currentState);
            if (stateTransitions == null) {
                throw new IllegalArgumentException("No transitions defined for state: " + currentState);
            }
            MealyConfiguration.Transition transition = stateTransitions.get(symbol);
            if (transition == null) {
                throw new IllegalArgumentException("No transition for symbol " + symbol + " in state " + currentState);
            }
            if (output.length() > 0) {
                output.append(",");
            }
            output.append(transition.getOutput());
            currentState = transition.getNextState();
        }
        return output.toString();
    }
}