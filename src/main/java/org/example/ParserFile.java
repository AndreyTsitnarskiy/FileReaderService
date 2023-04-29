package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParserFile {

    private final Logger LOGGER = LogManager.getLogger("phrase");

    public List<String> parseFile(File file) {
        List<String> resultParseFile = new ArrayList<>();
        try {
            if (!file.exists()) {
                LOGGER.info("Файл не существует");
                return resultParseFile;
            }
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                resultParseFile.add(line);
            }
            br.close();
            Thread.sleep(1000);
            deleteFile(file);
        } catch (Exception e) {
            LOGGER.info("Ошибка при чтении файла");
            e.printStackTrace();
        }
        return resultParseFile;
    }


    public List<String> returnPhrases(List<String> list) {
        List<String> phrase = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i % 2 != 0) {
                phrase.add(list.get(i));
            }
        }
        return phrase;
    }

    public List<String> returnAuthor(List<String> list) {
        List<String> author = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0) {
                author.add(list.get(i));
            }
        }
        return author;
    }

    private boolean deleteFile(File file) {
        boolean result = file.delete();
        if(result){
            LOGGER.info("Файл удален");
        } else {
            LOGGER.info("Файл не удален");
        }
        return result;
    }
}
