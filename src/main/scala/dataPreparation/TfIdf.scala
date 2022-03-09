package dataPreparation

import java.io.File
import scala.io.Source

object TfIdf {
  /**
   *
   * @param wordDict   Dictionary of words along with the frequency
   * @param bagOfWords Array of all the words in the sentences
   * @return Map of word along with the Term Frequency(TF)
   */
  private def computeTF(wordDict: Map[String, Int], bagOfWords: Array[String]): Map[String, Float] = {
    val loggerObj = new Logger
    loggerObj.logger.info("TF Computation started")
    loggerObj.startTime()
    var tfDict = Map[String, Float]()
    val bagOfWordsCount = bagOfWords.length
    for ((word, count) <- wordDict)
      tfDict = tfDict + (word -> count / bagOfWordsCount.toFloat)
    loggerObj.stopTime()
    loggerObj.logger.info("TF Computation ended")
    tfDict
  }

  /**
   *
   * @param docList    Array containing all the sentences of documents
   * @param bagOfWords Array of all the words in the sentences
   * @return Map of words along with Inverse Document Frequency(IDF)
   */
  private def computeIDF(docList: Array[String], bagOfWords: Array[String]): Map[String, Float] = {
    val loggerObj = new Logger
    loggerObj.logger.info("IDF Computation started")
    loggerObj.startTime()
    var idfMap = Map[String, Float]()
    val numOfDocs = docList.length
    for (word <- bagOfWords) {
      var sentCount = 0
      for (doc <- docList)
        if (doc.contains(word)) sentCount += doc.split(" ").count(_ == word)
      idfMap = idfMap + (word -> math.log(numOfDocs / sentCount.toFloat).toFloat)
    }
    loggerObj.stopTime()
    loggerObj.logger.info("IDF Computation ended")
    idfMap
  }

  /**
   *
   * @param tfBow Term Frequency
   * @param idfs  Inverse Document Frequency
   * @return Map of TF-IDF of all words in the document
   */
  private def computeTFIDF(tfBow: Map[String, Float], idfs: Map[String, Float]): Map[String, Float] = {
    val loggerObject = new Logger()
    loggerObject.logger.info("TF-IDF started")
    loggerObject.startTime()
    var TfIDF = Map[String, Float]()
    for ((word, value) <- tfBow) TfIDF = TfIDF + (word -> (value * idfs(word)))
    loggerObject.stopTime()
    loggerObject.logger.info("TF-IDF ended")
    TfIDF
  }

  /**
   * A function which iterates the files in the directory.
   *
   * @param inp The path of the directory
   */
  def folderProcess(inp: String): Array[Array[Float]] = {
    val loggerObj = new Logger()
    loggerObj.logger.info("Function read folder started")
    loggerObj.startTime()
    var Name = Array[Array[Float]]()
    var bagOfWords = Array[String]()
    var sentenceTmpArr = Array[String]()
    val inputFiles = new File(inp).listFiles()
    var listOf = Array[String]()
    for (i <- 0 until inputFiles.length) {
      val source = Source.fromFile(inputFiles(i).getAbsolutePath, "utf-8")
      sentenceTmpArr = sentenceTmpArr ++ source.getLines()
      source.close
    }
    for (i <- sentenceTmpArr) bagOfWords ++= i.split(" ")
    for (i <- sentenceTmpArr) {
      val tokens = i.split(" ")
      val map1 = bagOfWords.toSet.map((word: String) => (word, tokens.count(_ == word))).toMap
      val tfFirst = computeTF(map1, tokens)
      val getIDF = computeIDF(sentenceTmpArr, bagOfWords)
      val TFIDF_doc1 = computeTFIDF(tfFirst, getIDF)
      var arr = Array[Float]()
      arr ++= TFIDF_doc1.values.toArray
      listOf = TFIDF_doc1.keys.toArray
      Name ++= Array(arr)
    }
    for (item <- listOf)
      print("      " + item.replaceAll("[^a-zA-Z0-9]", " "))
    println()
    for (i <- Name) {
      for (j <- i) {
        print(f"  $j%1.6f")
      }
      println()
    }
    println()
    loggerObj.stopTime()
    loggerObj.logger.info("Function read folder ended.")
    Name
  }
}
