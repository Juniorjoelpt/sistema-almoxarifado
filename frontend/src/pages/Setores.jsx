import { useEffect, useState } from "react";
import MainLayout from "../layouts/MainLayout";
import api from "../services/api";
import { FaBuilding } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom";
import PageHeader from "../components/PageHeader";


function Setores() {
    const navigate = useNavigate();
    const [setores, setSetores] = useState([]);
    const [busca, setBusca] = useState("");

    useEffect(() => {

        carregarSetores();

    }, []);

    async function carregarSetores() {

        try {

            const response =
                await api.get("/api/setores");

            setSetores(response.data);

        } catch (erro) {

            console.error(erro);

        }
    }

    async function pesquisar(nome) {

        try {

            if (!nome.trim()) {

                carregarSetores();
                return;
            }

            const response =
                await api.get(
                    `/api/setores/filtro/nome?nome=${nome}`
                );

            setSetores(response.data);

        } catch (erro) {

            console.error(erro);

        }
    }

    async function excluirSetor(id) {

        const confirmar =
            window.confirm(
                "Deseja excluir este setor?"
            );

        if (!confirmar) return;

        try {

            await api.delete(
                `/api/setores/${id}`
            );

            alert("Setor excluído!");

            carregarSetores();

        } catch (erro) {

            console.error(erro);

            alert("Erro ao excluir");

        }
    }

    return (

        <MainLayout>

            <PageHeader
    categoria="Cadastros"
    titulo="Setores"
    subtitulo="Gerencie os setores da prefeitura."
    icone={<FaBuilding color="#c48a2a" size={24} />}
    botaoTexto="Novo Setor"
    onClick={() =>
        navigate("/setores/novo")
    }
/>

            <input
                type="text"
                className="form-control mb-3 dashboard-fade-in"
                placeholder="Pesquisar setor..."
                value={busca}
                onChange={(e) => {

                    setBusca(e.target.value);

                    pesquisar(e.target.value);

                }}
            />

            <table className="table table-striped dashboard-fade-in">

                <thead>

                    <tr>

                        <th>ID</th>
                        <th>Nome</th>
                        <th>Responsável</th>
                        <th>Telefone</th>
                        <th>Status</th>
                        <th>Ações</th>

                    </tr>

                </thead>

                <tbody>

                    {setores.map(setor => (

                        <tr key={setor.id}>

                            <td>{setor.id}</td>

                            <td>{setor.nome}</td>

                            <td>{setor.responsavel}</td>

                            <td>{setor.telefone}</td>

                            <td>

                                {setor.ativo ? (

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
                                    to={`/setores/editar/${setor.id}`}
                                    className="btn btn-warning btn-sm me-2"
                                >
                                    Editar
                                </Link>

                                <button
                                    className="btn btn-danger btn-sm"
                                    onClick={() =>
                                        excluirSetor(setor.id)
                                    }
                                >
                                    Excluir
                                </button>

                            </td>

                        </tr>

                    ))}

                </tbody>

            </table>

        </MainLayout>

    );
}

export default Setores;