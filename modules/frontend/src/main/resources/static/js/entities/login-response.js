export class LoginResponse {

    constructor(token, expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }
}
