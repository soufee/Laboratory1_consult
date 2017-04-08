import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Shoma on 06.04.2017.
 */
public class TextReader extends Thread{
private String fileName;

private ArrayList<String>words = new ArrayList<>();

public TextReader(String fileName)
{
    this.fileName = fileName;
}



    public ArrayList<String> getWords() {
        return words;
    }

    @Override
    public void run() {
        try {

           Scanner reader;
            //BufferedReader urlReader = new BufferedReader(new InputStreamReader(new URL(fileName)));
            URL url;
            if ((fileName.startsWith("http://")) || (fileName.startsWith("ftp://")) || (fileName.startsWith("https://"))) {
                url = new URL(fileName);
          reader= new Scanner(url.openStream(),"UTF-8");
                System.out.println("Читаем с файла "+fileName);
            } else if (fileName.endsWith("txt"))
                //читаем с текстового файла
                reader = new Scanner(new File(fileName), "UTF-8");
            else {
                reader=null;
                System.out.println("Несоответствующий тип файла");
            }

                while (reader.hasNext()) {
                    words.add(reader.nextLine());
                    Thread.yield();
                }

            } catch(UnsupportedEncodingException e){
                e.printStackTrace();
            } catch(FileNotFoundException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            String s = symbTrimmer(words.get(i));
            words.set(i, s);
            String[] str = (words.get(i).split(" "));
            for (int j = 0; j < str.length; j++) {
                if ((str[j] != null) && (str[j] != "") && (str[j] != " "))
                    list.add(str[j]);
            }
        }

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).equals("")) {
                list.remove(i);
                i--;
            }

        }

boolean flag = false;
        synchronized (Main.uniqueWords) {
            for (int i = 0; i < list.size(); i++) {
                if (Main.uniqueWords.contains(list.get(i).toLowerCase())) {
                    synchronized (System.out) {
                        System.out.println("Слово '" + list.get(i) + "' уже встречалось в списке.");
                        System.out.println("Поток " + Thread.currentThread().getName() + ", работающий с файлом '" +
                                fileName+"', прекращается досрочно");
                        System.out.println();
                        flag = true;
                        break;
                    }
                } else {
                    Main.uniqueWords.add(list.get(i).toLowerCase());
                    list.get(i).toLowerCase();
                }

            }
            if (!flag){
            synchronized (System.out) {

                System.out.println("Поток " + Thread.currentThread().getName() + " отработал без ошибок. " +
                        "Повторяющихся слов в источнике нет");
                System.out.println();
            }}



        }
    }
    public String symbTrimmer(String string) {
        return string.replaceAll("[^а-яА-Я0-9\\s]", "");
    }


}


