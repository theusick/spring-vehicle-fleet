import {showErrorToast} from "../../presentation/pages/toasts/show-error-toast";

$(document).ready(function () {
    $('#addButton').on('click', function (event) {
        event.preventDefault();

        openAndFillModal('#addVehicleBrandModal', '#addForm', {}, {});
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
            openAndFillModal('#editVehicleBrandModal', '#editForm', brand, fieldsMapping);
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
