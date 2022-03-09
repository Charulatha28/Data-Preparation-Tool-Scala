import dataPreparation.OneHotEncoder.oneHotEncoded
import dataPreparation.TfIdf.folderProcess
import dataPreparation.WordEmbeddings.{load, vector}
import java.io.File
import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    load("vectors.bin")
    var iter = true
    while (iter) {
      println("\n Enter \n 1-One Hot Encoder \n 2-TF-IDF \n 3-Word embeddings \n 4-Exit")
      val option = scala.io.StdIn.readInt
      option match {
        case 1 =>
          println("Enter the file path")
          val input = scala.io.StdIn.readLine()
          val f = new File(input)
          val inp = Source.fromFile(f).mkString
          println(inp)
          println("Enter the word")
          val inpWord = scala.io.StdIn.readLine().toLowerCase
          val encodedSeq = oneHotEncoded(inpWord, inp)
          encodedSeq.foreach((item: Int) => print(item + " "))
        case 2 =>
          println("Enter the folder path")
          val input = scala.io.StdIn.readLine()
          folderProcess(input)
        case 3 =>
          println("Enter the input word")
          val input = scala.io.StdIn.readLine()
          val outputArray = vector(input)
          outputArray.foreach((item: Float) => print(item + " "))
        case 4 =>
          println("Exited")
          iter = false
        case _ => println("Enter correct option")
      }
    }
  }
}
