package ru.otus;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        InputService<Integer> inputService = new UserConsoleInput();

        ArrayList<Integer> relevantBanknotes = new ArrayList<>(List.of(50,100,500,1000));

        Cell cell50 = new Cell(50);
        Cell cell100 = new Cell(100);
        Cell cell500 = new Cell(500);
        Cell cell1000 = new Cell(1000);

        ArrayList<Cell> cells = new ArrayList<>(List.of(cell50,cell100,cell500,cell1000));

        ATM atm = new ATM(relevantBanknotes,cells,inputService);

        atm.addMoney();

        atm.takeMoney(500);







    }

}