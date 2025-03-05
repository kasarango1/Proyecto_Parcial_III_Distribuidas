document.addEventListener("DOMContentLoaded", async () => {
    const subastas = await obtenerSubastas();
    const subastasContainer = document.getElementById("subastasContainer");

    subastas.forEach(subasta => {
        const card = document.createElement("div");
        card.className = "col-md-4 mb-3";
        card.innerHTML = `
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Subasta #${subasta.idSubasta}</h5>
                    <p>Estado: ${subasta.estado}</p>
                    <button class="btn btn-primary" onclick="verAutos(${subasta.idSubasta})">Ver Autos</button>
                </div>
            </div>
        `;
        subastasContainer.appendChild(card);
    });
});

async function verAutos(subastaId) {
    const subastas = await obtenerSubastas();
    const subasta = subastas.find(s => s.idSubasta === subastaId);
    const listaAutos = document.getElementById("listaAutos");
    listaAutos.innerHTML = "";

    subasta.autos.forEach(auto => {
        const item = document.createElement("li");
        item.className = "list-group-item d-flex justify-content-between align-items-center";
        item.innerHTML = `
            ${auto.marca} ${auto.modelo} - $${auto.precioBase}
            <button class="btn btn-success btn-sm" onclick="abrirModalPuja(${auto.idAuto})">Pujar</button>
        `;
        listaAutos.appendChild(item);
    });

    new bootstrap.Modal(document.getElementById("modalAutos")).show();
}

function abrirModalPuja(autoId) {
    document.getElementById("autoId").value = autoId;
    new bootstrap.Modal(document.getElementById("modalPuja")).show();
}

async function enviarPuja() {
    const autoId = document.getElementById("autoId").value;
    const monto = document.getElementById("montoPuja").value;
    await enviarPuja(autoId, monto);
    alert("Puja realizada con éxito");
}
