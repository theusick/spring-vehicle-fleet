import {loginUser} from '../../../usecases/auth/user-login.js';

document.getElementById('loginForm').addEventListener('submit', async (event) => {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        await loginUser(username, password);
    } catch (error) {
        console.error('Login failed:', error);
    }
});
