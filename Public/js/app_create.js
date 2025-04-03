document.getElementById('btnCriar').addEventListener('click', function () {
    const nomePassageiro = document.getElementById('nome_pax').value.trim();
    const cidadeOrigem = document.getElementById('origem').value.trim();
    const cidadeDestino = document.getElementById('destino').value.trim();
    const dataHoraSaida = document.getElementById('data_ida').value;
    const dataHoraChegada = document.getElementById('data_volta').value;

    if (!nomePassageiro || !cidadeOrigem || !cidadeDestino || !dataHoraSaida || !dataHoraChegada) {
        alert("Todos os campos são obrigatórios.");
        return;
    }

    const formatarDataHora = (data) => {
        return `${data}T12:00:00`;  
    };

    const novaSolicitacao = {
        nomePassageiro,
        cidadeOrigem,
        cidadeDestino,
        dataHoraSaida: formatarDataHora(dataHoraSaida),
        dataHoraChegada: formatarDataHora(dataHoraChegada),
        status: "Pendente",
        ciaAerea: "Fictício Airlines" 
    };

    // Recupera as solicitações do localStorage ou cria um array vazio
    const solicitacoes = JSON.parse(localStorage.getItem('solicitacoes')) || [];

    solicitacoes.push(novaSolicitacao);

    localStorage.setItem('solicitacoes', JSON.stringify(solicitacoes));

    alert('Solicitação criada com sucesso!');
    window.location.href = 'home.html';  
});
