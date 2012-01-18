import scala.math

object Fibbonacci {

  def fibbonacci(n: Int): Int = {
    val v = ((math.pow(1 + math.pow(5, .5), n) -
        math.pow(1 - math.pow(5, .5), n)) / 
        (math.pow(2, n) * math.pow(5, .5)));
    return v.intValue;
  }
  def main(args: Array[String]): Unit = {
    for(i <- 1 to 10) println(fibbonacci(i));
  }

}
