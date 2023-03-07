package ru.otus;


import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.TreeSet;

@RequiredArgsConstructor
public class ATM {

    private final ArrayList<Integer> relevantBanknoteValues;

    private final ArrayList<Cell> cells;

    private int totalSum;

    private final InputService<Integer> inputService;

    public void addMoney(){
        for(int i : relevantBanknoteValues) {

            System.out.println("Сколько банкнот по " + i + " вы хотите вложить?");
            int number = inputService.read();
            totalSum+=number*i;
            if(number > 0){
                Cell c = findRelevantCell(i);
                for (int j = 0; j < number; j++) {
                    c.putBanknote(new Banknote(i));
                }
            }
        }
    }

    public ArrayList<Banknote> takeMoney(int value) {
        int sum = value;
        ArrayList<Banknote> result = new ArrayList<>();
        TreeSet<Cell> treeSet = new TreeSet<>(cells);

        if(totalSum < value) throw new RuntimeException("Not enough money");

        for (int i = 0; i < cells.size(); i++) {
            Cell c = treeSet.pollFirst();
            while(!c.isEmpty() && value >= c.getValue() && sum != 0){
                result.add(c.takeBanknote());
                sum-=c.getValue();
                totalSum-=c.getValue();
            }
        }
        System.out.printf("Остаток в банкомате: %s\nВаши купюры:\n" + result ,totalSum);
        return result;
    }
    private Cell findRelevantCell(int value){
        for(Cell c : cells){
            if(c.getValue() == value){
                return c;
            }
        }
        return null;
    }
}
