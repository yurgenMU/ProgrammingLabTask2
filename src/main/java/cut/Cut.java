package cut;

import java.util.Map;
import java.util.Scanner;

public class Cut {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        CutMethods cm = new CutMethods();
        while (true) {
            String input = scan.nextLine();
            if (cm.isInputValid(input)) {
                String inputText;
                String outputText = "";
                Map<String, String> parameters = cm.getParameters(input);
                if ("".equals(parameters.get("inputFile"))) {
                    System.out.println("Enter the text");
                    inputText = scan.nextLine();
                } else {
                    inputText = cm.readFromFile(parameters.get("inputFile"));
                }
                String n = parameters.get("n");
                String k = parameters.get("k");
                if ("-c".equalsIgnoreCase(parameters.get("flag"))) {
                    outputText = cm.cutFlagC(inputText, n, k);
                }
                if ("-w".equalsIgnoreCase(parameters.get("flag"))) {
                    outputText = cm.cutFlagW(inputText, n, k);
                }
                if ("".equals(parameters.get("outputFile"))) {
                    System.out.println(outputText);
                } else {
                    cm.writeIntoFile(outputText, parameters.get("outputFile"));
                }
            } else {
                System.out.println("Input is invalid");
            }

        }
    }
}
