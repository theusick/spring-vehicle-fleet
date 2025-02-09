export function getUTCOffset(timeZone) {
    try {
        if (timeZone === 'UTC') {
            return 0;
        }

        const formatter = new Intl.DateTimeFormat('en-ca', {
            timeZone,
            timeZoneName: 'longOffset'
        });

        const formatted = formatter.formatToParts(new Date());
        const offsetPart = formatted.find(part =>
            part.type === 'timeZoneName');

        if (!offsetPart) return null;

        const match = offsetPart.value.match(/([+-]\d{2}:\d{2})/);
        return match ? match[1] : null;
    } catch (error) {
        return null;
    }
}
