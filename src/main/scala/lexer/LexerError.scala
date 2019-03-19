package lexer

case class LexerError(location: Location, msg: String)

case class Location(line: Int, column: Int) {
  override def toString: String = s"$line:$column"
}
