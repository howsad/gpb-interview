package ru.gpb.interview.util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This is small util class for parsing CL args
 */

public class CommandLineUtils {

    public static Map<String, String> parseArgs(String[] args) {
        return Arrays.stream(args)
                .map(arg -> arg.split("="))
                .collect(Collectors.toMap(argsKv -> argsKv[0], argsKv -> argsKv[1]));
    }
}
