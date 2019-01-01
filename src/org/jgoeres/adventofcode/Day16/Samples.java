package org.jgoeres.adventofcode.Day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Samples {
    private ArrayList<CodeSample> codeSamples = new ArrayList<>();
    private ArrayList<CodeOperation> programCode = new ArrayList<>();

    public Samples(String pathToFile) {
        loadSamples(pathToFile);
    }

    private void loadSamples(String pathToFile) {
        /*
        Part A file looks like:
            Before: [1, 0, 1, 3]
            9 2 1 0
            After:  [2, 0, 1, 3]

            Before: [0, 1, 2, 3]
            13 0 0 1
            After:  [0, 1, 2, 3]
         */

        Pattern beforePattern = Pattern.compile("^B.+");
        Pattern codePattern = Pattern.compile("^\\d.+");
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {    // Keep reading until the end of the file.
                Matcher beforeMatcher = beforePattern.matcher(line);
                Matcher codeMatcher = codePattern.matcher(line);

                if (beforeMatcher.matches()) { // If this is a BEFORE line.
                    // Get the BEFORE matches.
                    Memory beforeMemory = lineToMemory(line);

                    // Get the CodeOperation matches.
                    line = br.readLine();
                    CodeOperation codeOperation = lineToCodeOperation(line);

                    // Then get the AFTER matches.
                    line = br.readLine();
                    Memory afterMemory = lineToMemory(line);

                    CodeSample sample = new CodeSample(beforeMemory, codeOperation, afterMemory);
                    codeSamples.add(sample);
                } else if (codeMatcher.matches()) { // else if this line is a "code section" line.
                    CodeOperation codeOperation = lineToCodeOperation(line);
                    programCode.add(codeOperation);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
        return;

    }

    private Memory lineToMemory(String line) {
        Matcher m = Pattern.compile("\\d+")
                .matcher(line);
        Memory memory = new Memory();
        // Get the Before matches and put them in a register state.
        for (int i = 0; i < Register.values().length; i++) {
            m.find();
            memory.setRegisterValue(Register.intToEnum(i), Integer.parseInt(m.group()));
        }
        return memory;
    }

    private CodeOperation lineToCodeOperation(String line) {
        List<Integer> co = new ArrayList<>();
        Matcher m = Pattern.compile("\\d+")
                .matcher(line);
        while (m.find()) {
            co.add(Integer.parseInt(m.group()));
        }

        CodeOperation codeOperation = new CodeOperation(
                co.get(0),
                co.get(1),
                co.get(2),
                co.get(3));

        return codeOperation;
    }

    public ArrayList<CodeSample> getCodeSamples() {
        return codeSamples;
    }

    public void setCodeSamples(ArrayList<CodeSample> codeSamples) {
        this.codeSamples = codeSamples;
    }
}
