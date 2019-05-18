package debug

class Result {
    var resultcode: String? = null
    var reason: String? = null
    var result: String? = null
    var error_code: String? = null

    override fun toString(): String {
        return "resultcode: $resultcode reason + $reason"
    }
}