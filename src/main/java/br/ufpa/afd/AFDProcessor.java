package br.ufpa.afd;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import java.util.Map;

public class AFDProcessor {
    private AFDConfiguration config;
    private String currentState;

    public AFDProcessor(AFDConfiguration config) {
        this.config = config;
        reset();
    }

    public boolean processInput(String input) {
        reset();
        for (char symbol : input.toCharArray()) {
            String strSymbol = String.valueOf(symbol);
            if (!config.getAlphabet().contains(strSymbol)) {
                throw new IllegalArgumentException("Símbolo inválido: " + strSymbol);
            }

            Map<String, String> stateTransitions = config.getTransitions().get(currentState);
            if (stateTransitions == null) {
                return false;
            }

            String nextState = stateTransitions.get(strSymbol);
            if (nextState == null) {
                return false;
            }
            currentState = nextState;
        }
        return config.getFinalStates().contains(currentState);
    }

    public boolean isAccepted() {
        return config.getFinalStates().contains(currentState);
    }

    public void reset() {
        this.currentState = config.getInitialState();
    }

    public String getCurrentState() {
        return currentState;
    }
}
