const WS_SERVER = "ws://localhost:9091/ws/subastas";

let socket = new WebSocket(WS_SERVER);

socket.onopen = () => {
    console.log("Conectado al servidor WebSocket");
};

socket.onmessage = (event) => {
    const data = JSON.parse(event.data);
    console.log("Nueva puja recibida:", data);

    // Buscar el auto en la interfaz y actualizar su puja más alta
    actualizarPujaEnInterfaz(data);
};

function actualizarPujaEnInterfaz(puja) {
    const listaAutos = document.getElementById("listaAutos").children;
    for (let item of listaAutos) {
        if (item.dataset.id == puja.autoId) {
            item.innerHTML = `
                ${puja.marca} ${puja.modelo} - <strong>Oferta más alta: $${puja.monto}</strong>
                <button class="btn btn-success btn-sm" onclick="abrirModalPuja(${puja.autoId})">Pujar</button>
            `;
        }
    }
}

async function enviarPuja() {
    const autoId = document.getElementById("autoId").value;
    const monto = document.getElementById("montoPuja").value;
    await enviarPuja(autoId, monto);
    
    alert("Puja realizada con éxito");
    
    // Enviar actualización manual al WebSocket
    socket.send(JSON.stringify({ autoId, monto }));
}
