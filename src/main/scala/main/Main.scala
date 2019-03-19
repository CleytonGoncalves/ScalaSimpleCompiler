package main

import lexer.Lexer

object Main extends App {
  def code =
    """
  if (a9 < b)              /* operacao relacional */
  {
    soma = 4.9 + 0.5     // atribuicao
    i = i * 1
  }
  """
  
  Lexer.apply(code) match {
    case Left(error) => println(error)
    case Right(tokenList) => println(tokenList.mkString("\n"))
  }
}
