package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

/**
 * Model osztály, amely reprezentál egy feladatot.
 */
public class Task {
    /** A feladat leírása. */
    private final SimpleStringProperty taskDesc;
    /** A feladat fontossága. */
    private final SimpleStringProperty priority;
    /** A feladat dátuma. */
    private LocalDate taskDate;
    /** A feladat kész van  e. */
    private final BooleanProperty done;

    /**
     * Alapértelmezett konstruktor.
     */
    public Task() {
        this.taskDesc = new SimpleStringProperty("");
        this.priority = new SimpleStringProperty("");
        this.taskDate = LocalDate.now();
        this.done = new SimpleBooleanProperty();
    }

    /**
     * Paraméterezett konstruktor.
     *
     * @param taskDesc a feladat leirása
     * @param priority a feladat fontossága
     * @param taskDate a feladat dátuma
     */
    public Task(String taskDesc, String priority, LocalDate taskDate) {
        this.taskDesc = new SimpleStringProperty(taskDesc);
        this.priority = new SimpleStringProperty(priority);
        this.done = new SimpleBooleanProperty(false);
        this.taskDate = taskDate;
    }

    /**
     * Paraméterezett konstruktor.
     *
     * @param taskDesc a feladat leirása
     * @param priority a feladat fontossága
     * @param taskDate a feladat dátuma
     * @param done a feladat kész van e
     */
    public Task(String taskDesc, String priority, LocalDate taskDate, boolean done) {
        this.taskDesc = new SimpleStringProperty(taskDesc);
        this.priority = new SimpleStringProperty(priority);
        this.done = new SimpleBooleanProperty(done);
        this.taskDate = taskDate;
    }


    /**
     * Visszaadja a feladat leírását.
     * @return a feladat leírása
     */
    public String getTaskDesc() {
        return taskDesc.get();
    }

    /**
     * Beállítja a feladat leírását.
     * @param inTask a feladat leírása
     */
    public void setTaskDesc(String inTask) {
        taskDesc.set(inTask);
    }

    /**
     * Visszaadja a feladat fontosságát.
     * @return a feladat fontossága
     */
    public String getPriority() {
        return priority.get();
    }

    /**
     * Beállítja a feladat fontosságát.
     * @param inPriority a feladat fontossága
     */
    public void setPriority(String inPriority) {
        priority.set(inPriority);
    }

    /**
     * Visszaadja a feladat dátumát.
     * @return feladat dátuma
     */
    public LocalDate getTaskDate() {
        return taskDate;
    }

    /**
     * Beállítja a feladat dátumát.
     * @param taskDate a feladat dátuma
     */
    public void setTaskDate(LocalDate taskDate) {
        this.taskDate = taskDate;
    }

    /**
     * Visszaadja a <code>done</code> változó értékét.
     * @return boolean típusú változó
     */
    public boolean isDone() {
        return done.get();
    }


    /**
     * Visszaadja, hogy kész-e a feladat.
     * @return BooleanProperty típusú változó
     */
    public BooleanProperty doneProperty() {
        return done;
    }

    /**
     * Beállítja, hogy kész van e a feladat.
     * @param done boolean típusú változó
     */
    public void setDone(boolean done) {
        this.done.set(done);
    }

    /**
     * A feladat toString metódisa, leirja a feladat adatait.
     * @return a feladat leírása, a feladat fontossága, a feladat dátuma, a feladat kész van e
     */
    @Override
    public String toString() {
        return "Task{" +
                "taskDesc=" + taskDesc.getValue() +
                ", priority=" + priority.getValue() +
                ", taskDate=" + taskDate +
                ", done=" + done.getValue() +
                '}';
    }

}
