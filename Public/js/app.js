const API_URL = 'http://localhost:8081/solicitacoes';

// Carrega a lista de solicitações
async function carregarSolicitacoes() {
    const lista = document.getElementById("listaSolicitacoes");
    if (!lista) return; 

    lista.innerHTML = "<tr><td colspan='7'>Carregando...</td></tr>"; 

    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error("Erro ao carregar solicitações");

        const solicitacoes = await response.json();
        lista.innerHTML = solicitacoes.map(solicitacao => `
            <tr>
                <td>${solicitacao.id}</td>
                <td>${solicitacao.nomePassageiro}</td>
                <td>${solicitacao.ciaAerea}</td>
                <td>${solicitacao.dataHoraSaida}</td>
                <td>${solicitacao.dataHoraChegada}</td>
                <td>${solicitacao.cidadeOrigem}</td>
                <td>${solicitacao.cidadeDestino}</td>
            </tr>
        `).join("");
    } catch (error) {
        console.error(error);
        lista.innerHTML = "<tr><td colspan='7'>Erro ao carregar</td></tr>"; 
    }
}

// Sincroniza as solicitações
async function sincronizarSolicitacoes() {
    try {
        const response = await fetch(`${API_URL}/sincronizar`, { method: "GET" });
        if (!response.ok) throw new Error("Erro ao sincronizar solicitações");
        
        alert("Solicitações sincronizadas com sucesso!");
        carregarSolicitacoes(); 
    } catch (error) {
        console.error(error);
        alert("Erro ao sincronizar solicitações.");
    }
}

// Cria uma nova solicitação
document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("formSolicitacao");
    if (!form) return; 

    form.addEventListener("submit", async function(event) {
        event.preventDefault();

        const novaSolicitacao = {
            nomePassageiro: document.getElementById("nomePassageiro").value.trim(),
            cidadeOrigem: document.getElementById("cidadeOrigem").value.trim(),
            cidadeDestino: document.getElementById("cidadeDestino").value.trim(),
            dataHoraSaida: document.getElementById("dataIda").value,
            dataHoraChegada: document.getElementById("dataVolta").value,
            ciaAerea: document.getElementById("ciaAerea").value, 
            status: "Pendente"
        };

        if (Object.values(novaSolicitacao).some(value => !value)) {
            alert("Todos os campos são obrigatórios.");
            return;
        }

        try {
            const response = await fetch(API_URL, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(novaSolicitacao)
            });

            if (!response.ok) throw new Error("Erro ao criar solicitação");

            alert("Solicitação criada!");
            carregarSolicitacoes(); 
            form.reset();
        } catch (error) {
            console.error(error);
            alert("Erro ao criar solicitação.");
        }
    });

    // Evento para sincronizar solicitações ao clicar no botão
    const btnSincronizar = document.getElementById("btnSincronizar");
    if (btnSincronizar) {
        btnSincronizar.addEventListener("click", sincronizarSolicitacoes);
    }
});

// Carrega as solicitações inicialmente
carregarSolicitacoes();