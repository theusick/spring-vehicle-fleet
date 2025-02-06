import {ajaxRequest} from './ajax-client.js';

const BASE_API = '/api/v1/vehicles';

export const vehicleApi = {
    getVehicleBrands: async () => {
        return await ajaxRequest({
            url: `${BASE_API}/brands`,
            method: 'GET'
        });
    },
};
