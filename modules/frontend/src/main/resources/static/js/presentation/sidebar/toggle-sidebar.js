const toggleBtn = document.getElementById('toggleSidebar');
const sidebar = document.querySelector('#sidebar');
const mainContent = document.querySelector('.main-content');

const root = document.documentElement;
const CSS_PROPS = {
    EXPAND_WIDTH: '--sidebar-expand-width',
    COLLAPSE_WIDTH: '--sidebar-not-expand-width',
    EXPAND_MAX_WIDTH: '--main-sidebar-expand-max-width',
    COLLAPSE_MAX_WIDTH: '--main-sidebar-not-expand-max-width',
};

function updateMainContentStyles(isExpanded) {
    const computedStyles = getComputedStyle(root);
    const sidebarWidth = isExpanded
        ? computedStyles.getPropertyValue(CSS_PROPS.EXPAND_WIDTH).trim()
        : computedStyles.getPropertyValue(CSS_PROPS.COLLAPSE_WIDTH).trim();

    const mainMaxWidth = isExpanded
        ? computedStyles.getPropertyValue(CSS_PROPS.EXPAND_MAX_WIDTH).trim()
        : computedStyles.getPropertyValue(CSS_PROPS.COLLAPSE_MAX_WIDTH).trim();

    mainContent.style.marginLeft = sidebarWidth;
    mainContent.style.maxWidth = mainMaxWidth;
}

function resetDropdowns() {
    Array.from(sidebar.getElementsByClassName('sidebar-link has-dropdown'))
        .forEach(link => {
            link.classList.add('collapsed');
            link.setAttribute('aria-expanded', 'false');

            const targetId = link.getAttribute('data-bs-target');
            if (targetId) {
                const submenu = document.querySelector(targetId);
                if (submenu) submenu.classList.remove('show');
            }
        });
}

toggleBtn.addEventListener('click', () => {
    if (!sidebar) {
        return;
    }

    const isExpanded = sidebar.classList.toggle('expand');
    updateMainContentStyles(isExpanded);
    resetDropdowns();
});
