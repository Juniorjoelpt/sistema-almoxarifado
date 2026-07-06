import { useEffect, useState } from "react";
import MainLayout from "../layouts/MainLayout";
import api from "../services/api";
import { FaArrowDown } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom";
import PageHeader from "../components/PageHeader";
import ProdutoInfoCard from "../components/ProdutoInfoCard";
import ProductHeaderCard from
"../components/movimentacao/ProductHeaderCard";
import EstoqueResumo from
"../components/movimentacao/EstoqueResumo";


function NovaMovimentacao() {

    const navigate = useNavigate();

    const [produtos, setProdutos] = useState([]);

    const [setores, setSetores] = useState([]);

    const [fornecedores, setFornecedores] = useState([]);

    const [produtoSelecionado, setProdutoSelecionado] = useState(null);

  const [movimentacao, setMovimentacao] = useState({
    produtoId: "",
    fornecedorId: "",
    quantidade: 0,
    numeroLote: "",
    dataValidade: "",
    observacao: ""
});

    useEffect(() => {

        carregarProdutos();
	 carregarFornecedores();

    }, []);

    async function carregarProdutos() {

        try {

            const response =
                await api.get("/api/produtos");

            setProdutos(response.data);

        } catch (erro) {

            console.error(erro);

        }
    }
function alterarCampo(e) {

    const { name, value } = e.target;

    setMovimentacao({
        ...movimentacao,
        [name]:
            name === "quantidade"
                ? Number(value)
                : value
    });

    if (name === "produtoId") {

        const produto = produtos.find(
    	p => p.id === Number(e.target.value)
	);

	setProdutoSelecionado(produto);
    }
}
async function salvar(e) {

    e.preventDefault();

    try {

        await api.post(
    	"/api/movimentacoes/entrada",
    	movimentacao
	);

        alert("Movimentação registrada com sucesso!");

        navigate("/movimentacoes");

    } catch (erro) {

        console.error(erro);

        alert(
            erro.response?.data?.message ||
            "Erro ao registrar movimentação"
        );
    }
}
	async function carregarSetores() {

    try {

        const response =
            await api.get("/api/setores");

        setSetores(response.data);

    } catch (erro) {

        console.error(erro);

    }
}
	async function carregarFornecedores() {

    try {

        const response =
            await api.get("/api/fornecedores");

        console.log(response.data);

        setFornecedores(response.data);

    } catch (erro) {

        console.error(erro);

    }
}

const exigeValidade =
    produtoSelecionado &&
    produtoSelecionado?.categoria?.nome !==
    "Material Permanente";

const quantidadeConvertida =
    produtoSelecionado
        ? (movimentacao.quantidade || 0)
            * (produtoSelecionado.fatorConversao || 1)
        : 0;

    return (

    <MainLayout>

        <PageHeader
    categoria="Movimentação"
    titulo="Entrada de Estoque"
    subtitulo="Registre entradas de materiais."
    icone={<FaArrowDown color="#198754" size={24} />}
/>

        <form onSubmit={salvar}>

            <div className="mb-3 dashboard-fade-in">

                <label>Produto</label>

               	 <select
   			 name="produtoId"
    			className="form-select"
    			value={movimentacao.produtoId}
   			 onChange={alterarCampo}
			>

                    <option value="">
                        Selecione um produto
                    </option>

                   {produtos.map(produto => (

    <option
        key={produto.id}
        value={produto.id}
    >
        {produto.nome}
    </option>

))}
		
                </select>

		
		<ProductHeaderCard
    produto={produtoSelecionado}
/>
		<EstoqueResumo
    produto={produtoSelecionado}
/>

            </div>
	

		<div className="mb-3 dashboard-fade-in">

    <label>Fornecedor</label>

    <select
    name="fornecedorId"
    className="form-select"
    value={movimentacao.fornecedorId}
    onChange={alterarCampo}
>
    <option value="">
        Selecione um fornecedor
    </option>

    {fornecedores.map((fornecedor) => (
        <option
            key={fornecedor.id}
            value={fornecedor.id}
        >
            {fornecedor.razaoSocial}
        </option>
    ))}
</select>

</div>
			{exigeValidade && (
<>
    <div className="mb-3 ">

        <label>Número do Lote</label>

        <input
            type="text"
            name="numeroLote"
            className="form-control"
            value={movimentacao.numeroLote}
            onChange={alterarCampo}
        />

    </div>

    <div className="mb-3">

        <label>Data de Validade</label>

        <input
            type="date"
            name="dataValidade"
            className="form-control"
            value={movimentacao.dataValidade}
            onChange={alterarCampo}
        />

    </div>
</>
)}

	   
          
            <div className="mb-3 dashboard-fade-in">

                <label>Quantidade</label>

                <input
                    type="number"
                    name="quantidade"
                    className="form-control"
                    value={movimentacao.quantidade}
                    onChange={alterarCampo}
                />
		<small className="text-muted">

		1 {produtoSelecionado?.unidadeEmbalagem} =
		{produtoSelecionado?.fatorConversao}
		{produtoSelecionado?.unidadeEstoque}

		</small>

            </div>
		{
			produtoSelecionado && (

			<div className="alert alert-info mt-2">

			<strong>

			Conversão automática

			</strong>

			<br/>

			{movimentacao.quantidade || 0}

				{" "}

			{produtoSelecionado.unidadeEmbalagem}

				{" = "}

			<strong>
	
			{quantidadeConvertida}

				{" "}

			{produtoSelecionado.unidadeEstoque}

			</strong>

			</div>

				)
			}

            <div className="mb-3 dashboard-fade-in">

                <label>Observação</label>

                <textarea
                    name="observacao"
                    className="form-control"
                    rows="3"
                    value={movimentacao.observacao}
                    onChange={alterarCampo}
                />

            </div>

            <button
                type="submit"
                className="btn btn-gold"
            >
                Registrar Entrada
            </button>

        </form>

    </MainLayout>

);
}

export default NovaMovimentacao;