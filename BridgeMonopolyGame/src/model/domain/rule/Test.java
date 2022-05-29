package model.domain.rule;

import model.domain.map.MapDecoder;

public class Test {
    public static void main(String args[]) {
        try {
            MapDecoder decoder = new MapDecoder();
            System.out.println(decoder.getBoard().getStartCell());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
