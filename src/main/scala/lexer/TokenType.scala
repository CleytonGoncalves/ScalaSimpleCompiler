package lexer

import scala.util.parsing.input.Positional

sealed trait TokenType extends Positional

case class IDENTIFIER(str: String) extends TokenType
case class KEYWORD(str: String) extends TokenType // if
case class COMMENT(str: String) extends TokenType // //, /* */

case class INTEGER(num: Int) extends TokenType // ex: 1, 2, 1000
case class FLOAT(num: Double) extends TokenType // ex: 1., 3.14, .5

/* Operators */

case class ARITHMETIC_OPERATOR(str: String) extends TokenType // +, -, *, /
case class RELATIONAL_OPERATOR(str: String) extends TokenType // >, >=, <, <=, ==, !=

case class ASSIGNMENT(str: String) extends TokenType // =
case class OPENING_PARENTHESES(str: String) extends TokenType // )
case class CLOSING_PARENTHESES(str: String) extends TokenType // (
case class OPENING_BRACES(str: String) extends TokenType // {
case class CLOSING_BRACES(str: String) extends TokenType // }
