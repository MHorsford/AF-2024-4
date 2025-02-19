package br.ufpa.afnd;

import java.util.*;

public class AFNDProcessor {
    private final AFNDConfiguration config;
    private Set<String> currentStates;
    private List<Integer> matches;
    private String processedText;
    private boolean acceptance;

    public AFNDProcessor(AFNDConfiguration config) {
        this.config = config;
        this.matches = new ArrayList<>();
        reset();
    }

    public void processText(String text) {
        this.processedText = preprocessText(text);
        reset();

        for (int i = 0; i < processedText.length(); i++) {
            processSymbol(processedText.charAt(i), i);
        }
    }

    private String preprocessText(String text) {
        return text.toLowerCase()
                .replaceAll("[^a-z0-9]", "#");
    }

    private void processSymbol(char symbol, int position) {
        Set<String> nextStates = new HashSet<>();
        String strSymbol = Character.toString(symbol);

        for (String state : currentStates) {
            Map<String, List<String>> transitions = config.getTransitions().getOrDefault(state, Collections.emptyMap());

            // Handle explicit transitions first
            if (transitions.containsKey(strSymbol)) {
                nextStates.addAll(transitions.get(strSymbol));
            }
            // Handle wildcard transitions
            if (transitions.containsKey("*")) {
                nextStates.addAll(transitions.get("*"));
            }
        }

        checkForMatch(position, nextStates);
        currentStates = nextStates;
        this.acceptance = currentStates.stream().anyMatch(config.getFinalStates()::contains);
    }

    private void checkForMatch(int position, Set<String> nextStates) {
        if (nextStates.contains("q10")) {
            int startIndex = position - "computador".length() + 1;
            if (isValidMatch(startIndex)) {
                matches.add(startIndex);
            }
        }
    }

    private boolean isValidMatch(int startIndex) {
        // Boundary checks
        if (startIndex < 0 || startIndex + "computador".length() > processedText.length()) {
            return false;
        }

        // Exact match verification
        String candidate = processedText.substring(startIndex, startIndex + "computador".length());
        if (!candidate.equals("computador")) return false;

        // Word boundary checks
        boolean validStart = (startIndex == 0) || isDelimiter(startIndex - 1);
        boolean validEnd = (startIndex + "computador".length() == processedText.length()) ||
                isDelimiter(startIndex + "computador".length());

        return validStart && validEnd;
    }

    private boolean isDelimiter(int position) {
        return processedText.charAt(position) == '#';
    }

    public List<Integer> getMatches() {
        return new ArrayList<>(matches);
    }

    public boolean isAccepted() {
        return !matches.isEmpty(); // Accept if any valid matches found
    }

    public void reset() {
        this.currentStates = new HashSet<>(Collections.singleton(config.getInitialState()));
        this.matches.clear();
        this.acceptance = false;
    }
}