package com.github.vergenzt

import scala.collection.mutable
import scala.util.Try

import com.github.vergenzt.util._

package object puzzleretreat {

  type Puzzle = Map[Vec, Square]
  type Action = (Vec, Cardinal)

  /**
   * Returns true if the puzzle is solved.
   */
  def solved(puzzle: Puzzle): Boolean = {
    !puzzle.values.exists(_.isInstanceOf[DynamicSquare])
  }

  /**
   * Returns an iterator of valid actions and corresponding post-action puzzles.
   */
  def neighbors(puzzle: Puzzle): Iterator[(Action, Puzzle)] = {
    for {
      (pos, square: DynamicSquare) <- puzzle.iterator
      dir <- Cardinals
      action = (pos, dir)
      neighbor <- square.execute(puzzle, action)
    } yield {
      (action, neighbor)
    }
  }

  /**
   * Returns the result of taking the action on the given puzzle, if valid.
   */
  def neighbor(puzzle: Puzzle, action: Action): Option[Puzzle] = {
    val (pos, dir) = action
    puzzle.get(pos) match {
      case Some(square: DynamicSquare) => square.execute(puzzle, action)
      case _ => None
    }
  }
}
