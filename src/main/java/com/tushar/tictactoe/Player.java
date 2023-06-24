package com.tushar.tictactoe;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Player {
    private String symbol;
    private String name;


    private Player(Builder b){
        this.symbol = b.symbol;
        this.name = b.name;
    }

    @Getter
    public static class Builder{
        private String symbol;
        private String name;

        public Builder setSymbol(String symbol){
            this.symbol = symbol;
            return this;
        }
        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Player build(){
            if(this.name == null) {
                this.setName(symbol);
            }
            return new Player(this);
        }
    }

}
