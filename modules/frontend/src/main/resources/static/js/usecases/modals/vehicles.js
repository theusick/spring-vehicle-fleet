import {showErrorToast} from "../../presentation/pages/toasts/show-error-toast";

$(document).ready(function () {
    $('#addButton').on('click', function (event) {
        event.preventDefault();

        openAndFillModal('#addVehicleModal', '#addForm', {}, {});
    });

    $('table #editButton').on('click', function (event) {
        event.preventDefault();

        const href = $(this).attr('href');

        const fieldsMapping = {
            'id': '#vehicleId',
            'year': '#vehicleYear',
            'mileage': '#vehicleMileage',
            'color': '#vehicleColor',
            'price': '#vehiclePrice',
            'licensePlate': '#vehicleLicensePlate',
            'enterpriseId': '#vehicleEnterpriseId',
            'brandId': '#vehicleBrandId'
        };

        $.get(href, function (vehicle) {
            openAndFillModal('#editVehicleModal', '#editForm', vehicle, fieldsMapping);
        }).fail(function (jqXHR) {
            showErrorToast(jqXHR.responseText || 'Failed to load vehicle data.');
        });
    });

    $('table #deleteButton').on('click', function (event) {
        event.preventDefault();

        const deleteUrl = this.getAttribute('data-delete-url');

        $('#deleteForm').attr('action', deleteUrl);
        $('#deleteModal').modal('show');
    });
});
