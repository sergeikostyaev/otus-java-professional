package ru.otus;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Cell implements Comparable<Cell>{
    private final int value;

    private int sum;

    private boolean isEmpty;

    private final List<Banknote> list;

    public Cell(int value) {
        this.value = value;
        list = new ArrayList<>();
        isEmpty = true;
    }

    public void putBanknote(Banknote b){
        isEmpty = false;
        if(b.getValue() == value){
            sum+=value;
            list.add(b);
        }
    }

    public Banknote takeBanknote(){
        if(list.size() != 0){
            Banknote b = list.get(0);
            list.remove(0);
            if(list.isEmpty()){
                isEmpty = true;
            }
            return b;
        }else{
            isEmpty = true;
            return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.getValue());
    }

    @Override
    public int compareTo(Cell o) {
        return o.getValue()-value;
    }
}
