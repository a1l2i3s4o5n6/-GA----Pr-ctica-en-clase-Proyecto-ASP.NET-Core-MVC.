document.addEventListener('DOMContentLoaded', () => {
    const logoutBtn = document.getElementById('logoutBtn');
    const usersTableDiv = document.getElementById('usersTable');
    const showCreateBtn = document.getElementById('showCreateForm');
    const createForm = document.getElementById('createForm');
    const saveUserBtn = document.getElementById('saveUserBtn');

    async function cargarUsuarios() {
        usersTableDiv.innerHTML = '<div class="loading">Cargando...</div>';
        try {
            const users = await API.listarUsuarios();
            if (users.error) {
                usersTableDiv.innerHTML = `<div class="empty-state">${users.error}</div>`;
                return;
            }
            let html = '<table><thead><tr><th>ID</th><th>Usuario</th><th>Nombre</th><th>Rol</th><th>Acción</th></tr></thead><tbody>';
            users.forEach(u => {
                const roleClass = u.rol === 'admin' ? 'role-admin' : 'role-user';
                html += `
                    <tr>
                        <td>${u.id}</td>
                        <td>${escapeHtml(u.usuario)}</td>
                        <td>${escapeHtml(u.nombre)}</td>
                        <td><span class="role-badge ${roleClass}">${u.rol}</span></td>
                        <td><button class="btn-danger" onclick="eliminarUsuario(${u.id})">Eliminar</button></td>
                    </tr>
                `;
            });
            html += '</tbody></table>';
            usersTableDiv.innerHTML = html;
        } catch (err) {
            usersTableDiv.innerHTML = `<div class="empty-state">${escapeHtml(err.message)}</div>`;
        }
    }

    window.eliminarUsuario = async (id) => {
        if (!confirm('¿Eliminar este usuario?')) return;
        try {
            await API.eliminarUsuario(id);
            cargarUsuarios();
        } catch (err) {
            alert(err.message);
        }
    };

    showCreateBtn.addEventListener('click', () => {
        createForm.classList.toggle('hidden');
    });

    saveUserBtn.addEventListener('click', async () => {
        const usuario = document.getElementById('newUsuario').value.trim();
        const password = document.getElementById('newPassword').value;
        const nombre = document.getElementById('newNombre').value.trim();
        const rol = document.getElementById('newRol').value;

        if (!usuario || !password || !nombre) {
            alert('Todos los campos son requeridos');
            return;
        }

        try {
            await API.crearUsuario(usuario, password, nombre, rol);
            document.getElementById('newUsuario').value = '';
            document.getElementById('newPassword').value = '';
            document.getElementById('newNombre').value = '';
            createForm.classList.add('hidden');
            cargarUsuarios();
        } catch (err) {
            alert(err.message);
        }
    });

    logoutBtn.addEventListener('click', async () => {
        await API.logout();
        window.location.href = 'index.html';
    });

    cargarUsuarios();

    function escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }
});
