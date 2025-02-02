$(document).ready(function () {
    $('#enterprisesTable').DataTable({
        ajax: {
            url: '/api/v1/enterprises',
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
});

function deleteEnterprise(id) {
    if (confirm('Are you sure you want to delete this enterprise?')) {
        $.ajax({
            url: `/api/v1/enterprises/${id}`,
            type: 'DELETE',
            success: function () {
                $('#enterprisesTable').DataTable().ajax.reload();
                alert('Enterprise deleted successfully!');
            },
            error: function () {
                alert('Failed to delete enterprise.');
            }
        });
    }
}
