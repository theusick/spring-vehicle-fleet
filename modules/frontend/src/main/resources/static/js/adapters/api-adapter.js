import {ApiError} from '../error/api-error.js';

async function request(endpoint, method = 'GET', body = null) {
    const headers = {'Content-Type': 'application/json'};
    const token = sessionStorage.getItem('jwtToken');

    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const options = {method, headers};

    if (body) {
        options.body = JSON.stringify(body);
    }

    try {
        const response = await fetch(`${endpoint}`, options);

        if (!response.ok) {
            const error = await response.json();
            throw new ApiError(error.title || 'Request failed',
                response.status,
                error.detail);
        }

        return await response.json();
    } catch (error) {
        console.error(error.message);
        throw error;
    }
}

export const apiAdapter = {
    get: (endpoint) => request(endpoint),
    post: (endpoint, data) => request(endpoint, 'POST', data),
    put: (endpoint, data) => request(endpoint, 'PUT', data),
    delete: (endpoint) => request(endpoint, 'DELETE'),
};
