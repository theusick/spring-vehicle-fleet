import {showErrorToast} from '../../presentation/toasts.js';
import {userApi} from '../../api/user-api.js';
import {ApiError} from '../../error/api-error.js';

document
    .addEventListener('DOMContentLoaded', () => {
        const loginForm = document.querySelector('form');
        if (loginForm) {
            loginForm.addEventListener('submit', async (event) => {
                event.preventDefault();
                const username = document.getElementById('username').value;
                const password = document.getElementById('password').value;

                try {
                    await userApi.login({username, password});
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
