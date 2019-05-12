package controller;

import model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * A feladatokhoz kapcsolódó számolásokat végző osztály.
 */
public class TaskReport {

    /**
     * Példányosított Logger osztály.
     */
    private static Logger logger = LoggerFactory.getLogger(TaskReport.class);


    /**
     * Visszaadja a mai feladatok számát.
     * @param tasks beérkező feladatok listája
     * @return a mai feladatok száma
     */
    public static int todayTasksNumber(List<Task> tasks){

        LocalDate dateNow = LocalDate.now();
        int count = 0;
        for (Task t : tasks) {
            if (t.getTaskDate().getDayOfYear() == dateNow.getDayOfYear()) {
                count++;
            }
        }
        logger.info("Mai feladatok ujraszamolasa");
        return count;
    }

    /**
     * Visszaadja a fontos feladatok számát.
     * @param tasks beérkező feladatok listája
     * @return a fontos feladatok száma
     */
    public static int highPriorityTasksNumber(List<Task> tasks){

        int count = 0;
        for (Task t : tasks) {
            if (t.getPriority().equals("Magas")) {
                count++;
            }
        }
        logger.info("Magas prioritasu feladatok ujraszamolasa");
        return count;
    }
}
