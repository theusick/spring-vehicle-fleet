$(document).ready(function () {
    $('#vehiclesTable').DataTable({
        layout: {
            topStart: [
                'buttons',
                'pageLength'
            ]
        },
        info: true,
        ordering: true,
        paging: true,
        scrollX: true,
        buttons: [
            {
                text: 'Add',
                attr: {
                    href: '/vehicles',
                    id: "addButton",
                    class: 'btn btn-primary'
                }
            }
        ],
        columnDefs: [{searchable: false, targets: -1}]
    });
});
