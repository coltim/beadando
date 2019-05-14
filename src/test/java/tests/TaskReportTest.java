package tests;

import model.TaskReport;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskReportTest {


    List<Task> testTasks = new ArrayList<Task>() {};
    List<Task> testInputTasks = null;

    Task task1 = new Task("feladat1", "Magas", LocalDate.now(), false);
    Task task2 = new Task("feladat2", "Alacsony", LocalDate.of(2019, Month.JANUARY, 1), true);
    Task task3 = new Task("feladat3", "Magas", LocalDate.now(), true);
    Task task4 = new Task("feladat4", "Közepes", LocalDate.of(2009, Month.MARCH, 24), false);
    Task task5 = new Task("feladat5", "Közepes", LocalDate.now(), false);


    @BeforeEach
    void setUp() {
        testTasks.add(task1);
        testTasks.add(task2);
        testTasks.add(task3);
        testTasks.add(task4);
        testTasks.add(task5);
    }

    @AfterEach
    void tearDown() {
        testTasks.clear();
    }

    @Test
    public void todayTasksNumberTest1(){
        assertEquals(3, TaskReport.todayTasksNumber(testTasks));
    }

    @Test
    public void todayTasksNumberTest2(){
        testTasks.add(new Task("feladat6", "Magas", LocalDate.now(), false));
        assertEquals(4, TaskReport.todayTasksNumber(testTasks));
    }

    @Test
    public void todayTasksNumberTest3(){
        testTasks.add(new Task("feladat6", "Magas", LocalDate.of(2049, Month.DECEMBER, 31), false));
        assertEquals(3, TaskReport.todayTasksNumber(testTasks));
    }

    @Test
    public void todayTasksNumberTest4(){
        testTasks.clear();
        assertEquals(0, TaskReport.todayTasksNumber(testTasks));
    }

    @Test
    public void highPriorityTasksNumberTest1(){
        assertEquals(2,TaskReport.highPriorityTasksNumber(testTasks));
    }

    @Test
    public void highPriorityTasksNumberTest2(){
        testTasks.add(new Task("feladat6", "Magas", LocalDate.now(), false));
        assertEquals(3,TaskReport.highPriorityTasksNumber(testTasks));
    }

    @Test
    public void highPriorityTasksNumberTest3(){
        testTasks.add(new Task("feladat6", "Alacsony", LocalDate.now(), false));
        assertEquals(2,TaskReport.highPriorityTasksNumber(testTasks));
    }

    @Test
    public void highPriorityTasksNumberTest4(){
        testTasks.clear();
        assertEquals(0,TaskReport.highPriorityTasksNumber(testTasks));
    }

}
