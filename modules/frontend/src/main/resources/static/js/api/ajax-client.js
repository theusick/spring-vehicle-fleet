import {ApiError} from '../error/api-error.js';

export function ajaxRequest(
    {
        url,
        method = 'GET',
        data = null,
        headers = {}
    }) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: url,
            method: method,
            data: data ? JSON.stringify(data) : null,
            headers: headers,
            contentType: 'application/json',
            dataType: 'json',
            success: function (response) {
                resolve(response);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                let detail;
                try {
                    const responseJson = JSON.parse(jqXHR.responseText);
                    detail = responseJson.detail || '';
                } catch (e) {
                    detail = jqXHR.responseText || errorThrown;
                }

                const apiError = new ApiError(
                    errorThrown || 'Unknown error occurred',
                    jqXHR.status,
                    detail || 'Something went wrong, please try again later.'
                );

                reject(apiError);
            }
        });
    });
}
