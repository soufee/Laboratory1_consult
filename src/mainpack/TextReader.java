package mainpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Shoma on 06.04.2017.
 */
public class TextReader extends Thread {
    private String fileName;
    private ArrayList<String> words = new ArrayList<>();

    public TextReader(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    @Override
    public void run() {

        addToSet(readFromFile(fileName));

    }

    public ArrayList<String> readFromFile(String fileName) {
        try {

            Scanner reader;
            URL url;
            if ((fileName.startsWith("http://")) || (fileName.startsWith("ftp://")) || (fileName.startsWith("https://"))) {
                //читаем с интернет страницы
                url = new URL(fileName);
                reader = new Scanner(url.openStream(), "UTF-8");
                //     System.out.println("Читаем с файла " + fileName);
            } else

                //читаем с текстового файла
                reader = new Scanner(new File(fileName), "UTF-8");
            System.out.println("Читаем с файла " + fileName);
            while (reader.hasNext()) {
                words.add(reader.nextLine());
                Thread.yield();
            }

        } catch (UnsupportedEncodingException e) {
            System.out.println("Ошибка чтения файла " + fileName + ". Не поддерживается кодировка. \n");

            // e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            System.out.println("Файл " + fileName + " не найден\n");
            //  e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла " + fileName + "\n");
            //e.printStackTrace();
            return null;
        }

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            //  String s = symbTrimmer(words.get(i));
            String s = words.get(i);
            words.set(i, s);
            String[] str = (words.get(i).split(" "));
            for (int j = 0; j < str.length; j++) {
                if ((str[j] != null) && (str[j] != "") && (str[j] != " "))
                    list.add(delPunct(str[j].trim()));
            }
        }

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).equals("")) {
                list.remove(i);
                i--;
            }

        }
        return list;
    }

    public void addToSet(List<String> list) {
        boolean flag = false;
        if (list != null) {
            synchronized (Main.uniqueWords) {
                for (int i = 0; i < list.size(); i++) {

                    String string = delPunct(list.get(i));
                    if (Main.uniqueWords.contains(string.toLowerCase().trim()) || (symbContain(string.trim()))) {
                        synchronized (System.out) {
                            System.out.println("Слово '" + string + "' уже встречалось в списке или в слове недопустимый символ");
                            System.out.println("Поток " + Thread.currentThread().getName() + ", работающий с файлом '" +
                                    fileName + "', прекращается досрочно");
                            System.out.println();
                            flag = true;
                            break;
                        }
                    } else {
                        Main.uniqueWords.add(string);
                        // list.get(i).toLowerCase();
                    }

                }
                if (!flag) {
                    synchronized (System.out) {

                        System.out.println("Поток " + Thread.currentThread().getName() + " отработал без ошибок. " +
                                "Повторяющихся слов в источнике нет");
                        System.out.println();
                    }
                }


            }
        }
    }


    public boolean symbContain(String string) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')))
                return true;
        }
        return false;
    }

    public String delPunct(String string) {
        if ((string.endsWith(".")) || (string.endsWith(",")) || (string.startsWith("\"")) || (string.endsWith("\""))) {
            string = string.replace(".", "");
            string = string.replace(",", "");
            string = string.replace("\"", "");

        }
        return string;
    }

}


