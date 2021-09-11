package com.mobiquity;

import com.mobiquity.packer.Packer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {

        Logger LOGGER = Logger.getLogger(Main.class.getName());
        String FILE_PATH = "src/test/resources/example_input.txt";

        try {
            Packer.pack(FILE_PATH);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

}
