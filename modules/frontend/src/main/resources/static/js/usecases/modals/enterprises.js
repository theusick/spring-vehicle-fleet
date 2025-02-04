import {showErrorToast} from "../../presentation/toasts.js";

$(document).ready(function () {
    $('#addButton').on('click', function (event) {
        event.preventDefault();

        openAndFillModal('#addEnterpriseModal', '#addForm', {}, {});
    });

    $('table #editButton').on('click', function (event) {
        event.preventDefault();

        const href = $(this).attr('href');

        const fieldsMapping = {
            'id': '#enterpriseId',
            'name': '#enterpriseName',
            'city': '#enterpriseCity',
        };

        $.get(href, function (enterprise) {
            openAndFillModal('#editEnterpriseModal', '#editForm', enterprise, fieldsMapping);
        }).fail(function (jqXHR) {
            showErrorToast(jqXHR.responseText || 'Failed to load enterprise data.');
        });
    });

    $('table #deleteButton').on('click', function (event) {
        event.preventDefault();

        const deleteUrl = this.getAttribute('data-delete-url');

        $('#deleteForm').attr('action', deleteUrl);
        $('#deleteModal').modal('show');
    });
});
