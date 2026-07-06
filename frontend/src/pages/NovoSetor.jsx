import { useState } from "react";
import { useNavigate } from "react-router-dom";
import MainLayout from "../layouts/MainLayout";
import api from "../services/api";

function NovoSetor() {

    const navigate = useNavigate();

    const [setor, setSetor] = useState({

        nome: "",
        responsavel: "",
        telefone: "",
        ativo: true

    });

    function alterarCampo(e) {

        const { name, value, type, checked } =
            e.target;

        setSetor({

            ...setor,

            [name]:
                type === "checkbox"
                    ? checked
                    : value

        });
    }

    async function salvar(e) {

        e.preventDefault();

        try {

            await api.post(
                "/api/setores",
                setor
            );

            alert(
                "Setor cadastrado com sucesso!"
            );

            navigate("/setores");

        } catch (erro) {

            console.error(erro);

            alert(
                "Erro ao salvar setor"
            );

        }
    }

    return (

        <MainLayout>

            <h1>Novo Setor</h1>

            <form onSubmit={salvar}>

                <div className="mb-3">

                    <label>Nome</label>

                    <input
                        type="text"
                        name="nome"
                        className="form-control"
                        value={setor.nome}
                        onChange={alterarCampo}
                    />

                </div>

                <div className="mb-3">

                    <label>Responsável</label>

                    <input
                        type="text"
                        name="responsavel"
                        className="form-control"
                        value={setor.responsavel}
                        onChange={alterarCampo}
                    />

                </div>

                <div className="mb-3">

                    <label>Telefone</label>

                    <input
                        type="text"
                        name="telefone"
                        className="form-control"
                        value={setor.telefone}
                        onChange={alterarCampo}
                    />

                </div>

                <div className="form-check mb-3">

                    <input
                        type="checkbox"
                        name="ativo"
                        className="form-check-input"
                        checked={setor.ativo}
                        onChange={alterarCampo}
                    />

                    <label
                        className="form-check-label"
                    >
                        Setor Ativo
                    </label>

                </div>

                <button
                    type="submit"
                    className="btn btn-success"
                >
                    Salvar
                </button>

            </form>

        </MainLayout>

    );
}

export default NovoSetor;