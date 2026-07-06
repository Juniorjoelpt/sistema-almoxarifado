import { useEffect, useState } from "react";
import MainLayout from "../layouts/MainLayout";
import api from "../services/api";
import AppCard from "../components/AppCard";
import { FaTruck } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom";
import PageHeader from "../components/PageHeader";

function Fornecedores() {

  const navigate = useNavigate();
const [fornecedores, setFornecedores] = useState([]);
const [busca, setBusca] = useState("");

useEffect(() => {
    carregarFornecedores();
}, []);

async function carregarFornecedores() {

    try {

        const response =
            await api.get("/api/fornecedores");

        setFornecedores(response.data);

    } catch (erro) {

        console.error(erro);

    }
}

async function pesquisar(nome) {

    try {

        if (!nome.trim()) {

            carregarFornecedores();
            return;
        }

        const response =
            await api.get(
                `/api/fornecedores/filtro/razao-social?nome=${nome}`
            );

        setFornecedores(response.data);

    } catch (erro) {

        console.error(erro);

    }
}

async function excluirFornecedor(id) {

    const confirmar =
        window.confirm(
            "Deseja excluir este fornecedor?"
        );

    if (!confirmar) return;

    try {

        await api.delete(
            `/api/fornecedores/${id}`
        );

        alert("Fornecedor excluído!");

        carregarFornecedores();

    } catch (erro) {

        console.error(erro);

        alert("Erro ao excluir");

    }
}

return (

    <MainLayout>

        
            <PageHeader
    categoria="Cadastros"
    titulo="Fornecedores"
    subtitulo="Gerencie os fornecedores do almoxarifado."
    botaoTexto="Novo Fornecedor"
    onClick={() => navigate("/fornecedores/novo")}
/>

         

        
	
	<div className="mb-4">

<AppCard>

<input
  type="text"
  className="form-control"
  placeholder="Pesquisar fornecedor..."
  value={busca}
  onChange={(e) => {
      setBusca(e.target.value);
      pesquisar(e.target.value);
  }}
/>

</AppCard>

</div>

	

       <div className="mt-3 dashboard-fade-in">

<AppCard>

<table className="table align-middle mb-0">

            <thead>

                <tr>

                    <th>ID</th>
                    <th>Razão Social</th>
                    <th>Nome Fantasia</th>
                    <th>CNPJ</th>
                    <th>Telefone</th>
                    <th>Email</th>
                    <th>Status</th>
                    <th>Ações</th>

                </tr>

            </thead>

            <tbody>

                {fornecedores.map(fornecedor => (

                    <tr key={fornecedor.id}>

                        <td>{fornecedor.id}</td>

                        <td>{fornecedor.razaoSocial}</td>

                        <td>{fornecedor.nomeFantasia}</td>

                        <td>{fornecedor.cnpj}</td>

                        <td>{fornecedor.telefone}</td>

                        <td>{fornecedor.email}</td>

                        <td>

                            {fornecedor.ativo ? (

                                <span className="badge bg-success">
                                    Ativo
                                </span>

                            ) : (

                                <span className="badge bg-danger">
                                    Inativo
                                </span>

                            )}

                        </td>

                        <td>

                            <Link
                                to={`/fornecedores/editar/${fornecedor.id}`}
                                className="btn btn-warning btn-sm me-2"
                            >
                                Editar
                            </Link>

                            <button
                                className="btn btn-danger btn-sm"
                                onClick={() =>
                                    excluirFornecedor(
                                        fornecedor.id
                                    )
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

export default Fornecedores;
