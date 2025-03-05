document.getElementById("loginForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const correo = document.getElementById("correo").value;
    const contraseña = document.getElementById("contraseña").value;
    await login(correo, contraseña);
});
