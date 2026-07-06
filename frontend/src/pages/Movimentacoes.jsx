import { useEffect, useState } from "react";
import MainLayout from "../layouts/MainLayout";
import api from "../services/api";
import AppCard from "../components/AppCard";
import { FaExchangeAlt } from "react-icons/fa";
import PageHeader from "../components/PageHeader";
import { Link, useNavigate } from "react-router-dom";


function Movimentacoes() {

    const [movimentacoes, setMovimentacoes] = useState([]);
    const [tipoFiltro, setTipoFiltro] = useState("");


    useEffect(() => {
        carregarMovimentacoes();
    }, []);

    async function carregarMovimentacoes() {

        try {

            const response =
                await api.get("/api/movimentacoes");

            setMovimentacoes(response.data);

        } catch (erro) {

            console.error(erro);

        }
    }
async function filtrarTipo(tipo) {

    try {

        if (!tipo) {

            carregarMovimentacoes();
            return;
        }

        const response =
            await api.get(
                `/api/movimentacoes/filtro/tipo?tipo=${tipo}`
            );

        setMovimentacoes(response.data);

    } catch (erro) {

        console.error(erro);
    }
}

    return (

    <MainLayout>

        <PageHeader
    titulo="Movimentações"
    subtitulo="Histórico de entradas e saídas"
    icone={<FaExchangeAlt color="#c48a2a" size={24} />}
/>

	<div className="row mb-4 dashboard-fade-in">


        <AppCard>

<select
    className="form-select"
    value={tipoFiltro}
    onChange={(e) => {

        setTipoFiltro(e.target.value);

        filtrarTipo(e.target.value);

    }}
>

            <option value="">
                Todas
            </option>

            <option value="ENTRADA">
                Entradas
            </option>

            <option value="SAIDA">
                Saídas
            </option>

        </select>
</AppCard>
</div>

	<div className="row mb-4 dashboard-fade-in">

        <AppCard>

	<table className="table align-middle">
            <thead>

                <tr>
    		<th>ID</th>
    		<th>Produto</th>
    		<th>Tipo</th>
    		<th>Fornecedor</th>
    		<th>Setor</th>
    		<th>Quantidade</th>
    		<th>Data</th>
    		<th>Observação</th>
		</tr>

            </thead>

            <tbody>

                {movimentacoes.map(mov => (

                    <tr key={mov.id}>

    <td>{mov.id}</td>

    <td>{mov.produto?.nome}</td>

    <td>
        {mov.tipo === "ENTRADA" ? (
            <span className="badge bg-success">
                Entrada
            </span>
        ) : (
            <span className="badge bg-danger">
                Saída
            </span>
        )}
    </td>

    <td>
        {mov.fornecedor?.razaoSocial || "-"}
    </td>

    <td>
        {mov.setor?.nome || "-"}
    </td>

    <td>{mov.quantidade}</td>

    <td>
        {new Date(
            mov.dataMovimentacao
        ).toLocaleString()}
    </td>

    <td>{mov.observacao || "-"}</td>

</tr>

                ))}

            </tbody>

        </table>
	</AppCard>
</div>

    </MainLayout>

);
}

export default Movimentacoes;