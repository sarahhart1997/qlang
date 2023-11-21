all: antlr4
	javac -cp ./lib/antlr-4.13.1-complete.jar *.java

antlr4: Q.g4
	java -jar ./lib/antlr-4.13.1-complete.jar Q.g4

run: all
	java -cp "lib/antlr-4.13.1-complete.jar":. QL

generate_r_code: all
	java -cp "lib/antlr-4.13.1-complete.jar":. QL > R.code 

draw_quad: generate_r_code
	R --vanilla -q < R.code
	
clean:
	mv QL.java _QL.java
	rm Q*.java
	mv _QL.java QL.java
	rm Q*.class Q*.tokens *.interp 
	rm R.code
	

.PHONY: all antlr4 run clean