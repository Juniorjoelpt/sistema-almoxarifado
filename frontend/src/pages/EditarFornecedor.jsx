import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import MainLayout from "../layouts/MainLayout";
import api from "../services/api";

function EditarFornecedor() {

const { id } = useParams();

const navigate = useNavigate();

const [fornecedor, setFornecedor] = useState({

    razaoSocial: "",
    nomeFantasia: "",
    cnpj: "",
    telefone: "",
    email: "",
    endereco: "",
    ativo: true

});

useEffect(() => {

    carregarFornecedor();

}, []);

async function carregarFornecedor() {

    try {

        const response =
            await api.get(
                `/api/fornecedores/${id}`
            );

        setFornecedor(response.data);

    } catch (erro) {

        console.error(erro);

        alert(
            "Erro ao carregar fornecedor"
        );

    }
}

function alterarCampo(e) {

    const { name, value, type, checked } =
        e.target;

    setFornecedor({

        ...fornecedor,

        [name]:
            type === "checkbox"
                ? checked
                : value

    });
}

async function salvar(e) {

    e.preventDefault();

    try {

        await api.put(
            `/api/fornecedores/${id}`,
            fornecedor
        );

        alert(
            "Fornecedor atualizado com sucesso!"
        );

        navigate("/fornecedores");

    } catch (erro) {

        console.error(erro);

        alert(
            "Erro ao atualizar fornecedor"
        );

    }
}

return (

    <MainLayout>

        <h1>Editar Fornecedor</h1>

        <form onSubmit={salvar}>

            <div className="mb-3">

                <label>Razão Social</label>

                <input
                    type="text"
                    name="razaoSocial"
                    className="form-control"
                    value={fornecedor.razaoSocial}
                    onChange={alterarCampo}
                />

            </div>

            <div className="mb-3">

                <label>Nome Fantasia</label>

                <input
                    type="text"
                    name="nomeFantasia"
                    className="form-control"
                    value={fornecedor.nomeFantasia}
                    onChange={alterarCampo}
                />

            </div>

            <div className="mb-3">

                <label>CNPJ</label>

                <input
                    type="text"
                    name="cnpj"
                    className="form-control"
                    value={fornecedor.cnpj}
                    onChange={alterarCampo}
                />

            </div>

            <div className="mb-3">

                <label>Telefone</label>

                <input
                    type="text"
                    name="telefone"
                    className="form-control"
                    value={fornecedor.telefone}
                    onChange={alterarCampo}
                />

            </div>

            <div className="mb-3">

                <label>Email</label>

                <input
                    type="email"
                    name="email"
                    className="form-control"
                    value={fornecedor.email}
                    onChange={alterarCampo}
                />

            </div>

            <div className="mb-3">

                <label>Endereço</label>

                <textarea
                    name="endereco"
                    className="form-control"
                    rows="3"
                    value={fornecedor.endereco}
                    onChange={alterarCampo}
                />

            </div>

            <div className="form-check mb-3">

                <input
                    type="checkbox"
                    name="ativo"
                    className="form-check-input"
                    checked={fornecedor.ativo}
                    onChange={alterarCampo}
                />

                <label
                    className="form-check-label"
                >
                    Fornecedor Ativo
                </label>

            </div>

            <button
                type="submit"
                className="btn btn-success"
            >
                Salvar Alterações
            </button>

        </form>

    </MainLayout>

);


}

export default EditarFornecedor;
