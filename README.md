# README
## FORTH Virtual Machine in Java
This does what it says on the tin, it virtualizes the FORTH programming language through Java.
It is by no means complete, but contains basic operations, as well as the following instructions:

- `DUP`
- `ABS`
- `NEGATE`
- `MIN`
- `MAX`
- `*/`
- `*/MOD`

It also contains a simulated memory array of `0x8000` `int`s, and a String 
Stack for both the stack and the result stack. It's not my cleanest work, but it functions as it should, I believe.
