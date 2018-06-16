package ru.gpb.interview.grouper;

import ru.gpb.interview.util.CommandLineUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

class GrouperSettings {

    private final Path inputFilePath;
    private final Path outputFilePath;

    private GrouperSettings(Path inputFilePath, Path outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    Path getInputFilePath() {
        return inputFilePath;
    }

    Path getOutputFilePath() {
        return outputFilePath;
    }

    static GrouperSettings parseCommandLineArgs(String[] args) {
        Map<String, String> keyValueArgs = CommandLineUtils.parseArgs(args);
        String inputPath = keyValueArgs.get("inputPath");
        if (inputPath == null) {
            throw new RuntimeException("Не задан путь к файлу с данными о продажах (параметр inputPath)");
        }
        String outputPath = keyValueArgs.get("outputPath");
        if (outputPath == null) {
            throw new RuntimeException("Не задан путь к файлу с выводом (параметр outputPath)");
        }
        return new GrouperSettings(Paths.get(inputPath), Paths.get(outputPath));
    }

}
