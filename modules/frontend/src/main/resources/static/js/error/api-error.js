export class ApiError extends Error {

    constructor(message, statusCode, detail) {
        super(message);
        this.name = 'ApiError';
        this.statusCode = statusCode;
        this.detail = detail;
    }
}
