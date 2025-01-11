package org.example;

import javazoom.jl.player.Player;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

public class DownloadAudio {

    public static void main(String[] args) {

        System.out.println("Script to save audio files... ");
        Scanner scanner = new Scanner(System.in);

        List<String> lines;
        try {

            ClassLoader classLoader = DownloadAudio.class.getClassLoader();
//            URL resource = classLoader.getResource("words/one_bee_school.txt");
//            URL resource = classLoader.getResource("words/two_bee_school.txt");
//            URL resource = classLoader.getResource("words/three_bee_school.txt");
//            URL resource = classLoader.getResource("words/one_bee_champions.txt");
//            URL resource = classLoader.getResource("words/two_bee_champions.txt");
            URL resource = classLoader.getResource("words/three_bee_champions.txt");
            File file = new File(resource.toURI());

            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            lines.forEach(line -> {

//                System.out.println("Press enter to read the next line");
//                scanner.nextLine();
                System.out.println(line);
//                runAudioFile(line);
                saveAudio(line, "https://api.dictionaryapi.dev/media/pronunciations/en/" + line + "-us.mp3");
//                runAudio(line, "https://api.dictionaryapi.dev/media/pronunciations/en/" + line + "-us.mp3");

            });


        } catch (Exception ex) {
            System.out.println("Error reading file");
        }
    }

    public static void runAudioFile(String word) {

        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/" + word); BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

            Player player = new Player(bufferedInputStream);
            player.play();

        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void runAudio(String word, String audio) {

        try {

            URL audioUrl = new URL(audio);
            InputStream inputStream = audioUrl.openStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            File targetFile = new File("src/main/resources/" + word);
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(bufferedInputStream.readAllBytes());

            Player player = new Player(bufferedInputStream);
            player.play();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void saveAudio(String word, String audio) {

        try {

            File f = new File("src/main/resources/" + word + ".mp3");
            if (f.exists() && !f.isDirectory()) {
                return;
            }

            URL audioUrl = new URL(audio);
            InputStream inputStream = audioUrl.openStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            File targetFile = new File("src/main/resources/" + word + ".mp3");
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(bufferedInputStream.readAllBytes());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}