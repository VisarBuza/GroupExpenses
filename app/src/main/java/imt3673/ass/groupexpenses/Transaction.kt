package imt3673.ass.groupexpenses

/**
 * Represents a single settlement transaction.
 * Payment is from the payer to payee.
 * The source and destination here are names of respective people.
 */
class Transaction(val payer: String,
                  val payee: String,
                  val amount: Long)  {

    override fun toString(): String {
        return "Tx{$payer, $payee, $amount}"
    }
}

