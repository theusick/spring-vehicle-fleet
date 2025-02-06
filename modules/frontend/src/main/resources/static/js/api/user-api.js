import {ajaxRequest} from './ajax-client.js';
import {LoginResponse} from '../entities/login-response.js'

export const userApi = {
    login: async (user) => {
        const responseBody = await ajaxRequest({
            url: '/api/auth/login',
            method: 'POST',
            data: {
                username: user.username,
                password: user.password,
            },
            headers: {
                'Content-Type': 'application/json',
            },
        });
        return new LoginResponse(responseBody.token, responseBody.expiresIn);
    },
};
