import {ajaxRequest} from './ajax-client.js';

const BASE_API = '/api/v1/enterprises';

export const enterpriseApi = {
    getEnterprise: async (enterpriseId) => {
        return await ajaxRequest({
            url: `${BASE_API}/${enterpriseId}`,
            method: 'GET'
        });
    },

    updateEnterprise: async (enterprise) => {
        return await ajaxRequest({
            url: `${BASE_API}/${enterprise.id}`,
            method: 'PUT',
            data: enterprise,
            headers: {'Content-Type': 'application/json'}
        });
    },

    deleteEnterprise: async (enterpriseId) => {
        return await ajaxRequest({
            url: `${BASE_API}/${enterpriseId}`,
            method: 'DELETE'
        });
    },

    createEnterprise: async (enterprise) => {
        return await ajaxRequest({
            url: `${BASE_API}`,
            method: 'POST',
            data: enterprise,
            headers: {'Content-Type': 'application/json'}
        });
    },

    updateEnterpriseVehicle: async (enterpriseId, vehicleId, vehicleData) => {
        return await ajaxRequest({
            url: `${BASE_API}/${enterpriseId}/vehicles/${vehicleId}`,
            method: 'PUT',
            data: vehicleData,
            headers: {'Content-Type': 'application/json'}
        });
    },

    deleteEnterpriseVehicle: async (enterpriseId, vehicleId) => {
        return await ajaxRequest({
            url: `${BASE_API}/${enterpriseId}/vehicles/${vehicleId}`,
            method: 'DELETE'
        });
    },

    createEnterpriseVehicle: async (enterpriseId, vehicleData) => {
        return await ajaxRequest({
            url: `${BASE_API}/${enterpriseId}/vehicles`,
            method: 'POST',
            data: vehicleData,
            headers: {'Content-Type': 'application/json'}
        });
    }
};
