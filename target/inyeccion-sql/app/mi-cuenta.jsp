<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mi Cuenta - Área Protegida</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <div class="nav-left"><h2>Mi Cuenta — Área Protegida</h2></div>
        <div class="nav-right">
            <span>${sessionScope.nombre} (${sessionScope.rol})</span>
            <a href="${pageContext.request.contextPath}/dashboard.html" class="nav-link">Dashboard</a>
            <form action="${pageContext.request.contextPath}/api/logout" method="POST" style="display:inline">
                <button type="submit" class="btn-danger">Cerrar Sesión</button>
            </form>
        </div>
    </nav>
    <main class="main-content">
        <section class="admin-section">
            <h1>Área Protegida</h1>
            <div class="search-info" style="background:#e8f5e9;color:#2e7d32;">
                <strong>✓ Acceso autorizado</strong> — Esta página solo es visible con sesión activa.
            </div>
            <div style="margin-top:20px;padding:20px;background:#f5f5f5;border-radius:8px;">
                <h3>Datos de tu sesión:</h3>
                <ul style="margin-top:10px;line-height:1.8;">
                    <li><strong>Usuario:</strong> ${sessionScope.usuario}</li>
                    <li><strong>Nombre:</strong> ${sessionScope.nombre}</li>
                    <li><strong>Rol:</strong> ${sessionScope.rol}</li>
                    <li><strong>ID de sesión:</strong> ${pageContext.session.id}</li>
                </ul>
            </div>
            <div class="search-info" style="margin-top:20px;">
                <strong>Prueba de seguridad:</strong>
                <ol style="margin:10px 0 0 20px;">
                    <li>Cierra sesión usando el botón de arriba</li>
                    <li>Intenta acceder directamente a <code>/app/mi-cuenta.jsp</code></li>
                    <li>El filtro <strong>AuthFilter</strong> redirigirá al login automáticamente</li>
                </ol>
            </div>
        </section>
    </main>
</body>
</html>
