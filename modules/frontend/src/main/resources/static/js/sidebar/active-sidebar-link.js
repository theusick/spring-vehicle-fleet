export function activeSidebarLink() {
    document.addEventListener('DOMContentLoaded', function () {
        const currentPage = window.location.pathname;

        document.querySelectorAll('.sidebar .nav-link').forEach(link => {
            if (link.href.includes(currentPage)) {
                link.classList.add('active');

                const submenu = link.closest('.sub-menu');
                if (submenu) {
                    const parentItem = submenu.closest('.sidebar-item');
                    const collapseButton = parentItem.querySelector('a[data-bs-toggle="collapse"]');

                    if (!submenu.classList.contains('show')) {
                        collapseButton.click();
                    }
                }
            }
        });
    });
}
