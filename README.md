# Data-Preparation-Tool-Scala

Data preparation tool is a command line interface project that aims to perform the 
following activities on a text document or a word.

* One hot encoding - Generates one hot encoded sequence of a word from the sentence in the input directory.
* TF-IDF - Generates TF-IDF matrix for folder input.
* Word Embedding - Vector representation of english words. 

All the above functionalities are implemented in scala.

## Pre-requisites 

* Intellij idea - IDE 

## Steps to run

* Fork and clone the project into your system.
* Run the main folder available inside src folder.
* Select 
  * 1 for One hot encoding.
  * 2 for TF-IDF.
  * 3 for Word Embedding.  
  * 4 for exiting out of console.
    
## Note :

* For one hot encoding input the directory of the text file which can be one or more sentences.
* For TF-IDF enter the folder directory where the sample text files 
with 1 sentence each must be available.
* Word embedding is a word2vec like model trained on vectors.bin file.
For not all words the vector representation would be displayed.