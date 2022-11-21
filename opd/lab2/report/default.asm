ORG 0x142
X: WORD 0x0100
A: WORD 0xA149
Y: WORD 0x0200
B: WORD 0x0100
C: WORD 0x414A
D: WORD 0x0200
E: WORD 0xA149
F: WORD 0xE14A
G: WORD 0xE14A
H: WORD 0x414A

LD $A   
OR $K   
ST $G   
CLA     
ADD $C  
ADD $G  
ST $G   
LD $D   
OR $G   
ST $G   
LD $Y   
SUB $G  
ST $G   
LD $F   
AND $G  
ST $G   
LD $B   
ADD $G  
ST $G   
CLA      
OR $H   
AND $G  
ST $G   
LD $E   
ADD $G  
ST $X   
HLT      

K: WORD 0xE14A