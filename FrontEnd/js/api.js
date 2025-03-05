const AUTH_SERVER = "http://localhost:9092";
const API_SERVER = "http://localhost:9091";

async function login(correo, contraseña) {
    const response = await fetch(`${AUTH_SERVER}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ correo, contraseña })
    });
    const data = await response.json();
    if (response.ok) {
        localStorage.setItem("token", data.token);
        window.location.href = "comprador.html";
    } else {
        alert("Error en el inicio de sesión");
    }
}

async function obtenerSubastas() {
    const token = localStorage.getItem("token");
    const response = await fetch(`${API_SERVER}/api/subastas`, {
        headers: { "Authorization": `Bearer ${token}` }
    });
    return await response.json();
}

async function enviarPuja(autoId, monto) {
    const token = localStorage.getItem("token");
    await fetch(`${API_SERVER}/api/pujas`, {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ autoId, monto })
    });
}
