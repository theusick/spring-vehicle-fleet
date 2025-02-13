export function openAndFillModal(modalId, formId, data, fieldsMapping) {
    const form = $(formId);

    for (let field in fieldsMapping) {
        if (fieldsMapping.hasOwnProperty(field)) {
            const fieldSelector = fieldsMapping[field];
            let value = data;

            const fieldPath = field.split('.');

            for (let part of fieldPath) {
                if (value[part] !== undefined) {
                    value = value[part];
                } else {
                    value = '';
                    break;
                }
            }

            const fieldElement = form.find(fieldSelector);
            if (fieldElement.length && fieldElement.is('select')) {
                fieldElement.val(value).change();
            } else if (fieldElement.length) {
                fieldElement.val(value);
            }
        }
    }

    $(modalId).modal('show');
}
