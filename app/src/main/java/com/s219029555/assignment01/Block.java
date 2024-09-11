package com.s219029555.assignment01;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private String Letter;

    public String getLetter() {
        return Letter;
    }
    public void setLetter(String letter) {
        Letter = letter;
    }
    public Block() {
        Letter = "";
    }
    public static List<Block> getBlocks(int dimension){
        List<Block> blocks = new ArrayList<>();
        for (int i = 0; i < dimension*dimension; i++) {
            blocks.add(new Block());
        }
        return blocks;
    }

}
