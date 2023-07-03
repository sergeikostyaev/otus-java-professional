package ru.otus;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

public class Main {

    int counter = 0;
    public synchronized void run() throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            Thread.sleep(1000);
            notify();
            wait();
        }

        for (int i = 9; i >= 0; i--) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            Thread.sleep(1000);
            notify();
            wait();
        }
    }




    public static void main(String[] args) {

        Main main = new Main();

        Thread thread1 = new Thread(new Thread1(main));
        Thread thread2 = new Thread(new Thread2(main));

        thread1.setName("thread1");
        thread2.setName("thread2");

        thread1.start();
        thread2.start();

    }
}

@RequiredArgsConstructor
class Thread1 extends Thread{
    final Main main;

    @SneakyThrows
    @Override
    public void run() {
        main.run();
    }
}

@RequiredArgsConstructor
class Thread2 extends Thread{
    final Main main;

    @SneakyThrows
    @Override
    public void run() {
        main.run();
    }
}