Optiver  
A Symbolic Expression is a notation for tree structed data.    
Given a set of parent-child pairs find the lexographically smallest s-expression.  
Ex. Given (A,B) (A,C) (B,G) (C,H) (E,F) (B,D) (C,E)  
Return (A(B(D)(G))(C(E(F))(H))  
  
Check for the following errors:  
E1 = bad input  
E2 = duplicate edges  
E3 = node has >2 children   
E4 = tree has 0 or >2 roots    
E5 = cycle  
