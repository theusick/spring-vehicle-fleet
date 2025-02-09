import {showErrorToast, showSuccessToast} from '../../../presentation/toasts.js';
import {getUTCOffset} from '../../../utils/time-utils.js'

$(document).ready(function () {
    const table = initEnterprisesTable();
    setupRowClickHandler(table);
});

function initEnterprisesTable() {
    return $('#enterprisesTable').DataTable({
        ajax: {
            url: '/api/v1/enterprises',
            type: 'GET',
            dataSrc: ''
        },
        columns: getTableColumns(),
        rowId: 'id',
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
}

function getTableColumns() {
    return [
        {data: 'id'},
        {data: 'name'},
        {data: 'city'},
        {
            data: 'timezone',
            render: data => {
                if (!data) return '';
                const utcOffset = getUTCOffset(data);
                return data + ` (${utcOffset})`;
            }
        },
        {
            data: null,
            render: renderActionsColumn,
            orderable: false,
            searchable: false
        }
    ];
}

function renderActionsColumn(data, type, row) {
    const editUrl = `/enterprises/${row.id}`;
    return `
        <a class="btn btn-primary btn-sm" href="${editUrl}">
            <i class="bi bi-pencil-square"></i> Edit
        </a>
        <button class="btn btn-danger btn-sm delete-btn" data-id="${row.id}">
            <i class="bi bi-trash"></i> Delete
        </button>`;
}

function setupRowClickHandler(table) {
    $('#enterprisesTable tbody').on('click', 'tr', function (e) {
        if ($(e.target).closest('.btn').length) return;

        const enterpriseId = table.row(this).id();
        window.location.href = `${window.location.origin}/enterprises/${enterpriseId}/vehicles`;
    }).on('click', '.delete-btn', function (e) {
        e.stopPropagation();
        const enterpriseId = $(this).data('id');
        handleDeleteEnterprise(enterpriseId);
    });
}

function handleDeleteEnterprise(enterpriseId) {
    if (!confirm('Are you sure you want to delete this enterprise?')) return;

    $.ajax({
        url: `/api/v1/enterprises/${enterpriseId}`,
        type: 'DELETE',
        success: () => {
            $('#enterprisesTable').DataTable().ajax.reload();
            showSuccessToast('Enterprise deleted successfully!');
        },
        error: () => {
            showErrorToast('Failed to delete enterprise');
        }
    });
}
