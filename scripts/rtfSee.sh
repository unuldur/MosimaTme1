#!/bin/bash

cp ../ressources/wekaFile/headerSeeTouch ../ressources/wekaFile/duelSeeTouch.arff

for i in ../ressources/learningBase/see/*.csv
do
cat $i | tr \; , | tr '\n' , >> ../ressources/wekaFile/duelSeeTouch.arff
echo "NO" >> ../ressources/wekaFile/duelSeeTouch.arff
done 

for i in ../ressources/learningBase/touch/*.csv
do
cat $i | tr \; , | tr '\n' , >> ../ressources/wekaFile/duelSeeTouch.arff
echo "YES" >> ../ressources/wekaFile/duelSeeTouch.arff
done 
