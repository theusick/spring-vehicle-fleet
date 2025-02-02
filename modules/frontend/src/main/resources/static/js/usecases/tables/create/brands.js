$(document).ready(function () {
    $('#vehicleBrandsTable').DataTable({
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
                    href: '/brands',
                    id: "addButton",
                    class: 'btn btn-primary'
                }
            }
        ],
        columnDefs: [{searchable: false, targets: -1}]
    });
});
