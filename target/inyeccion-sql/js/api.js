const API = {
    async request(method, url, data) {
        const options = {
            method,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        };
        if (data) {
            const params = new URLSearchParams();
            for (const [key, value] of Object.entries(data)) {
                params.append(key, value);
            }
            options.body = params.toString();
        }
        const response = await fetch(url, options);
        const json = await response.json();
        if (!response.ok && json.error) {
            throw new Error(json.error);
        }
        return json;
    },

    get(url) {
        return this.request('GET', url);
    },

    post(url, data) {
        return this.request('POST', url, data);
    },

    delete(url) {
        return this.request('DELETE', url);
    },

    login(usuario, password) {
        return this.post('/inyeccion-sql/api/login', { usuario, password });
    },

    logout() {
        return this.post('/inyeccion-sql/api/logout');
    },

    buscarProductos(termino) {
        const url = termino
            ? `/inyeccion-sql/api/productos?buscar=${encodeURIComponent(termino)}`
            : '/inyeccion-sql/api/productos';
        return this.get(url);
    },

    listarUsuarios() {
        return this.get('/inyeccion-sql/api/usuarios');
    },

    crearUsuario(usuario, password, nombre, rol) {
        return this.post('/inyeccion-sql/api/usuarios', { usuario, password, nombre, rol });
    },

    eliminarUsuario(id) {
        return this.delete(`/inyeccion-sql/api/usuarios?id=${id}`);
    }
};
