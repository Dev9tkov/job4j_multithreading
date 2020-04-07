package ru.job4j.io;

import java.io.*;

/**
 * @author Ilya Devyatkov
 * @since 06.04.2020
 */
public class ParseFile {
    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() {
        StringBuilder sb = new StringBuilder();
        try(InputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) > 0) {
                sb.append((char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public synchronized String getContentWithoutUnicode() {
        StringBuilder sb = new StringBuilder();
        try(InputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) > 0) {
                if (data < 0x80) {
                    sb.append((char) data);
                }
            }
        } catch (IOException e ) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public synchronized void saveContent(String content) {
        try(OutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
