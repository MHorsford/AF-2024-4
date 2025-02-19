package br.ufpa;

import br.ufpa.afd.AFDProcessor;
import br.ufpa.afd.AFDConfiguration;
import br.ufpa.afnd.AFNDConfiguration;
import br.ufpa.afnd.AFNDProcessor;
import br.ufpa.transdutor.MealyConfiguration;
import br.ufpa.transdutor.MealyTransducer;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            menu();
            int option = scanner.nextInt();
            scanner.nextLine();  // Limpar buffer

            switch (option) {
                case 1:
                    testAFD();
                    break;
                case 2:
                    testAFND();
                    break;
                case 3:
                    testMealy();
                    break;
                case 4:
                    testMoore();
                    break;
                case 5:
                    System.out.println("Encerrando programa...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void menu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Testar AFD");
        System.out.println("2. Testar AFND");
        System.out.println("3. Testar Transdutor Mealy");
        System.out.println("4. Testar Transdutor Moore");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void testAFD() {
        try {
            AFDConfiguration config = AFDConfiguration.fromClassPathResource("/afd_test_2.json");
            AFDProcessor afd = new AFDProcessor(config);

            System.out.println("\nResultado do AFD:");
            for (AFDConfiguration.Test test : config.getTests()) {
                boolean resultado = afd.processInput(test.getInput());
                System.out.printf("Cadeia: '%s' → Aceita: %s%n",
                        test.getInput(), resultado ? "Sim" : "Não");
            }
        } catch (IOException e) {
            System.err.println("Erro no AFD: " + e.getMessage());
        }
    }

    private static void testAFND() {
        try {
            AFNDConfiguration config = AFNDConfiguration.fromClassPathResource("/afnd_computador.json");
            AFNDProcessor processor = new AFNDProcessor(config);

            System.out.println("\n=== Modo de Operação AFND ===");
            System.out.println("1. Encontre 'computador' no texto de teste.");
            System.out.println("2. Verificação customizada.");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                processTestText(config, processor);
            } else {
                //processCustomInput(processor);
            }
        } catch (IOException e) {
            System.err.println("AFND Error: " + e.getMessage());
        }
    }

    private static void processTestText(AFNDConfiguration config, AFNDProcessor processor) {
        String testText = config.getTests().get(0).getInput();
        processor.processText(testText);

        System.out.println("\n=== Resultado ===");
        System.out.println("Vizualização do texto: " + testText.substring(0, 100) + "...");
        System.out.println("Valid matches: " + processor.getMatches().size());

        processor.getMatches().forEach(pos -> {
            String snippet = testText.substring(pos, pos + "computador".length());
            System.out.printf("- Posição %3d: %s%n", pos, snippet);
        });

        System.out.println("É aceito? " + processor.isAccepted());
    }


    /*private static void exibirAceitacao(AFNDProcessor processor) {
        System.out.printf("\nCadeia %saceita!%n",
                processor.isAccepted() ? "" : "NÃO ");
    }*/

    private static void testMealy() {
        try {
            MealyConfiguration config = MealyConfiguration.fromClassPathResource("/mealy_test.json");
            MealyTransducer transducer = new MealyTransducer(config);

            for (MealyConfiguration.Test test : config.getTests()) {
                String input = test.getInput();
                String expected = test.getExpected();
                String actual = transducer.processInput(input);
                System.out.printf("Input: %-15s Esperado: %-10s Saida: %-10s %s%n",
                        input, expected, actual, expected.equals(actual) ? "*" : "-");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testMoore() {
        System.out.println("\nFuncionalidade Moore não implementada ainda!");
    }
}