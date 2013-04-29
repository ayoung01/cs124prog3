#!/bin/bash
i=1
while [ $i -le $1 ]
do
	mat=$(java Strassen 0 $1 $1.txt $i 0 $2) 
	stras=$(java Strassen 0 $1 $1.txt $i 1 $2) 	
	echo -e "$i \t $mat \t $stras" >> $2_$1.txt

	i=$(( $i + 1 ))
done