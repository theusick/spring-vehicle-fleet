$(document).ready(function () {
    $('#addButton').on('click', function (event) {
        event.preventDefault();

        openModal('#addVehicleBrandModal', '#addForm', {}, {});
    });

    $('table #editButton').on('click', function (event) {
        event.preventDefault();

        const href = $(this).attr('href');

        const fieldsMapping = {
            'id': '#vehicleBrandId',
            'name': '#vehicleBrandName',
            'type': '#vehicleBrandType',
            'seats': '#vehicleBrandSeats',
            'fuelTank': '#vehicleBrandFuelTank',
            'payloadCapacity': '#vehicleBrandPayloadCapacity',
        };

        $.get(href, function (brand) {
            openModal('#editVehicleBrandModal', '#editForm', brand, fieldsMapping);
        }).fail(function (jqXHR) {
            showErrorToast(jqXHR.responseText || 'Failed to load brand data.');
        });
    });

    $('table #deleteButton').on('click', function (event) {
        event.preventDefault();

        const deleteUrl = this.getAttribute('data-delete-url');

        $('#deleteForm').attr('action', deleteUrl);
        $('#deleteModal').modal('show');
    });
});
