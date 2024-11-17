export function toggleSidebar() {
    document.getElementById('toggleSidebar').addEventListener('click', function () {
        const sidebar = document.querySelector('.sidebar');
        const mainContent = document.querySelector('.main-content-container');

        sidebar.classList.toggle('collapsed');

        if (sidebar.classList.contains('collapsed')) {
            mainContent.style.marginLeft = "40px";
        } else {
            mainContent.style.marginLeft = "80px";
        }
    });
}
