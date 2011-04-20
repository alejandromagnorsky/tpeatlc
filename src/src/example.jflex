package src;

import java.io.*;
import java.util.*;

enum Token{A,B,C}

%%

%class Lexer
%type Token

L = [a-z]

%%

{L}+ {System.out.println("hola");}
.                   {}