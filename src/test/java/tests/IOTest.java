package tests;

import IO.IOHandler;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class IOTest {



    List<Task> testTasks = new ArrayList<Task>() {};
    List<Task> testInputTasks = null;

    Task task1 = new Task("feladat", "Magas", LocalDate.now(), false);
    Task task2 = new Task("proba", "Alacsony", LocalDate.of(2019, Month.JANUARY, 1), true);



    @BeforeEach
    void setUp() {
        testTasks.add(task1);
        testTasks.add(task2);
    }

    @AfterEach
    void tearDown(){
        testTasks.clear();
    }


   @Test
    public void XMLWriterTest1(){

        //megnezi hogy letrehozza e az xml filet

        File directory = new File(new File("testTasks.xml").getAbsolutePath());
        File file = new File("testTasks.xml");
        IOHandler.XmlWriter(directory,testTasks);

        assertTrue(file.exists());

        file.deleteOnExit();
    }

    @Test
    public void XMLWriterTest2(){

        //megnezi hogy a letrehozott xml file nem ures

        File directory = new File(new File("testTasks.xml").getAbsolutePath());
        File file = new File("testTasks.xml");
        IOHandler.XmlWriter(directory,testTasks);

        assertTrue(file.length()>0);

        file.deleteOnExit();
    }

}
