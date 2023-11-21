grammar Q;

qlist : qexpr+ ;
qexpr : VAR '=' '(' num ',' num ',' num ',' num ',' num ',' num ',' num ',' num ')' EOL ;

VAR : [A-Z];
EOL : ';';
WS : [ \t\r\n]+ -> skip ;
num : (INT);
INT : [0-9]+ ;
