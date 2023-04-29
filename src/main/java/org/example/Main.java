package org.example;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger("phrase");

    public static void main(String[] args) {

        DataBaseLogic dataBaseLogic = new DataBaseLogic();
        dataBaseLogic.updateDataBase();
        LOGGER.info("------------------------Заверение работы------------------------");
    }
}