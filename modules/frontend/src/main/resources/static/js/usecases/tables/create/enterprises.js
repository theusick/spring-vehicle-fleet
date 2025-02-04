import {showErrorToast, showSuccessToast} from '../../../presentation/toasts.js'

$(document).ready(function () {
    $('#enterprisesTable').DataTable({
        ajax: {
            url: '/api/v1/enterprises',
            type: 'GET',
            dataSrc: ''
        },
        columns: [
            {data: 'id'},
            {data: 'name'},
            {data: 'city'},
            {
                data: null,
                render: function (data, type, row) {
                    const editUrl = `/enterprises/${row.id}`;
                    return `
                        <a class="btn btn-primary btn-sm" href="${editUrl}">
                            <i class="bi bi-pencil-square"></i> Edit
                        </a>
                        <button class="btn btn-danger btn-sm" onclick="deleteEnterprise(${row.id})">
                            <i class="bi bi-trash"></i> Delete
                        </button>`;
                },
                orderable: false,
                searchable: false
            }
        ],
        rowId: function (data) {
            return data.id;
        },
        paging: true,
        ordering: true,
        scrollX: true,
        buttons: [
            {
                text: 'Add Enterprise',
                attr: {
                    href: '/enterprises/add',
                    id: "addButton",
                    class: 'btn btn-primary'
                }
            }
        ]
    });

    $('#enterprisesTable tbody').on('click', 'tr', function () {
        const enterpriseId = $(this).closest('tr').attr('id');
        window.location.href = window.location.origin + '/enterprises/' + enterpriseId + '/vehicles';
    })
});

function deleteEnterprise(id) {
    if (confirm('Are you sure you want to delete this enterprise?')) {
        $.ajax({
            url: `/api/v1/enterprises/${id}`,
            type: 'DELETE',
            success: function () {
                $('#enterprisesTable').DataTable().ajax.reload();
                showSuccessToast('Enterprise deleted successfully!');
            },
            error: function () {
                showErrorToast('Failed to delete enterprise');
            }
        });
    }
}
