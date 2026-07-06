import { useEffect, useState } from "react";
import MainLayout from "../layouts/MainLayout";
import api from "../services/api";
import PageHeader from "../components/PageHeader";
import AppCard from "../components/AppCard";
import { Link, useNavigate } from "react-router-dom";
import { FaBoxes } from "react-icons/fa";
import StatCard from "../components/StatCard";

function Produtos() {

    // ESTADO
    const [produtos, setProdutos] = useState([]);
    const [busca, setBusca] = useState("");
    const navigate = useNavigate();

    // EXECUTA AO ABRIR A PÁGINA
    useEffect(() => { carregarProdutos(); }, []);

    // FUNÇÃO QUE BUSCA NO BACKEND
   async function carregarProdutos() {

    try {

        console.log("TOKEN:");
        console.log(localStorage.getItem("token"));

        const response =
            await api.get("/api/produtos");

        console.log("STATUS:");
        console.log(response.status);

        console.log("DADOS:");
        console.log(response.data);

        setProdutos(response.data);

    } catch (erro) {

        console.log("ERRO:");
        console.log(erro);

        console.log("STATUS:");
        console.log(erro.response?.status);

        console.log("BODY:");
        console.log(erro.response?.data);
    }
}
	async function pesquisarProdutos(nome) {

    try {

        if (!nome.trim()) {

            carregarProdutos();
            return;
        }

        const response =
            await api.get(
                `/api/produtos/filtro?nome=${nome}`
            );

        setProdutos(response.data);

    } catch (erro) {

        console.error(
            "Erro ao pesquisar produtos",
            erro
        );
    }
}
async function excluirProduto(id) {

    const confirmar = window.confirm(
        "Deseja realmente excluir este produto?"
    );

    if (!confirmar) {
        return;
    }

    try {

        await api.delete(
            `/api/produtos/${id}`
        );

        alert("Produto excluído com sucesso!");

        carregarProdutos();

    } catch (erro) {

        console.error(erro);

        alert("Erro ao excluir produto");
    }
}
const totalProdutos = produtos.length;

const estoqueBaixo = produtos.filter(

    p => p.estoqueAtual <= p.estoqueMinimo

).length;

const produtosVencendo = produtos.filter(p => {

    if (!p.dataValidade) return false;

    const hoje = new Date();

    const limite = new Date();

    limite.setDate(limite.getDate() + 30);

    const validade = new Date(p.dataValidade);

    return validade >= hoje && validade <= limite;

}).length;

const totalCategorias = new Set(

    produtos.map(p => p.categoria?.id)

).size;

const ultimosProdutos =

    [...produtos]

        .sort((a, b) => b.id - a.id)

        .slice(0, 5);

    return (

    <MainLayout>

        <PageHeader
    categoria="Cadastros"
    titulo="Produtos"
    subtitulo="Gerenciamento de estoque"
    icone={
        <FaBoxes
            style={{
                color: "#c48a2a",
                fontSize: "26px"
            }}
        />
    }
    botaoTexto="Novo Produto"
    onClick={() =>
        navigate("/produtos/novo")
    }
/>

        <div className="row mb-4 dashboard-fade-in">

            <div className="table align-middle">

                <AppCard>

                    <input
                        type="text"
                        className="form-control"
                        placeholder="Pesquisar produto..."
                        value={busca}
                        onChange={(e) => {

                            setBusca(e.target.value);

                            pesquisarProdutos(
                                e.target.value
                            );

                        }}
                    />

                </AppCard>

            </div>

        </div>

	
	
	<div className="row mb-4 dashboard-fade-in">

    <div className="col-md-3">
        <StatCard
            titulo="Produtos"
            valor={totalProdutos}
        />
    </div>

    <div className="col-md-3">
        <StatCard
            titulo="Estoque Baixo"
            valor={estoqueBaixo}
            cor="danger"
        />
    </div>

    <div className="col-md-3">
        <StatCard
            titulo="Vencendo"
            valor={produtosVencendo}
            cor="warning"
        />
    </div>

    <div className="col-md-3 ">
        <StatCard
            titulo="Categorias"
            valor={totalCategorias}
            cor="primary"
        />
    </div>

</div>
	<div className="row mb-4 dashboard-fade-in">

    <div className="table align-middle">
        <AppCard>
            <h6 className="mb-3">
                Últimos produtos cadastrados
            </h6>

            <div className="timeline-products">

    {ultimosProdutos.map((produto, index) => (
        <div key={produto.id} className="timeline-item">

            <div className="timeline-dot" />

            <div className="timeline-card">
                <div className="timeline-title">
                    {produto.nome}
                </div>

                <div className="timeline-sub">
                    Código: {produto.codigo} • ID: {produto.id}
                </div>
            </div>

        </div>
    ))}

</div>
        </AppCard>
    </div>

</div>

           <div className="mt-4 dashboard-fade-in">

	<AppCard>

		<table className="table align-middle">

   <thead className="table-light">

    <tr>

        <th>ID</th>
        <th>Código</th>
        <th>Nome</th>
        <th>Categoria</th>
	<th>Validade</th>
        <th>Unidade</th>
        <th>Estoque Atual</th>
        <th>Estoque Mínimo</th>
	<th>Status</th>
	

    </tr>

</thead>

    <tbody>

    {produtos.map(produto => (

        <tr
    key={produto.id}
    className={
        produto.estoqueAtual <= produto.estoqueMinimo
            ? "table-danger"
            : ""
    }
>

            <td>{produto.id}</td>

            <td>{produto.codigo}</td>

            <td>{produto.nome}</td>

            <td>{produto.categoria?.nome}</td>
	    
	   

	    <td>

    {produto.dataValidade ? (

        new Date(produto.dataValidade) <=
        new Date(
            Date.now() + 30 * 24 * 60 * 60 * 1000
        ) ? (

            <span className="badge bg-warning text-dark">
                {new Date(
                    produto.dataValidade
                ).toLocaleDateString("pt-BR")}
            </span>

        ) : (

            new Date(
                produto.dataValidade
            ).toLocaleDateString("pt-BR")

        )

    ) : "-"}

</td>

            <td>{produto.unidade}</td>

            <td>

    {produto.estoqueAtual <= produto.estoqueMinimo ? (

        <span className="badge bg-danger">
            {produto.estoqueAtual}
        </span>

    ) : (

        <span className="badge bg-success">
            {produto.estoqueAtual}
        </span>

    )}

</td>

            <td>{produto.estoqueMinimo}</td>

            <td>

                <Link
    			to={`/produtos/editar/${produto.id}`}
    			className="btn btn-gold btn-sm me-2"
			>
   			 Editar
		</Link>

                	<button
   			className="btn btn-gold btn-sm me-2"
   			 onClick={() =>
       			 excluirProduto(produto.id)
   			 }
			>
    				Excluir
			</button>

            </td>

        </tr>

    ))}

</tbody>

</table>

</AppCard>
</div>

        </MainLayout>

    );
}

export default Produtos;