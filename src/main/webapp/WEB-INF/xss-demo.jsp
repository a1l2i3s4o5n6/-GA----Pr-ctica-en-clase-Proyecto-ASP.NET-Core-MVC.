<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Demo XSS - Escapado vs Sin Escapar</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <div class="nav-left"><h2>Demo XSS (Cross-Site Scripting)</h2></div>
        <div class="nav-right">
            <span>${sessionScope.nombre} (${sessionScope.rol})</span>
            <a href="${pageContext.request.contextPath}/admin" class="nav-link">Admin</a>
            <a href="${pageContext.request.contextPath}/dashboard.html" class="nav-link">Dashboard</a>
            <form action="${pageContext.request.contextPath}/api/logout" method="POST" style="display:inline">
                <button type="submit" class="btn-danger">Cerrar Sesión</button>
            </form>
        </div>
    </nav>

    <main class="main-content">
        <section class="admin-section">
            <h1>Demostración de XSS</h1>
            <p class="subtitle">Ingresa código HTML/JS y observa la diferencia entre mostrar sin escapar y escapando</p>

            <form method="POST" action="${pageContext.request.contextPath}/xss-demo" class="search-box">
                <input type="text" name="input" placeholder='Ej: <script>alert("XSS")</script>'
                       value="<c:out value="${input}" />" required>
                <button type="submit" class="btn-primary">Probar</button>
            </form>
        </section>

        <c:if test="${not empty input}">
            <section class="admin-section" style="margin-top:20px;">
                <h2>Resultados</h2>

                <div style="background:#fff5f5;border:2px solid #d32f2f;border-radius:8px;padding:15px;margin:10px 0;">
                    <h3 style="color:#d32f2f;">VULNERABLE — Sin escapar (${input})</h3>
                    <p style="font-size:0.85em;color:#666;">
                        Usando <code>${input}</code> — El navegador interpreta el HTML/JS
                    </p>
                    <div style="border:1px dashed #d32f2f;padding:10px;border-radius:4px;margin-top:10px;">
                        <%= request.getAttribute("input") != null ? request.getAttribute("input") : "" %>
                    </div>
                </div>

                <div style="background:#f5fff5;border:2px solid #2e7d32;border-radius:8px;padding:15px;margin:10px 0;">
                    <h3 style="color:#2e7d32;">SEGURO — Escapado con &lt;c:out&gt;</h3>
                    <p style="font-size:0.85em;color:#666;">
                        Usando <code>&lt;c:out value="${input}" /&gt;</code> — JSTL escapa &lt; &gt; &amp;
                    </p>
                    <div style="border:1px dashed #2e7d32;padding:10px;border-radius:4px;margin-top:10px;background:white;">
                        <c:out value="${input}" />
                    </div>
                </div>

                <div class="search-info">
                    <strong>¿Qué pasó?</strong><br>
                    <strong>Sin escapar</strong>: el navegador ejecutó el código HTML/JS (si pusiste un script, se ejecutó).<br>
                    <strong>Con &lt;c:out&gt;</strong>: los caracteres &lt; &gt; se convirtieron en <code>&amp;lt; &amp;gt;</code>,
                    por lo que el código se muestra como <strong>texto plano</strong> y no se ejecuta.
                </div>
            </section>
        </c:if>

        <c:if test="${empty input}">
            <section class="admin-section" style="margin-top:20px;">
                <div class="search-info">
                    <strong>Instrucciones:</strong> Escribe código HTML/JS en el campo de arriba y presiona "Probar".
                    Por ejemplo: <code>&lt;h1&gt;Hola&lt;/h1&gt;</code> o <code>&lt;script&gt;alert('XSS')&lt;/script&gt;</code>
                </div>
            </section>
        </c:if>
    </main>
</body>
</html>
