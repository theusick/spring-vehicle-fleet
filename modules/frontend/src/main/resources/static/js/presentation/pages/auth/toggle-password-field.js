const passwordField = document.getElementById('password');
const toggleButton = document.getElementById('togglePassword');
const passwordIcon = document.getElementById('passwordIcon');

export function togglePasswordVisibility() {
    if (!passwordField || !toggleButton) return;

    toggleButton.addEventListener('click', () => {
        const isPasswordVisible = passwordField.type === 'password';
        passwordField.type = isPasswordVisible ? 'text' : 'password';
        passwordIcon.classList.toggle('bi-eye');
        passwordIcon.classList.toggle('bi-eye-slash');
    });
}

document.addEventListener('DOMContentLoaded', () => {
    togglePasswordVisibility();
});
