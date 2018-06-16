package ru.gpb.interview.generator;

import ru.gpb.interview.util.CommandLineUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

class GeneratorSettings {

    private final Path sellingPointNamesFilePath;
    private final Path outputFilePath;
    private final long outputLinesCount;

    private GeneratorSettings(Path sellingPointNamesFilePath,
                              Path outputFilePath,
                              long outputLinesCount) {
        this.sellingPointNamesFilePath = sellingPointNamesFilePath;
        this.outputFilePath = outputFilePath;
        this.outputLinesCount = outputLinesCount;
    }

    static GeneratorSettings parseCommandLineArgs(String[] args) {
        Map<String, String> keyValueArgs = CommandLineUtils.parseArgs(args);
        String namesPath = keyValueArgs.get("namesPath");
        if (namesPath == null) {
            throw new RuntimeException("Не задан путь к файлу с номерами точек продаж (параметр namesPath)");
        }
        String outputPath = keyValueArgs.get("outputPath");
        if (outputPath == null) {
            throw new RuntimeException("Не задан путь к файлу с выводом (параметр outputPath)");
        }
        String linesCount = keyValueArgs.get("linesCount");
        if (linesCount == null) {
            throw new RuntimeException("Не задано число строк для вывода (параметр linesCount)");
        }
        Path sellingPointNamesFilePath = Paths.get(namesPath);
        Path outputFilePath = Paths.get(outputPath);
        Integer outputLinesCount = Integer.valueOf(linesCount);
        return new GeneratorSettings(
                sellingPointNamesFilePath,
                outputFilePath,
                outputLinesCount
        );
    }

    Path getSellingPointNamesFilePath() {
        return sellingPointNamesFilePath;
    }

    Path getOutputFilePath() {
        return outputFilePath;
    }

    long getOutputLinesCount() {
        return outputLinesCount;
    }
}
