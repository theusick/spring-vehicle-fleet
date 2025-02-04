import {showErrorToast, showSuccessToast} from '../../../presentation/toasts.js';

$(document).ready(function () {
    const enterpriseId = window.location.pathname.split('/')[2];

    let brandList = [];

    $.ajax({
        url: '/api/v1/vehicles/brands',
        type: 'GET',
        success: function (brands) {
            brandList = brands;
        },
        error: function () {
            showErrorToast('Failed to load brands list');
        }
    });

    const table = $('#enterpriseVehiclesTable').DataTable({
        ajax: {
            url: '/api/v1/enterprises/' + enterpriseId + '/vehicles',
            type: 'GET',
            /* Request parameters */
            data: function (data) {
                const pageNumber = data.start / data.length;
                return {
                    page: pageNumber >= 0 ? pageNumber : 0,
                    size: data.length
                };
            },
            dataSrc: function (response) {
                if (response.page) {
                    response.recordsTotal = response.page.totalElements;
                    response.recordsFiltered = response.page.totalElements;
                } else {
                    response.recordsTotal = 0;
                    response.recordsFiltered = 0;
                }
                return response.content;
            },
            error: function () {
                showErrorToast('Failed to load vehicles data.');
            }
        },
        columns: [
            {data: 'id'},
            {data: 'year'},
            {data: 'mileage'},
            {data: 'color'},
            {data: 'price'},
            {data: 'licensePlate'},
            {
                data: 'brand',
                render: function (brandId) {
                    const brand = brandList.find(b => b.id === brandId);
                    return brand ? brand.name : 'Unknown';
                }
            },
            {
                data: null,
                render: function (data, type, row) {
                    return `
                        <button class="btn btn-primary btn-sm edit-btn" data-id="${row.id}">
                            <i class="bi bi-pencil-square"></i> Edit
                        </button>
                        <button class="btn btn-danger btn-sm delete-btn" data-id="${row.id}">
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
        serverSide: true,
        processing: true,
        lengthMenu: [5, 10, 25, 50],
        pageLength: 20,
        layout: {
            topStart: [
                'buttons',
                'pageLength'
            ]
        },
        buttons: [
            {
                text: 'Add',
                attr: {
                    id: "addButton",
                    class: 'btn btn-primary'
                },
                action: function () {
                    openAddVehicleModal(enterpriseId);
                }
            }
        ]
    });

    function openAddVehicleModal() {
        const addForm = $('#addForm');
        addForm.trigger('reset');

        fillBrandModalField(null, $('#vehicleBrand'));

        addForm.attr('action', `/api/v1/enterprises/${enterpriseId}/vehicles`);
        $('#addVehicleModal').modal('show');
    }

    function openEditVehicleModal(rowData) {
        const editForm = $('#editForm');
        editForm[0].reset();

        $('#vehicleId').val(rowData.id);
        $('#vehicleYear').val(rowData.year);
        $('#vehicleMileage').val(rowData.mileage);
        $('#vehicleColor').val(rowData.color);
        $('#vehiclePrice').val(rowData.price);
        $('#vehicleLicensePlate').val(rowData.licensePlate);

        fillBrandModalField(rowData.brandId, $('#vehicleBrandId'));

        editForm.attr('action', `/api/v1/enterprises/${enterpriseId}/vehicles/${rowData.id}`);
        $('#editVehicleModal').modal('show');
    }

    function fillBrandModalField(brandId, brandForm) {
        brandForm.empty();
        brandForm.append('<option disabled>Select brand</option>');
        brandList.forEach(brand => {
            const selected = brand.id === brandId ? 'selected' : '';
            brandForm.append(
                `<option value="${brand.id}" ${selected}>[${brand.id}] ${brand.name}</option>`
            );
        });
    }

    function openDeleteVehicleModal(vehicleId) {
        $('#deleteForm').attr('action', `/api/v1/enterprises/${enterpriseId}/vehicles/${vehicleId}`);
        $('#deleteModal').modal('show');
    }

    $('#enterpriseVehiclesTable tbody').on('click', '.edit-btn', function () {
        const rowData = table.row($(this).parents('tr')).data();
        openEditVehicleModal(rowData);
    }).on('click', '.delete-btn', function () {
        const vehicleId = $(this).data('id');
        openDeleteVehicleModal(vehicleId);
    });

    function getVehicleData(modal, brandFormName) {
        return {
            year: parseInt(modal.find('#vehicleYear').val()),
            mileage: parseFloat(modal.find('#vehicleMileage').val()),
            color: modal.find('#vehicleColor').val(),
            price: parseFloat(modal.find('#vehiclePrice').val()),
            licensePlate: modal.find('#vehicleLicensePlate').val(),
            brand: {
                id: parseInt(modal.find(brandFormName).val())
            }
        };
    }

    $('#addForm').on('submit', function (event) {
        event.preventDefault();

        let modal = $('#addVehicleModal');
        let vehicleData = getVehicleData(modal, '#vehicleBrand');

        $.ajax({
            url: $(this).attr('action'),
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(vehicleData),
            success: function (response) {
                modal.modal('hide');
                table.ajax.reload();
                showSuccessToast(`Vehicle [${response.id}] added successfully!'`);
            },
            error: function (response) {
                showErrorToast('Failed to add vehicle', response.message);
            }
        });
    });

    $('#editForm').on('submit', function (event) {
        event.preventDefault();

        let modal = $('#editVehicleModal');
        let vehicleData = getVehicleData(modal, '#vehicleBrandId');

        $.ajax({
            url: $(this).attr('action'),
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(vehicleData),
            success: function (response) {
                modal.modal('hide');
                table.ajax.reload();
                showSuccessToast(`Vehicle [${response.id}] updated successfully!`);
            },
            error: function (response) {
                showErrorToast('Failed to update vehicle', response.message);
            }
        });
    });

    $('#deleteForm').on('submit', function (event) {
        event.preventDefault();
        $.ajax({
            url: $(this).attr('action'),
            type: 'DELETE',
            success: function () {
                $('#deleteModal').modal('hide');
                table.ajax.reload();
                showSuccessToast('Vehicle deleted successfully!');
            },
            error: function (response) {
                showErrorToast('Failed to delete vehicle', response.message);
            }
        });
    });
});
