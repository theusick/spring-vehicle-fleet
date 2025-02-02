export function showErrorToast(message) {
    const toastContainer = document.getElementById('errorToastContainer');
    const toastMessage = $('#errorToastMessage');

    toastMessage.text(message);

    const toast = new bootstrap.Toast(toastContainer);
    toast.show();
}
