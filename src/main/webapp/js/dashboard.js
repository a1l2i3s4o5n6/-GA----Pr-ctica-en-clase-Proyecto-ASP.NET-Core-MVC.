document.addEventListener('DOMContentLoaded', async () => {
    try {
        const userSpan = document.getElementById('userInfo');
        const adminLink = document.getElementById('adminLink');

        const sessionCheck = await API.buscarProductos('');
        if (sessionCheck.error) {
            window.location.href = 'index.html';
            return;
        }
    } catch (e) {
        window.location.href = 'index.html';
        return;
    }

    const searchInput = document.getElementById('searchInput');
    const searchBtn = document.getElementById('searchBtn');
    const resultsDiv = document.getElementById('results');
    const logoutBtn = document.getElementById('logoutBtn');

    async function cargarProductos(termino) {
        resultsDiv.innerHTML = '<div class="loading">Buscando...</div>';
        try {
            const productos = await API.buscarProductos(termino);
            if (productos.length === 0) {
                resultsDiv.innerHTML = '<div class="empty-state">No se encontraron productos</div>';
                return;
            }
            let html = '<div class="product-grid">';
            productos.forEach(p => {
                html += `
                    <div class="product-card">
                        <h3>${escapeHtml(p.nombre)}</h3>
                        <span class="category">${escapeHtml(p.categoria)}</span>
                        <p class="description">${escapeHtml(p.descripcion || 'Sin descripción')}</p>
                        <div class="details">
                            <span class="price">$${p.precio.toFixed(2)}</span>
                            <span class="stock">Stock: ${p.stock}</span>
                        </div>
                    </div>
                `;
            });
            html += '</div>';
            resultsDiv.innerHTML = html;
        } catch (err) {
            resultsDiv.innerHTML = `<div class="empty-state">${escapeHtml(err.message)}</div>`;
        }
    }

    searchBtn.addEventListener('click', () => {
        const termino = searchInput.value.trim();
        cargarProductos(termino || '');
    });

    searchInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') searchBtn.click();
    });

    logoutBtn.addEventListener('click', async () => {
        await API.logout();
        window.location.href = 'index.html';
    });

    cargarProductos('');

    function escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }
});
