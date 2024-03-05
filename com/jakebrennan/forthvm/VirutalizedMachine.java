package com.jakebrennan.forthvm;

import java.util.*;

public class VirutalizedMachine {
    private int[] memory = new int[0xFF];
    private Stack<Integer> dataStack = new Stack<>();
    private Stack<Integer> returnStack = new Stack<>();

    public VirutalizedMachine() {

    }

    public VirutalizedMachine(int memSize) {
        this.memory = new int[memSize];
    }

    public void writeToMemory(int value, int address) {
        this.memory[address] = value;
    }

    public int readFromMemory(int address) {
        return this.memory[address];
    }

    public void pushToDataStack(int value) {
        this.dataStack.push(value);
    }

    public int popFromDataStack() {
        return this.dataStack.pop();
    }

    public void pushToReturnStack(int value) {
        this.returnStack.push(value);
    }

    public int popFromReturnStack() {
        return this.returnStack.pop();
    }
}
