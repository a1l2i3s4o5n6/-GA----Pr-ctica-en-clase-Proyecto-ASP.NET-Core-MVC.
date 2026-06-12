document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('loginForm');
    const errorDiv = document.getElementById('errorMessage');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        errorDiv.classList.add('hidden');

        const usuario = document.getElementById('usuario').value.trim();
        const password = document.getElementById('password').value;

        if (!usuario || !password) {
            showError('Todos los campos son requeridos');
            return;
        }

        try {
            const result = await API.login(usuario, password);
            if (result.success) {
                window.location.href = 'dashboard.html';
            }
        } catch (err) {
            showError(err.message || 'Error al iniciar sesión');
        }
    });

    function showError(msg) {
        errorDiv.textContent = msg;
        errorDiv.classList.remove('hidden');
    }
});
