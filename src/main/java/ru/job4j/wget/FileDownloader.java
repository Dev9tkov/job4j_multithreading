package ru.job4j.wget;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * @author Ilya Devyatkov
 * @since 30.03.2020
 */
public class FileDownloader {

    public static void main(String[] args) {
        Args keys = new Args();
        keys.parseValues(args);
        String file = keys.getPath();
        Integer speed = keys.getSpeed();
        String output = keys.getOutput();

        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(output)) {

            int delay = 0;
            double sec = (double) 1024 / speed;
            if (sec > 1) {
                delay = 1024 / speed;
            }

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                System.out.println(bytesRead);
                Thread.sleep(delay);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
