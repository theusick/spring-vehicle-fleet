import {apiAdapter} from './api-adapter.js';
import {LoginResponse} from '../entities/responses/login-response.js'

export const userApiAdapter = {
    login: async (user) => {
        const responseBody = await apiAdapter.post('/api/auth/login', {
            username: user.username,
            password: user.password,
        });

        return new LoginResponse(responseBody.token, responseBody.expiresIn);
    },
};
