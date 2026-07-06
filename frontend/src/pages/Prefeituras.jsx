import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import MainLayout from "../layouts/MainLayout";
import PageHeader from "../components/PageHeader";
import AppCard from "../components/AppCard";

import api from "../services/api";

function Prefeituras() {

    const navigate = useNavigate();

    const [prefeituras, setPrefeituras] = useState([]);
    const [pesquisa, setPesquisa] = useState("");
    const [loading, setLoading] = useState(false);

    useEffect(() => {

        carregarPrefeituras();

    }, []);

    async function carregarPrefeituras() {

        try {

            setLoading(true);

            const response = await api.get("/api/prefeituras");

            setPrefeituras(response.data);

        } catch (erro) {

            console.error(erro);

        } finally {

            setLoading(false);

        }

    }

    async function alterarStatus(prefeitura) {

        const texto = prefeitura.ativo
            ? "Inativar"
            : "Ativar";

        if (!window.confirm(`${texto} esta prefeitura?`))
            return;

        try {

            await api.put(
                `/api/prefeituras/${prefeitura.id}`,
                {
                    ...prefeitura,
                    ativo: !prefeitura.ativo
                }
            );

            carregarPrefeituras();

        } catch (erro) {

            alert("Erro ao alterar status");

        }

    }

    const lista = prefeituras.filter(p =>

        p.nome.toLowerCase().includes(
            pesquisa.toLowerCase()
        ) ||

        p.cnpj.includes(pesquisa)

    );

    return (

        <MainLayout>

            <PageHeader
    categoria="Administração"
    titulo="Prefeituras"
    subtitulo="Gerencie as prefeituras cadastradas."
    botaoTexto="Nova Prefeitura"
    onClick={() => navigate("/prefeituras/nova")}
/>
		
	<div className="row mb-4 dashboard-fade-in">
	
            <AppCard>

                <div className="row mb-3">

                    <div className="col-md-4">

                        <input
                            className="form-control"
                            placeholder="Pesquisar..."
                            value={pesquisa}
                            onChange={(e)=>
                                setPesquisa(
                                    e.target.value
                                )
                            }
                        />

                    </div>

                </div>

                <table className="table table-hover align-middle">

                    <thead>

                    <tr>

                        <th>ID</th>
                        <th>Nome</th>
                        <th>CNPJ</th>
                        <th>Cidade</th>
                        <th>UF</th>
                        <th>Status</th>
                        <th width="220">
                            Ações
                        </th>

                    </tr>

                    </thead>

                    <tbody>

                    {

                        loading ?

                        <tr>

                            <td colSpan="7"
                                className="text-center">

                                Carregando...

                            </td>

                        </tr>

                        :

                        lista.map(prefeitura=>(

                            <tr key={prefeitura.id}>

                                <td>{prefeitura.id}</td>

                                <td>{prefeitura.nome}</td>

                                <td>{prefeitura.cnpj}</td>

                                <td>{prefeitura.cidade}</td>

                                <td>{prefeitura.estado}</td>

                                <td>

                                    {

                                        prefeitura.ativo ?

                                        <span className="badge bg-success">

                                            Ativa

                                        </span>

                                        :

                                        <span className="badge bg-danger">

                                            Inativa

                                        </span>

                                    }

                                </td>

                                <td>

                                    <button

                                        className="btn btn-sm btn-gold me-2"

                                        onClick={()=>navigate(
                                            `/prefeituras/editar/${prefeitura.id}`
                                        )}

                                    >

                                        Editar

                                    </button>

                                    <button

                                        className={`btn btn-sm ${
                                            prefeitura.ativo
                                            ?
                                            "btn-danger"
                                            :
                                            "btn-success"
                                        }`}

                                        onClick={()=>alterarStatus(prefeitura)}

                                    >

                                        {

                                            prefeitura.ativo

                                            ?

                                            "Inativar"

                                            :

                                            "Ativar"

                                        }

                                    </button>

                                </td>

                            </tr>

                        ))

                    }

                    </tbody>

                </table>

            </AppCard>
</div>

        </MainLayout>

    );

}

export default Prefeituras;