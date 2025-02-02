import {showErrorToast} from '../../presentation/pages/toasts/show-error-toast.js';
import {userApiAdapter} from '../../adapters/user-api-adapter.js';
import {ApiError} from '../../error/api-error.js';

document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.querySelector('form');
    if (loginForm) {
        loginForm.addEventListener('submit', async (event) => {
            event.preventDefault();
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;

            try {
                await userApiAdapter.login({username, password});
                window.location.href = "/overview";
            } catch (error) {
                if (error instanceof ApiError) {
                    showErrorToast(error.detail || 'Unknown error occurred');
                } else {
                    console.error(error.message)
                    showErrorToast('Something went wrong, please try again later.');
                }
            }
        });
    }
});
