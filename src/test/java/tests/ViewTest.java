package tests;

import controller.ViewController;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ViewTest {

    ViewController controller = new ViewController();
    List<Task> testTasks = new ArrayList<Task>() {};
    List<Task> testInputTasks = null;

    Task task1 = new Task("feladat", "Magas", LocalDate.now(), false);
    Task task2 = new Task("proba", "Alacsony", LocalDate.of(2019, Month.JANUARY, 1), true);


    @BeforeEach
    void setUp() {
        testTasks.add(task1);
        testTasks.add(task2);
    }

    @Test
    public void addNewTaskTest(){



    }
}
