package dataPreparation

import com.typesafe.scalalogging
import com.typesafe.scalalogging.Logger
import org.apache.commons.lang3.time.StopWatch

class Logger {
  val logger: scalalogging.Logger = Logger("log")
  val stopWatch = new StopWatch("")

  /**
   * starts the stopwatch
   *
   * @return
   */
  def startTime(): Unit = {
    stopWatch.start()
  }

  /**
   * stops the stopwatch
   *
   * @return
   */
  def stopTime(): Unit = {
    stopWatch.stop()
  }

}

