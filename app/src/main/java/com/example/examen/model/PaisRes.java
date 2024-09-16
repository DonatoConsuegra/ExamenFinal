
package com.example.examen.model;

import java.util.List;

public class PaisRes {
    private String name;
    private Flags flags;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Flags getFlags() {
        return flags;
    }

    public void setFlags(Flags flags) {
        this.flags = flags;
    }

    public PaisRes get(int i) {
        return null;
    }

    public boolean isEmpty() {
        return false;
    }

    public static class Flags {
        private String png;

        public String getPng() {
            return png;
        }

        public void setPng(String png) {
            this.png = png;
        }
    }
}