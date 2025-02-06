export class UserCredentials {

    constructor(username, password) {
        this.username = username;
        this.password = password;
    }

    isValid() {
        return this.username !== '' && this.password.length >= 6;
    }
}
