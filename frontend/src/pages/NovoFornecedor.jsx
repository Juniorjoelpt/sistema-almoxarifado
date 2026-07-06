import { useState } from "react";
import { useNavigate } from "react-router-dom";
import MainLayout from "../layouts/MainLayout";
import api from "../services/api";

function NovoFornecedor() {


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

        await api.post(
            "/api/fornecedores",
            fornecedor
        );

        alert(
            "Fornecedor cadastrado com sucesso!"
        );

        navigate("/fornecedores");

    } catch (erro) {

    console.error("ERRO COMPLETO:");
    console.error(erro);

    console.log("STATUS:");
    console.log(erro.response?.status);

    console.log("BODY:");
    console.log(erro.response?.data);

    alert("Erro ao cadastrar fornecedor");
}
}

return (

    <MainLayout>

        <h1>Novo Fornecedor</h1>

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
                Salvar
            </button>

        </form>

    </MainLayout>

);
}

export default NovoFornecedor;
