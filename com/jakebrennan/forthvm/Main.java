package com.jakebrennan.forthvm;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a FORTH program (no branching, yet), with identifiers separated by spaces: ");
        String progStr = scan.nextLine();
        String[] prog = progStr.split(" ");
        HashMap<String, String[]> words = new HashMap<>();
        Stack<String> stack = new Stack<>();
        Stack<String> retStack = new Stack<>();
        int[] memory = new int[32768];
        String output = "";
        Stack<String> wordStack = new Stack<>();
        for (int i = 0; i < prog.length; i++) {
            if (i == 0 && prog[i].charAt(0) == ':') {
                i++;
                String[] def = new String[prog.length - 2];
                for (int j = i + 1; j < prog.length; j++) {
                    if (prog[j].charAt(0) == ';') {
                        break;
                    }
                    def[j -2] = prog[j];
                }
                words.put(prog[i], def);
            } else {
                int num1, num2, num3, imd, addr;
                String topOfStack, addrString;
                switch (prog[i].charAt(0)) {
                    case '+':
                        num2 = Integer.parseInt(stack.pop());
                        num1 = Integer.parseInt(stack.pop());
                        int sum = num1 + num2;
                        stack.push("" + sum);
                        break;
                    case '-':
                        num2 = Integer.parseInt(stack.pop());
                        num1 = Integer.parseInt(stack.pop());
                        int diff = num1 - num2;
                        stack.push("" + diff);
                        break;
                    case '*':
                        if (prog[i].charAt(1) == '/') {
                            break;
                        }
                        num2 = Integer.parseInt(stack.pop());
                        num1 = Integer.parseInt(stack.pop());
                        int product = num1 * num2;
                        stack.push("" + product);
                        break;
                    case '/':
                        num2 = Integer.parseInt(stack.pop());
                        num1 = Integer.parseInt(stack.pop());
                        int quotient = num1 / num2;
                        stack.push("" + quotient);
                        break;
                    default:
                        switch(prog[i]) {
                            case "DUP":
                                num1 = Integer.parseInt(stack.peek());
                                stack.push("" + num1);
                                break;
                            case "ABS":
                                num1 = Integer.parseInt(stack.pop());
                                num2 = Math.abs(num1);
                                stack.push("" + num2);
                                break;
                            case "NEGATE":
                                num1 = Integer.parseInt(stack.pop());
                                num2 = -num1;
                                stack.push("" + num2);
                                break;
                            case "MIN":
                                num2 = Integer.parseInt(stack.pop());
                                num1 = Integer.parseInt(stack.pop());
                                int min = Math.min(num1, num2);
                                stack.push("" + min);
                                break;
                            case "MAX":
                                num2 = Integer.parseInt(stack.pop());
                                num1 = Integer.parseInt(stack.pop());
                                int max = Math.max(num1, num2);
                                stack.push("" + max);
                                break;
                            case ">R":
                                topOfStack = stack.pop();
                                retStack.push(topOfStack);
                                break;
                            case "R>":
                                topOfStack = retStack.pop();
                                stack.push(topOfStack);
                                break;
                            case "R@":
                                topOfStack = retStack.peek();
                                stack.push(topOfStack);
                                break;
                            case "*/":
                                num3 = Integer.parseInt(stack.pop());
                                num2 = Integer.parseInt(stack.pop());
                                num1 = Integer.parseInt(stack.pop());
                                imd = num1 * num2;
                                imd /= num3;
                                stack.push("" + imd);
                                break;
                            case "*/MOD":
                                num3 = Integer.parseInt(stack.pop());
                                num2 = Integer.parseInt(stack.pop());
                                num1 = Integer.parseInt(stack.pop());
                                imd = num1 * num2;
                                imd /= num3;
                                int rem = imd % num3;
                                stack.push("" + rem);
                                stack.push("" + imd);
                                break;
                            case "0<":
                                num1 = Integer.parseInt(stack.pop());
                                if (num1 < 0) {
                                    stack.push("1");
                                } else {
                                    stack.push("0");
                                }
                                break;
                            case "0=":
                                num1 = Integer.parseInt(stack.pop());
                                if (num1 == 0) {
                                    stack.push("1");
                                } else {
                                    stack.push("0");
                                }
                                break;
                            case "0":
                                num1 = Integer.parseInt(stack.pop());
                                if (num1 > 0) {
                                    stack.push("1");
                                } else {
                                    stack.push("0");
                                }
                                break;
                            case "<":
                                num2 = Integer.parseInt(stack.pop());
                                num1 = Integer.parseInt(stack.pop());
                                if (num1 < num2) {
                                    stack.push("1");
                                } else {
                                    stack.push("0");
                                }
                                break;
                            case "<>":
                                num2 = Integer.parseInt(stack.pop());
                                num1 = Integer.parseInt(stack.pop());
                                if (num1 != num2) {
                                    stack.push("1");
                                } else {
                                    stack.push("0");
                                }
                                break;
                            case "=":
                                num2 = Integer.parseInt(stack.pop());
                                num1 = Integer.parseInt(stack.pop());
                                if (num1 == num2) {
                                    stack.push("1");
                                } else {
                                    stack.push("0");
                                }
                                break;
                            case ">":
                                num2 = Integer.parseInt(stack.pop());
                                num1 = Integer.parseInt(stack.pop());
                                if (num1 > num2) {
                                    stack.push("1");
                                } else {
                                    stack.push("0");
                                }
                                break;
                            case "!":
                                addrString = stack.pop();
                                num1 = Integer.parseInt(stack.pop());
                                if (addrString.charAt(0) == '$' || addrString.startsWith("0x")) {
                                    addr = Integer.parseInt(addrString.substring(1), 16);
                                } else if (addrString.startsWith("0b")) {
                                    addr = Integer.parseInt(addrString.substring(2), 2);
                                } else if (addrString.startsWith("0q")) {
                                    addr = Integer.parseInt(addrString.substring(2), 8);
                                } else if (addrString.startsWith("0d")) {
                                    addr = Integer.parseInt(addrString.substring(2));
                                } else {
                                    addr = Integer.parseInt(addrString);
                                }
                                memory[addr] = num1;
                                break;
                            case "+!":
                                addrString = stack.pop();
                                num1 = Integer.parseInt(stack.pop());
                                if (addrString.charAt(0) == '$' || addrString.startsWith("0x")) {
                                    addr = Integer.parseInt(addrString.substring(1), 16);
                                } else if (addrString.startsWith("0b")) {
                                    addr = Integer.parseInt(addrString.substring(2), 2);
                                } else if (addrString.startsWith("0q")) {
                                    addr = Integer.parseInt(addrString.substring(2), 8);
                                } else if (addrString.startsWith("0d")) {
                                    addr = Integer.parseInt(addrString.substring(2));
                                } else {
                                    addr = Integer.parseInt(addrString);
                                }
                                memory[addr] += num1;
                                break;
                            case "?DUP":
                                num1 = Integer.parseInt(stack.peek());
                                if (num1 != 0) {
                                    stack.push("" + num1);
                                }
                                break;
                            case "@":
                                addrString = stack.pop();
                                if (addrString.charAt(0) == '$' || addrString.startsWith("0x")) {
                                    addr = Integer.parseInt(addrString.substring(1), 16);
                                } else if (addrString.startsWith("0b")) {
                                    addr = Integer.parseInt(addrString.substring(2), 2);
                                } else if (addrString.startsWith("0q")) {
                                    addr = Integer.parseInt(addrString.substring(2), 8);
                                } else if (addrString.startsWith("0d")) {
                                    addr = Integer.parseInt(addrString.substring(2));
                                } else {
                                    addr = Integer.parseInt(addrString);
                                }
                                stack.push("" + memory[addr]);
                                break;
                            case "AND":
                                num2 = Integer.parseInt(stack.pop());
                                num1 = Integer.parseInt(stack.pop());
                                num3 = num1 & num2;
                                stack.push("" + num3);
                                break;
                            case "DROP":
                                stack.pop();
                                break;
                            case "NOT":
                                num1 = Integer.parseInt(stack.pop());
                                if (num1 != 0) {
                                    stack.push("" + 0);
                                } else {
                                    stack.push("" + 1);
                                }
                                break;
                            case "OR":
                                num2 = Integer.parseInt(stack.pop());
                                num1 = Integer.parseInt(stack.pop());
                                num3 = num1 | num2;
                                stack.push("" + num3);
                                break;
                            case "OVER":
                                num2 = Integer.parseInt(stack.pop());
                                num1 = Integer.parseInt(stack.pop());
                                stack.push("" + num2);
                                stack.push("" + num1);
                                break;
                            case "XOR":
                                num2 = Integer.parseInt(stack.pop());
                                num1 = Integer.parseInt(stack.pop());
                                num3 = num1 ^ num2;
                                stack.push("" + num3);
                                break;
                            case "CR":
                                output += "\n";
                                break;
                            case ".":
                                output += stack.pop();
                                break;
                            default:
                                if (words.containsKey(prog[i])) {
                                    for (String str : words.get(prog[i])) {
                                        wordStack.push(str);
                                    }
                                    for (int k = 0; k < wordStack.size(); k++) {
                                        stack.push(wordStack.pop());
                                    }
                                } else {
                                    stack.push(prog[i]);
                                }
                                break;

                        }
                        break;

                }

            }
        }
        System.out.println(output + " ok");
        String finalStack = stack.toString();
        System.out.println("Final stack: " + finalStack);
    }
}
