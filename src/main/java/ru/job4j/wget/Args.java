package ru.job4j.wget;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Ilya Devyatkov
 * @since 30.03.2020
 */
public class Args {
    private String path;
    private Integer speed;
    private String output;

    /**
     * Парсинг всех параметров и запись их в поля
     * @param parametrs
     */
    public void parseValues(String[] parametrs) {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
           parametrs = br.readLine().split("\\s");
            path = parsePath(parametrs);
            speed = parseSpeed(parametrs);
            output = parametrs[parametrs.length - 1];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Парсит путь до файла в сети, который нужно скачать
     * @param params
     * @return путь
     */
    private String parsePath(String[] params) {
        String result = null;
        for(String val : params) {
            if (val.contains("http")) {
                result = val;
            }
        }
        return result;
    }

    /**
     * Парсит скорость скачивания
     * @param params
     * @return скорость
     */
    private Integer parseSpeed(String[] params) {
        Integer result = null;
        for(String val : params) {
            if (val.chars().allMatch(Character::isDigit)) {
                result = Integer.parseInt(val);
            }
        }
        return result;
    }

    public String getPath() {
        return path;
    }

    public Integer getSpeed() {
        return speed;
    }

    public String getOutput() {
        return output;
    }
}
