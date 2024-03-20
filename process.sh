#!/bin/bash
OUT=$1.tsv
echo "" > $OUT
for fi in $1*-NETWORK.tsv
do 
	echo $fi
	echo $fi"-START">> $OUT
	cat $fi >> $OUT
   echo $fi"-END" >> $OUT
done 
