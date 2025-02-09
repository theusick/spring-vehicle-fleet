import {showErrorToast, showSuccessToast} from '../../../presentation/toasts.js';
import {vehicleApi} from '../../../api/vehicle-api.js';
import {enterpriseApi} from "../../../api/enterprise-api.js";

let enterpriseId;
let brandList = [];

$(document).ready(async function () {
    enterpriseId = window.location.pathname.split('/')[2];

    await loadEnterpriseName(enterpriseId);

    brandList = await loadBrands();

    initEnterpriseVehiclesTable(enterpriseId, brandList);

    setupEventHandlers();
});

async function loadEnterpriseName(enterpriseId) {
    try {
        const enterprise = await enterpriseApi.getEnterprise(enterpriseId);
        $('#page-title').text(`Enterprise "${enterprise.name}" vehicles`);
    } catch (error) {
        if (error.status === 403) {
            showErrorToast('Access denied.');
            $('#enterpriseVehiclesTable').hide();
        } else {
            showErrorToast(error.message);
        }
    }
}

async function loadBrands() {
    try {
        return await vehicleApi.getVehicleBrands();
    } catch {
        showErrorToast('Failed to load brands list');
        return [];
    }
}

function initEnterpriseVehiclesTable(enterpriseId, brandList) {
    $('#enterpriseVehiclesTable').DataTable({
        ajax: {
            url: `/api/v1/enterprises/${enterpriseId}/vehicles`,
            type: 'GET',
            dataSrc: parseResponseData,
            error: handleTableError,
            /* Request parameters */
            data: function (data) {
                const pageNumber = data.start / data.length;
                return {
                    page: pageNumber >= 0 ? pageNumber : 0,
                    size: data.length
                };
            },
        },
        columns: getTableColumns(brandList),
        rowId: 'id',
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
}

function parseResponseData(response) {
    if (response.page) {
        response.recordsTotal = response.page.totalElements;
        response.recordsFiltered = response.page.totalElements;
    } else {
        response.recordsTotal = 0;
        response.recordsFiltered = 0;
    }
    return response.content;
}

function handleTableError(xhr) {
    if (xhr.status === 403) {
        showErrorToast('Access denied: failed to load vehicle data.');
        $('#enterpriseVehiclesTable').hide();
    } else {
        showErrorToast('Failed to load vehicles data.');
    }
}

function getTableColumns(brandList) {
    return [
        {data: 'id'},
        {data: 'year'},
        {data: 'mileage'},
        {data: 'color'},
        {data: 'price'},
        {data: 'licensePlate'},
        {
            data: 'purchaseDate',
            render: data => {
                if (!data) return '';
                const utcDate = new Date(data);
                return utcDate.toLocaleString();
            }
        },
        {
            data: 'brand',
            render: brandId => {
                const brand = brandList.find(b => b.id === brandId);
                return brand ? brand.name : 'Unknown';
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
    return `
        <button class="btn btn-primary btn-sm edit-btn" data-id="${row.id}">
            <i class="bi bi-pencil-square"></i> Edit
        </button>
        <button class="btn btn-danger btn-sm delete-btn" data-id="${row.id}">
            <i class="bi bi-trash"></i> Delete
        </button>`;
}

function setupEventHandlers() {
    $('#enterpriseVehiclesTable tbody')
        .on('click', '.edit-btn', handleEditClick)
        .on('click', '.delete-btn', handleDeleteClick);

    $('#addForm').on('submit', handleAddSubmit);
    $('#editForm').on('submit', handleEditSubmit);
    $('#deleteForm').on('submit', handleDeleteSubmit);
}

function handleEditClick() {
    const table = $('#enterpriseVehiclesTable').DataTable();
    const rowData = table.row($(this).closest('tr')).data();
    openEditVehicleModal(rowData);
}

function handleDeleteClick() {
    const vehicleId = $(this).data('id');
    openDeleteVehicleModal(vehicleId);
}

async function handleAddSubmit(event) {
    event.preventDefault();

    const modal = $('#addVehicleModal');
    console.log(new Date(modal.find('#vehiclePurchaseDate').val()).toISOString());
    const vehicleData = getVehicleData(modal, '#vehicleBrand');

    try {
        const response = await enterpriseApi.createEnterpriseVehicle(enterpriseId, vehicleData);
        modal.modal('hide');
        $('#enterpriseVehiclesTable').DataTable().ajax.reload();
        showSuccessToast(`Vehicle [${response.id}] added successfully!`);
    } catch (error) {
        showErrorToast('Failed to add vehicle', error.message);
    }
}

async function handleEditSubmit(event) {
    event.preventDefault();

    const modal = $('#editVehicleModal');
    const vehicleData = getVehicleData(modal, '#vehicleBrandId');
    const vehicleId = $('#updateObjectId').val();

    try {
        const response = await enterpriseApi.updateEnterpriseVehicle(enterpriseId, vehicleId, vehicleData);
        modal.modal('hide');
        $('#enterpriseVehiclesTable').DataTable().ajax.reload();
        showSuccessToast(`Vehicle [${response.id}] updated successfully!`);
    } catch (error) {
        showErrorToast(`Failed to update vehicle [${vehicleId}]`, error.message);
    }
}

async function handleDeleteSubmit(event) {
    event.preventDefault();

    const vehicleId = $('#deleteObjectId').val();

    try {
        await enterpriseApi.deleteEnterpriseVehicle(enterpriseId, vehicleId);
        $('#deleteModal').modal('hide');
        $('#enterpriseVehiclesTable').DataTable().ajax.reload();
        showSuccessToast(`Vehicle [${vehicleId}] deleted successfully!`);
    } catch (error) {
        showErrorToast(`Failed to delete vehicle [${vehicleId}]`, error.message);
    }
}

function openAddVehicleModal() {
    const addForm = $('#addForm');
    addForm.trigger('reset');

    fillBrandModalField(null, $('#vehicleBrand'));

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
    $('#vehiclePurchaseDate').val(rowData.purchaseDate);

    fillBrandModalField(rowData.brandId, $('#vehicleBrandId'));

    $('#updateObjectId').val(rowData.id);
    $('#editVehicleModal').modal('show');
}

function openDeleteVehicleModal(vehicleId) {
    $('#deleteObjectId').val(vehicleId);
    $('#deleteModal').modal('show');
}

function getVehicleData(modal, brandFormSelector) {
    return {
        year: parseInt(modal.find('#vehicleYear').val()),
        mileage: parseInt(modal.find('#vehicleMileage').val()),
        color: modal.find('#vehicleColor').val(),
        price: parseFloat(modal.find('#vehiclePrice').val()),
        licensePlate: modal.find('#vehicleLicensePlate').val(),
        purchaseDate: new Date(modal.find('#vehiclePurchaseDate').val()).toISOString(),
        brand: {
            id: parseInt(modal.find(brandFormSelector).val())
        }
    };
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
