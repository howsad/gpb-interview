package ru.gpb.interview.generator;

import ru.gpb.interview.SaleDetails;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.stream.Stream;

class SaleDetailsFileGenerator {

    private final SaleDetailsStreamGenerator streamGenerator;

    SaleDetailsFileGenerator(SaleDetailsStreamGenerator streamGenerator) {
        this.streamGenerator = streamGenerator;
    }

    void generateToFile(LocalDateTime referenceDate, GeneratorSettings generatorSettings) {
        try {
            Stream<String> outputStream = streamGenerator.generateOutputStream(
                    referenceDate,
                    Files.readAllLines(generatorSettings.getSellingPointNamesFilePath()),
                    generatorSettings.getOutputLinesCount()
            ).map(SaleDetails::toOutputString)
                    .parallel();
            @SuppressWarnings("NullableProblems")
            Iterable<String> outputLines = outputStream::iterator;
            Files.write(generatorSettings.getOutputFilePath(), outputLines);
        } catch (IOException e) {
            throw new RuntimeException("Произошла ошибка при обращении к файлу.", e);
        }
    }

}
