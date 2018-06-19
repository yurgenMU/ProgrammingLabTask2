package cut;

import java.io.*;
import java.util.*;


public class CutMethods {

//Это не нужно
    public static String treatment(String inputString, String[] tokens) throws IOException {
        String textName = tokens[tokens.length - 2];
        String inputFile = "";
        String unexpectedToken = tokens[tokens.length - 3];
        if (tokens.length > 3) {
            if ((textName != null) && (unexpectedToken != "-o")) { //За такое сразу два, на самом деле
                FileReader text = null;
                try {
                    text = new FileReader("cut.txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Scanner scan = new Scanner(text);
                int i = 1;
                while (scan.hasNextLine()) {
                    inputFile += scan.nextLine();
                    i++;
                }
                text.close();
            } else {
                Scanner scan = new Scanner(System.in);
                inputFile += scan.nextLine();
            }
        }
        return inputFile;
    }

    public String cutFlagC(String inputFile, String n, String k) {
        String answer = "";
        if ((n.equals("")) && (!k.equals(""))) {
            answer = inputFile.substring(0, Integer.parseInt(k) + 1);
        } else if ((!n.equals("")) && (k.equals(""))) {
            answer = inputFile.substring(Integer.parseInt(n), inputFile.length() - 1);
        } else if ((!n.equals("")) && (!k.equals(""))) {
            answer = inputFile.substring(Integer.parseInt(n), Integer.parseInt(k) + 1);
        }
        return answer;
    }

//Где-то здесь есть бага, ищи
    public String cutFlagW(String inputFile, String n, String k) {
        String answer = "";
        List<String> words = getWordsFromString(inputFile);
        if (("".equals(n)) && (!("".equals(k)))) {
            for (Integer i = 0; i < Integer.parseInt(k); i++) {
                answer += words.indexOf(i);
            }
        } else if ((!("".equals(n))) && ("".equals(k))) {
            for (Integer i = Integer.parseInt(n); i <= words.toString().length(); i++) {
                answer += words.indexOf(i);
            }
        } else if ((!("".equals(n))) && ((!("".equals(n))))) {
            for (Integer i = Integer.parseInt(n); i <= Integer.parseInt(k); i++) {
                answer += words.indexOf(i);
            }
        }
        return answer;
    }

    /**
     * Re
     *
     * @param inputFile
     * @return
     */
    private List<String> getWordsFromString(String inputFile) {
        inputFile = inputFile.replaceAll("[;:!?,.-]", "");
        String[] words = inputFile.split("\\s+");
        return Arrays.asList(words);
    }

    /**
     * Returns Map with values of all parameters of the terminal command. If some argument is missed his value is equal
     * to empty string
     *
     * @param input String from the command line
     * @return Map with parameter values
     */
    public Map<String, String> getParameters(String input) {
        String[] tokens = input.split(" ");
        Map<String, String> params = new HashMap<>();
        params.put("flag", tokens[1]);
        if ("-o".equalsIgnoreCase(tokens[2])) {
            params.put("outputFile", tokens[3]);
            if (tokens.length == 6) {
                params.put("inputFile", tokens[4]);
            }
            if (tokens.length == 5) {
                params.put("inputFile", "");
            }
        } else {
            if (tokens.length == 4) {
                params.put("inputFile", tokens[2]);
            } else {
                params.put("inputFile", "");
            }
            params.put("outputFile", "");
        }
        String range = tokens[tokens.length - 1];
        String[] numbersOfRange = range.split("-");
        Character firstOfChar = range.charAt(0);
        Character lastOfChar = range.charAt(range.length() - 1);
        String n = "";
        String k = "";
        if ((firstOfChar != '-') && (lastOfChar != '-')) {
            n = numbersOfRange[0];
            k = numbersOfRange[1];
        } else {
            if (firstOfChar != '-') {
                n = numbersOfRange[0];
            } else {
                if (lastOfChar != '-') {
                    k = numbersOfRange[1];
                }
            }
        }
        params.put("n", n);
        params.put("k", k);
        return params;
    }

    /**
     * Checks if input suits the given format
     *
     * @param input string from the command line
     * @return result of checking
     */
    public boolean isInputValid(String input) {
        String[] tokens = input.split(" ");
        if ((tokens.length < 3) || (tokens.length > 6))
            return false;
        if (!"cut".equalsIgnoreCase(tokens[0]))
            return false;
        if ((!"-c".equalsIgnoreCase(tokens[1])) && (!"-w".equalsIgnoreCase(tokens[1]))) {
            return false;
        }

        if ("-o".equalsIgnoreCase(tokens[2]) && (tokens.length < 5))
            return false;
        if (!tokens[tokens.length - 1].contains("-")) {
            return false;
        } else {
            String[] borders = tokens[tokens.length - 1].split("-");
            if (borders.length == 2) {
                if ((!"".equals(borders[0])) && (!"".equals(borders[1])) && (Integer.parseInt(borders[0])
                        > Integer.parseInt(borders[1]))) {
                    return false;
                }
            }
            return true;
        }


    }

    /**
     * Method reading from the specified file
     *
     * @param path path to the specified file
     * @return text from the given file as String
     */
    public String readFromFile(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String sCurrentLine;
            while ((sCurrentLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(sCurrentLine).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    /**
     * Method writing String into the file
     *
     * @param arg  String to write
     * @param path path to the specified file
     */
    public void writeIntoFile(String arg, String path) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)))) {
            writer.write(arg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
