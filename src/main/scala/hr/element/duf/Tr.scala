package hr.element.duf

import scala.collection.immutable.VectorBuilder

case class Transaction(
    order: Int
  , inflow: Long
  , outflow: Long
  , balance: Long)

object Tr extends App {
  val trans = IndexedSeq(
    Transaction( 0, 100,   0, 0)
  , Transaction( 1,   0, 100, 0)
  , Transaction( 2,   0, 100, 0)
  , Transaction( 3,  50,   0, 0)
  )

  def fixBalances(transactions: IndexedSeq[Transaction], startBalance: Long) = {
    val fixedTransactions = new VectorBuilder[Transaction]

    var curBalance = startBalance

    for (t <- transactions) {
      fixedTransactions += t.copy(balance = curBalance)

      curBalance += t.outflow - t.inflow
    }

    fixedTransactions.result
  }

  fixBalances(trans, 5).foreach(println)
}

