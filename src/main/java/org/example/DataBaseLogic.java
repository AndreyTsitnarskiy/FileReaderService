package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataBaseLogic {

    private Session session = new HibernateConnection().getSessionFactory().getCurrentSession();
    private ParserFile parserFile = new ParserFile();
    private File file = new File("data/say.txt");
    private final Logger LOGGER = LogManager.getLogger("phrase");
    private Transaction transaction = session.beginTransaction();

    private void addPhrase() {
        LOGGER.info("Добавляем записи в базу - nameMethod - addPhrase");
        int count = 0;
        List<String> parser = parserFile.parseFile(file);
        List<String> phrases = parserFile.returnPhrases(parser);
        List<String> authors = parserFile.returnAuthor(parser);
        if (checkFile(parser)) {
            LOGGER.info("В фалйле нет записей - nameMethod - addPhrase");
        }
        for (int i = 0; i < phrases.size(); i++) {
            count++;
            Phrase phrase = new Phrase(phrases.get(i), authors.get(i));
            session.save(phrase);
        }
        transaction.commit();
        session.close();
        LOGGER.info("Записей добавлено: " + count + " nameMethod - addPhrase");
    }

    private long countRowsTablePhrase() {
        long result;
        result = (long) session.createQuery("select count(*) from Phrase").getSingleResult();
        LOGGER.info("В таблице phrase " + result + " записей");
        return result;
    }

    private long countRowsTableOldPhrase() {
        long result;
        result = (long) session.createQuery("select count(*) from OldPhrase").getSingleResult();
        LOGGER.info("В таблице old_phrase " + result + " записей");
        return result;
    }

    private void transferDataBase() {
        List<OldPhrase> phrases = session.createQuery("from OldPhrase").getResultList();
        LOGGER.info("Собрано для переноса " + phrases.size() + " записей");
        for (OldPhrase phrase : phrases) {
            Phrase phraseNew = new Phrase(phrase.getPhrase(), phrase.getAuthor());
            session.save(phraseNew);
            session.delete(phrase);
        }
        transaction.commit();
        LOGGER.info("Данные перенесены nameMethod - transferDataBase");
    }

    public void updateDataBase() {
        if (countRowsTablePhrase() == 0 && countRowsTableOldPhrase() > 0) {
            LOGGER.info("База данных пуста небходим перенос данных nameMethod - updateDataBase");
            LOGGER.info("Начинаю перенос данных nameMethod - updateDataBase");
            transferDataBase();
        } else {
            addPhrase();
        }
    }

    private boolean checkFile(List<String> list) {
        return list.size() == 0 ? true : false;
    }
}
