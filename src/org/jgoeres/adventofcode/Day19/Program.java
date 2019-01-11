package org.jgoeres.adventofcode.Day19;

import org.jgoeres.adventofcode.Day19.CodeOperation;
import org.jgoeres.adventofcode.Day19.Memory;
import org.jgoeres.adventofcode.Day19.Register;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {
    Integer ip = null; // instruction pointer mapping.
    private ArrayList<CodeOperation> programCode = new ArrayList<>();

    public Program(String pathToFile) {
        loadProgramCode(pathToFile);
    }

    private void loadProgramCode(String pathToFile) {
        /*
        File looks like:
        #ip 5
        addi 5 16 5
        seti 1 2 2
        seti 1 0 4
        mulr 2 4 3
        eqrr 3 1 3
        addr 3 5 5
         */

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {   // Read in the program code line by line.
                if (ip == null) {
                    // The first line is the instruction pointer setting.
                    Matcher ipMatcher = Pattern.compile("\\d").matcher(line);
                    if (ipMatcher.find()) {
                        ip = Integer.parseInt(ipMatcher.group());
                    }
                } else {
                    // The rest of the program is code.
                    CodeOperation codeOperation = lineToCodeOperation(line);
                    programCode.add(codeOperation);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
        return;

    }

    private CodeOperation lineToCodeOperation(String line) {
        List<Integer> co = new ArrayList<>();
        Matcher opCodeMatcher = Pattern.compile("[a-z]{4}").matcher(line);
        Matcher m = Pattern.compile("\\d+")
                .matcher(line);
        OpCode opCode = null;

        if (opCodeMatcher.find()) {
            opCode = OpCode.get(opCodeMatcher.group());
        }
        while (m.find()) {
            co.add(Integer.parseInt(m.group()));
        }

        CodeOperation codeOperation = new CodeOperation(
                opCode,
                co.get(0),
                co.get(1),
                co.get(2));

        return codeOperation;
    }

    public Integer getIp() {
        return ip;
    }

    public ArrayList<CodeOperation> getProgramCode() {
        return programCode;
    }
}
