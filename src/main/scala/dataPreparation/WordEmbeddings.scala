package dataPreparation

import java.io.{BufferedInputStream, DataInputStream, File, FileInputStream, FileNotFoundException}
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object WordEmbeddings {
  private val SPACE = 32
  private val LF = 10
  private val fis = new FileInputStream("vectors.bin")
  private val bis = new BufferedInputStream(fis)
  private val dis = new DataInputStream(bis)

  /**
   * A function to close the Input streams.
   */
  private def close() {
    dis.close()
    bis.close()
    fis.close()
  }

  /**
   * A function to read the tokens from the binary file.
   *
   * @param delimiters
   * @return String
   */
  private def readToken(delimiters: Set[Int] = Set(SPACE, LF)): String = {
    val bytes = new ArrayBuffer[Byte]()
    val sb = new StringBuilder()
    var byte = dis.readByte()
    while (!delimiters.contains(byte)) {
      bytes.append(byte)
      byte = dis.readByte()
    }
    sb.append(new String(bytes.toArray[Byte])).toString()
  }

  private val vocab = new mutable.HashMap[String, Array[Float]]()
  private var numWords = 0
  private var vecSize = 0

  /**
   * A function to load the binary file.
   *
   * @param filename
   * @param limit     Maximum number of words to load from file.
   * @param normalize To normalize the loaded vector.
   */
  def load(filename: String, limit: Integer = Int.MaxValue, normalize: Boolean = true): Unit = {
    val file = new File(filename)
    if (!file.exists()) {
      throw new FileNotFoundException("Binary vector file not found <" + file.toString + ">")
    }
    numWords = Integer.parseInt(readToken())
    vecSize = Integer.parseInt(readToken())
    var word = ""
    val vector = new Array[Float](vecSize)
    var normFactor = 1f
    for (_ <- 0 until math.min(numWords, limit)) {
      word = readToken()
      for (i <- vector.indices) vector(i) = java.lang.Float.intBitsToFloat(java.lang.Integer.reverseBytes(dis.readInt()))
      normFactor = if (normalize) math.sqrt(vector.foldLeft(0.0) { (sum, x) => sum + (x * x) }).toFloat else 1f
      vocab.put(word, vector.map(_ / normFactor))
      dis.readByte()
    }
    close()
  }

  /**
   * A function to fetch the vector representation of input word.
   *
   * @param word Input word
   * @return Embedded representation of the word in Array[Float]
   */
  def vector(word: String): Array[Float] = {
    val arr = vocab.getOrElse(word, Array[Float]())
    //val arr=vocab.getOrElse(word, Array[Float]()).take(10)
    arr
  }
}
