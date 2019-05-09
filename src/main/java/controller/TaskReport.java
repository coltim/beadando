package controller;

import javafx.collections.ObservableList;
import model.Task;

import java.time.LocalDate;
import java.util.List;

public class TaskReport {
    public static int todayTasksNumber(List<Task> tasks){

        LocalDate dateNow = LocalDate.now();
        int count = 0;
        for (Task t : tasks) {
            if (t.getTaskDate().getDayOfYear() == dateNow.getDayOfYear()) {
                count++;
            }
        }
        return count;
    }

    public static int highPriorityTasksNumber(List<Task> tasks){

        int count = 0;
        for (Task t : tasks) {
            if (t.getPriority().equals("Magas")) {
                count++;
            }
        }
        return count;
    }
}
