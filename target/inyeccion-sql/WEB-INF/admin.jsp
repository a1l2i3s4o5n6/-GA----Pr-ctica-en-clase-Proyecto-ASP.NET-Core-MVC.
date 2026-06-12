<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Admin - Listado con JSTL</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <div class="nav-left"><h2>Panel Admin - JSP + JSTL</h2></div>
        <div class="nav-right">
            <span>${sessionScope.nombre} (${sessionScope.rol})</span>
            <a href="${pageContext.request.contextPath}/dashboard.html" class="nav-link">Dashboard</a>
            <a href="${pageContext.request.contextPath}/xss-demo" class="nav-link">Demo XSS</a>
            <form action="${pageContext.request.contextPath}/api/logout" method="POST" style="display:inline">
                <button type="submit" class="btn-danger">Cerrar Sesión</button>
            </form>
        </div>
    </nav>

    <main class="main-content">
        <section class="admin-section">
            <h1>Usuarios con <code>&lt;c:forEach&gt;</code></h1>
            <p class="subtitle">Datos renderizados desde el servidor con JSP + JSTL</p>

            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>

            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Usuario</th>
                            <th>Nombre</th>
                            <th>Rol</th>
                            <th>Activo</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${usuarios}" var="u" varStatus="status">
                            <tr>
                                <td>${u.id}</td>
                                <td><c:out value="${u.usuario}" /></td>
                                <td><c:out value="${u.nombre}" /></td>
                                <td>
                                    <span class="role-badge ${u.rol eq 'admin' ? 'role-admin' : 'role-user'}">
                                        <c:out value="${u.rol}" />
                                    </span>
                                </td>
                                <td>${u.activo ? 'Sí' : 'No'}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <c:if test="${empty usuarios}">
                <div class="empty-state">No hay usuarios registrados.</div>
            </c:if>
        </section>
    </main>
</body>
</html>
