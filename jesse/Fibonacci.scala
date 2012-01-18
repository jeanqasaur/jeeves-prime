object Fibonacci 
{
	def main(args: Array[String])
	{
		for (i <- 1 to 10)
			println(fibonacci(i))	
	}

	def fibonacci (n: Int): Int = 
	{
	if (n < 3)
		1
	else
		fibonacci(n-1)+fibonacci(n-2)
	}
}