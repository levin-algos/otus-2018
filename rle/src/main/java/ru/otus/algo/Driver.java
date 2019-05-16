package ru.otus.algo;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
text:
java -jar rle.jar -enc -s 1.txt -d 1-enc.txt
Encoding 1.txt and save to 1-enc.txt.
Time for operation: 19.33ms.
Source size:       13 bytes.
Dest size:         26 bytes.

java -jar rle.jar -dec -s 1-enc.txt -d 1-dec.txt
Decoding 1-enc.txt and save to 1-dec.txt.
Time for operation: 19.24ms.
Source size:       26 bytes.
Dest size:         13 bytes.

image:
java -jar rle.jar -enc -s 1.jpg -d 1-enc.jpg
Encoding 1.jpg and save to 1-enc.jpg.
Time for operation: 243.69ms.
Source size:  2749528 bytes.
Dest size:    5473072 bytes.

java -jar rle.jar -dec -s 1-enc.jpg -d 1-dec.jpg
Decoding 1-enc.jpg and save to 1-dec.jpg.
Time for operation: 217.86ms.
Source size:  5473072 bytes.
Dest size:    2749528 bytes.

executable:
java -jar rle.jar -enc -s NM34_x86.exe -d NM34_x86.enc
Encoding NM34_x86.exe and save to NM34_x86.enc.
Time for operation: 418.90ms.
Source size:  6407992 bytes.
Dest size:   12727368 bytes.

java -jar rle.jar -dec -s NM34_x86.enc -d NM34_x86-dec.exe
Decoding NM34_x86.enc and save to NM34_x86-dec.exe.
Time for operation: 389.58ms.
Source size: 12727368 bytes.
Dest size:    6407992 bytes.

video:
java -jar rle.jar -enc -s 5b60c67.mp4 -d 5b60c67.enc
Encoding 5b60c67.mp4 and save to 5b60c67.enc.
Time for operation: 96.04ms.
Source size:   477440 bytes.
Dest size:     948168 bytes.

java -jar rle.jar -dec -d 5b60c67-dec.mp4 -s 5b60c67.enc
Decoding 5b60c67.enc and save to 5b60c67-dec.mp4.
Time for operation: 88.59ms.
Source size:   948168 bytes.
Dest size:     477440 bytes.
 */

public class Driver {

    private Options options;
    private CommandLine cmd;

    public static void main(String[] args) {
        final Driver driver = new Driver();
        try {
            if (driver.addArgs(args)) {
                driver.convert();
            }
        } catch (ParseException e) {
            System.out.println("Parse error, cause: " + e.getCause());
        }
    }

    private boolean addArgs(String[] args) throws ParseException {
        this.options = generateOptions();
        CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(generateOptions(), args);
        return validateArgs();
    }

    private void convert() {
        Path source = Paths.get(cmd.getOptionValue("s"));
        Path dest = Paths.get(cmd.getOptionValue("d"));
        boolean isEncoding = cmd.hasOption("enc");
        boolean isDecoding = cmd.hasOption("dec");

        if (isEncoding && isDecoding) {
            System.out.println("Decoding and encoding simultaneously");
            printUsage();
            return;
        }

        if (Files.exists(dest)) {
            System.out.println("Destination file already exists. Remove the file or change the destination.");
            printUsage();
            return;
        }


        long time = System.nanoTime();
        if (isEncoding) {
            RLE.encode(source, dest);
        }
        else if (isDecoding) {
            RLE.decode(source, dest);
        } else {
            System.out.println("Decoding or encoding flags must be specified");
            printUsage();
            return;
        }
        time = System.nanoTime() - time;

        printStatistics(time, source, dest, isDecoding? "Decoding" : "Encoding");

        if (cmd.hasOption("c")) {
            try {
                System.out.println("Delete destination file.");
                Files.delete(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printStatistics(long time, Path source, Path dest, String mode) {
        System.out.println(String.format(mode + " %s and save to %s.", source, dest));
        System.out.println(String.format("Time for operation: %.2fms.", (float)time/1_000_000));
        try {
            System.out.println(String.format("Source size: %8d bytes.", Files.size(source)));
            System.out.println(String.format("Dest size: %10d bytes.", Files.size(dest)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean validateArgs() {
        if (!cmd.hasOption("s") || !cmd.hasOption("d")) {
            printUsage();
            return false;
        }
        return true;
    }

    private void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("rle", options);
    }

    private Options generateOptions() {
        final Options options = new Options();
        options.addOption("s", "source", true, "file to compress");
        options.addOption("d", "destination", true, "compressed filename");
        options.addOption("enc", "encode", false, "encoding source file");
        options.addOption("dec", "decode", false, "decoding source file");
        options.addOption("c", "clean", false, "wipe destination file at the end of the program");

        return options;
    }
}
