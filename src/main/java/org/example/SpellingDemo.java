package org.example;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class SpellingDemo {

    public static void main(String[] args) {

        System.out.println("Welcome to spelling test");
        System.out.println("-------------------------\n\n");

        Scanner scanner = new Scanner(System.in);

        List<String> lines;

        try {

            ClassLoader classLoader = DownloadAudio.class.getClassLoader();

            System.out.println("Here are the difficulty levels: \n\tnew_words \n\tone_bee_school \n\tone_bee_champions \n\ttwo_bee_school \n\ttwo_bee_champions \n\tthree_bee_school \n\tthree_bee_champions\n");
            System.out.println("Select the level name here ==>");

            Scanner filenameScanner = new Scanner(System.in);
            String fileName = filenameScanner.nextLine();

            URL resource = classLoader.getResource("words/" + fileName + ".txt");
            File file = new File(resource.toURI());

            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);

            System.out.println("Number of words: " + lines.size());

            int i = 0;

            System.out.println("\nPress enter to get next word !, or q/Q to quit or r/R for replay again");

            String previousWord;
            for (String word : lines) {

                System.out.print(word);
                runAudioFile(word);

                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("Q")) {
                    System.out.println("\n\n *** You did an awesome job ! *** ");
                    System.out.println("\t\t Finished: " + i + " / " + lines.size());
                    exit(0);

                } else if (input.equalsIgnoreCase("r")) {

                    do {
                        runAudioFile(word);
                        input = scanner.nextLine();
                    } while (input.equalsIgnoreCase("R"));
                }

                i++;
            }

            System.out.println("\n\n *** You did an awesome job ! *** ");
            System.out.println("\t\t Finished: " + i + " / " + lines.size());

        } catch (Exception ex) {
            System.out.println("Error reading audio file: " + ex.getMessage());
        }

    }

    public static void runAudioFile(String word) {

        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/" + word + ".mp3"); BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {


            Player player = new Player(bufferedInputStream);
            player.play();

        } catch (Exception e) {
            System.err.println("\n \tword not found in dictionary api: " + "https://api.dictionaryapi.dev/media/pronunciations/en/" + word + "-us.mp3");
        }
    }
}
