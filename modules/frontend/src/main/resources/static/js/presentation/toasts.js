export function showSuccessToast(message) {
    const toastContainer = document.getElementById('successToastContainer');
    const toastMessage = $('#successToastMessage');

    toastMessage.text(message);

    const toast = new bootstrap.Toast(toastContainer);
    toast.show();
}

export function showErrorToast(message, description = '') {
    const toastContainer = document.getElementById('errorToastContainer');
    const toastMessage = $('#errorToastMessage');
    const toastDescription = $('#errorToastDescription');

    toastMessage.text(message);

    if (description && description.trim() !== '') {
        toastDescription.text(description).show();
    } else {
        toastDescription.text('').hide();
    }

    const toast = new bootstrap.Toast(toastContainer);
    toast.show();
}

