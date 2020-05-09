package ru.vtb.afsc.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

/**
 * Разбор аргументов командной строки
 *
 * ArgumentParser cmd = new ArgumentParser(args);
 * if (cmd.has(ArgumentParser.Option.CONFIGURATION_FILE)) {
 *
 *     // есть параметр конфигурационного файла
 *     String pathFile = cmd.get(ArgumentParser.Option.CONFIGURATION_FILE);
 * }
 *
 * @author semenov
 */
public class ArgumentParser {
    public enum Option {
        CONFIGURATION_FILE("config", "c"),
        HELP("help", "h");

        final public String longName;
        final public String shortName;

        Option(final String longName, final String shortName) {
            this.longName = longName;
            this.shortName = shortName;
        }
    }

    final static private org.apache.commons.cli.Options options =
        new org.apache.commons.cli.Options();

    static {
        options.addOption(
            org.apache.commons.cli.Option.builder(Option.CONFIGURATION_FILE.shortName)
                .longOpt(Option.CONFIGURATION_FILE.longName)
                .hasArgs()
                .desc("Configuration file" )
                .build()
        );

        options.addOption(
            org.apache.commons.cli.Option.builder(Option.HELP.shortName)
                .longOpt(Option.HELP.longName)
                .desc("Show start arguments" )
                .build()
        );
    }

    final private CommandLine cmd;

    public ArgumentParser(final String[] args) {
        CommandLine cmdLocal;
        final CommandLineParser parser = new DefaultParser();

        try {
            cmdLocal = parser.parse(options, args);
        } catch (final ParseException ex) {
            ex.printStackTrace();

            cmdLocal = null;
        }
        cmd = cmdLocal;
    }

    public String get(final Option opt) {
        return cmd != null ? cmd.getOptionValue(opt.longName) : null;
    }

    public boolean has(final Option opt) {
        return cmd != null && cmd.hasOption(opt.longName);
    }

    public void man() {
        final HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("CLITester", options);
    }
}
