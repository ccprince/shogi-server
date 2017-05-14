package models
import models.Player._

import scala.collection.mutable

final case class Location(rank: Int, file: Int) {
  require(rank >= 1 && rank <= 9 && file >= 1 && file <= 9)

  def +(d: (Int, Int)): Option[Location] = {
    val (dr, df) = d
    if (rank + dr > 9 || rank + dr < 1 || file + df > 9 || file + df < 1)
      None
    else
      Some(Location(rank + dr, file + df))
  }
}

object Location {
  implicit def intsToLocation(x: (Int, Int)): Location = Location(x._1, x._2)
}

object ShogiBoard {
  def initialSetup(): ShogiBoard = {
    val pieces = mutable.Map[Location,Piece]()
    for (player <- Player.values) {
      val backRank = if (player == Black) 9 else 1
      pieces += Location(backRank, 9) -> Lance(player)
      pieces += Location(backRank, 1) -> Lance(player)
      pieces += Location(backRank, 8) -> Knight(player)
      pieces += Location(backRank, 2) -> Knight(player)
      pieces += Location(backRank, 7) -> Silver(player)
      pieces += Location(backRank, 3) -> Silver(player)
      pieces += Location(backRank, 6) -> Gold(player)
      pieces += Location(backRank, 4) -> Gold(player)
      pieces += Location(backRank, 5) -> King(player)

      val frontRank = if (player == Black) 7 else 3
      for (file <- 1 to 9) {
        pieces += Location(frontRank, file) -> Pawn(player)
      }
    }

    pieces += Location(2,2) -> Bishop(White)
    pieces += Location(2,8) -> Rook(White)
    pieces += Location(8,2) -> Rook(Black)
    pieces += Location(8,8) -> Bishop(Black)

    apply(Map.empty ++ pieces) // Make the map immutable
  }
}

case class ShogiBoard(pieces: Map[Location,Piece]) {

  def pieceAt(l: Location): Option[Piece] = pieces.get(l)
}
