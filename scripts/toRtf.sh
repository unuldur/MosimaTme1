#!/bin/bash

cp ../ressources/wekaFile/header ../ressources/wekaFile/duel.arff

for i in ../ressources/learningBase/defeat/*.csv
do
cat $i | tr \; , >> ../ressources/wekaFile/duel.arff
done 

for i in ../ressources/learningBase/victory/*.csv
do
cat $i | tr \; , >> ../ressources/wekaFile/duel.arff
done 
