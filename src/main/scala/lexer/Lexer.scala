package lexer

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

object Lexer extends RegexParsers {
  override def skipWhitespace = true
  override val whiteSpace: Regex = "[ \t\r\f\n]+".r
  
  def apply(code: String): Either[LexerError, List[TokenType]] = {
    parse(tokens, code) match {
      case NoSuccess(msg, next) => Left(LexerError(Location(next.pos.line, next.pos.column), msg))
      case Success(result, _) => Right(result)
    }
  }
  
  def tokens: Parser[List[TokenType]] = {
    phrase(rep1(
      // Order matters!
      comment | openBraces | closeBraces | openParentheses | closeParentheses | assignment | arithmeticOp |
      relationalOp | keyword | float | integer | identifier
    ))
  }
  
  def identifier: Parser[IDENTIFIER] = positioned {
    "[a-zA-Z][a-zA-Z0-9]*".r ^^ { str => IDENTIFIER(str) }
  }
  
  def comment: Parser[COMMENT] = positioned {
    "(/\\*(?:.|[\\n\\r])*?\\*/)|(\\/\\/.*)".r ^^ { str => COMMENT(str) }
  }
  
  def integer: Parser[INTEGER] = positioned {
    "[-+]?[0-9]+".r ^^ { str => INTEGER(str.toInt) }
  }
  
  def float: Parser[FLOAT] = positioned {
    "[-+]?[0-9]*\\.[0-9]+".r ^^ { str => FLOAT(str.toDouble) }
  }
  
  def arithmeticOp: Parser[ARITHMETIC_OPERATOR] = positioned {
    "[\\+\\-\\*\\/]".r ^^ { str => ARITHMETIC_OPERATOR(str) }
  }
  
  def relationalOp: Parser[RELATIONAL_OPERATOR] = positioned {
    "(>=)|(<=)|(==)|(!=)|[><]".r ^^ { str => RELATIONAL_OPERATOR(str) }
  }
  
  def keyword:          Parser[KEYWORD]             = positioned { "if" ^^ (_ => KEYWORD("if")) }
  def assignment:       Parser[ASSIGNMENT]          = positioned { "="  ^^ (_ => ASSIGNMENT("=")) }
  def openParentheses:  Parser[OPENING_PARENTHESES] = positioned { "("  ^^ (_ => OPENING_PARENTHESES("(")) }
  def closeParentheses: Parser[CLOSING_PARENTHESES] = positioned { ")"  ^^ (_ => CLOSING_PARENTHESES(")")) }
  def openBraces:       Parser[OPENING_BRACES]      = positioned { "{"  ^^ (_ => OPENING_BRACES("{")) }
  def closeBraces:      Parser[CLOSING_BRACES]      = positioned { "}"  ^^ (_ => CLOSING_BRACES("}")) }
  
}
