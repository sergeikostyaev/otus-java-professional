package ru.otus;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

public class Main {
    public static String FIRST_THREAD = "FIRST";
    public static String SECOND_THREAD = "SECOND";
    private String currentThread = "SECOND";

    private boolean addition = true;


    public static void main(String[] args) throws Exception {
        Main main = new Main();

        Thread thread1 = new Thread(() -> main.action(FIRST_THREAD));
        Thread thread2 = new Thread(() -> main.action(SECOND_THREAD));
        thread1.setName("thread 1");
        thread2.setName("thread 2");

        thread2.start();
        thread1.start();

    }

    public synchronized void action(String message) {
        int number = 1;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (currentThread.equals(message)) {
                    wait();
                }

                System.out.println(Thread.currentThread().getName() + ": " + number);
                sleep();

                if(number == 10){
                    addition = false;
                }else if(number == 1){
                    addition = true;
                }

                if (addition) {
                    number++;
                } else {
                    number--;
                }

                currentThread = message;

                notify();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}