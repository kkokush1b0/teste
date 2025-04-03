const API_URL = 'http://localhost:8081/solicitacoes';

// Carrega a lista de solicitações
async function carregarSolicitacoes() {
    const lista = document.getElementById("listaSolicitacoes");
    if (!lista) return; 

    lista.innerHTML = "<tr><td colspan='4'>Carregando...</td></tr>";

    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error("Erro ao carregar solicitações");

        const solicitacoes = await response.json();
        lista.innerHTML = solicitacoes.map(solicitacao => `
            <tr>
                <td>${solicitacao.id}</td>
                <td>${solicitacao.nomePassageiro}</td>
                <td>${solicitacao.cidadeOrigem}</td>
                <td>${solicitacao.cidadeDestino}</td>
            </tr>
        `).join("");
    } catch (error) {
        console.error(error);
        lista.innerHTML = "<tr><td colspan='4'>Erro ao carregar</td></tr>";
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
            status: "Pendente"
        };

        // Verifica se todos os campos foram preenchidos
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
            window.location.href = "home.html";
        } catch (error) {
            console.error(error);
            alert("Erro ao criar solicitação.");
        }
    });
});

carregarSolicitacoes();
