document.addEventListener('DOMContentLoaded', initializeSidebar);

/**
 * Initializes the sidebar logic.
 */
function initializeSidebar() {
    const sidebar = document.querySelector('#sidebar');
    if (!sidebar) {
        return;
    }

    const activeLink = getActiveLink(sidebar);
    if (!activeLink) {
        console.warn('No active-link attribute found on the sidebar');
        return;
    }
    highlightActiveLink(sidebar, activeLink);
}

/**
 * Retrieves the `active-link` attribute value from the sidebar element.
 * @param {HTMLElement} sidebar - The sidebar element.
 * @returns {string|null} The active link value or null if the attribute is missing.
 */
function getActiveLink(sidebar) {
    return sidebar.getAttribute('active-link');
}

/**
 * Highlights the active link and expands its related submenu if necessary.
 * @param {HTMLElement} sidebar - The sidebar element.
 * @param {string} activeLink - The identifier of the active link.
 */
function highlightActiveLink(sidebar, activeLink) {
    const sidebarLinks = sidebar.querySelectorAll('.sidebar-link');

    sidebarLinks.forEach(link => {
        if (isActiveLink(link, activeLink)) {
            setActiveState(link);
            expandParentMenu(link);
        }
    });
}

/**
 * Checks whether a link is active.
 * @param {HTMLElement} link - The sidebar link element.
 * @param {string} activeLink - The identifier of the active link.
 * @returns {boolean} True if the link is active, false otherwise.
 */
function isActiveLink(link, activeLink) {
    const href = link.getAttribute('href');
    return href && href.includes(activeLink);
}

/**
 * Sets the active state for a link.
 * @param {HTMLElement} link - The sidebar link element.
 */
function setActiveState(link) {
    link.classList.add('active');
}

/**
 * Expands the parent menu if the active link is inside a submenu.
 * @param {HTMLElement} link - The sidebar link element.
 */
function expandParentMenu(link) {
    const parentItem = link.closest('.sidebar-item');

    if (parentItem) {
        const parentLink = parentItem.querySelector('.sidebar-link.has-dropdown');
        if (parentLink) {
            parentLink.classList.remove('collapsed');
            parentLink.setAttribute('aria-expanded', 'true');
        }

        const submenu = parentItem.querySelector('.sidebar-dropdown');
        if (submenu) {
            submenu.classList.add('show');
        }
    }
}
