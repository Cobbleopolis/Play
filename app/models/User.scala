package models

class User(val username: String, val email: String, val password: String, val salt: String, val accountType: Int, val submissionsOpen: Boolean) {

}
