package dataPreparation

import opennlp.tools.tokenize.{TokenizerME, TokenizerModel}
import java.io.InputStream

object OneHotEncoder {
  val modelIn1: InputStream = getClass.getResourceAsStream("/da-token.bin")
  val tokenModel = new TokenizerModel(modelIn1)
  modelIn1.close()
  val _tokenizer = new TokenizerME(tokenModel)

  /**
   * A function to pre process the sentence such as converting to lowercase,removes [,.!],tokenizes and sorts the words.
   *
   * @param input :input sentence
   * @return tokens in Array[String[]
   */
  private def wordPreprocess(input: String): Array[String] = {
    val loggerObj = new Logger
    loggerObj.logger.info("Word preprocess started")
    loggerObj.startTime()
    val words = input.toLowerCase
    val finalWord = words.replaceAll("[^a-zA-Z0-9]"," ")
    val tokens = _tokenizer.tokenize(finalWord).distinct
    scala.util.Sorting.quickSort(tokens)
    loggerObj.stopTime()
    loggerObj.logger.info("Word preprocess ended")
    tokens
  }

  /**
   * A function which calculates the one hot encoded sequence of a word in the sentence.
   *
   * @param word  : Word in the sentence for which one hot encoding is to be done.
   * @param input : Input Sentence
   * @return one hot encoded array of integer
   */
  def oneHotEncoded(word: String, input: String): Array[Int] = {
    val loggerObj = new Logger
    loggerObj.logger.info("One hot encoding started")
    loggerObj.startTime()
    val wordList = wordPreprocess(input: String)
    println("Length of the vector : " + wordList.length)
    val arr = new Array[Int](wordList.length)
    for (i <- arr.indices)
      for (j <- wordList.indices)
        if (word == wordList(j)) {
          if (j == i)
            arr(i) = 1
        }
    loggerObj.stopTime()
    loggerObj.logger.info("One hot encoding ended.")
    arr
  }
}
