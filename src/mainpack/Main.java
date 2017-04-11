package mainpack;

import java.io.*;
import java.util.*;

/**
 * Created by Shoma on 06.04.2017.
 * Необходимо разработать программу, которая получает на вход список ресурсов, содержащих текст,
 * и проверяет уникальность каждого слова. Каждый ресурс должен быть обработан в отдельном потоке,
 * текст не должен содержать инностранных символов, только кириллица, знаки препинания и цифры.
 * В случае нахождения дубликата, программа должна прекращать выполнение с соответсвующим сообщением.
 * Все ошибки должны быть корректно обработаны, все API покрыто модульными тестами
 */
public class Main {
    public static volatile Set<String>  uniqueWords = new HashSet<>();
    public static ArrayList<String>     resourses   = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("resourse_list.txt"), "UTF-8"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            resourses.add(line);
        }
        Thread[] threads = new Thread[resourses.size()];
        for (int i = 0; i < resourses.size(); i++) {
            threads[i] = new TextReader(resourses.get(i));
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++)
            threads[i].join();

        System.out.println("==============Список уникальных слов================");
        System.out.println(uniqueWords);
    }

}
