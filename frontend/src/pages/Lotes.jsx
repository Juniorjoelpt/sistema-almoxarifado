import { useEffect, useState } from "react";
import MainLayout from "../layouts/MainLayout";
import api from "../services/api";
import { Link } from "react-router-dom";

function Lotes() {

    const [lotes, setLotes] = useState([]);

    useEffect(() => {

        carregarLotes();

    }, []);

    async function carregarLotes() {

        try {

            const response =
                await api.get("/api/lotes");

            setLotes(response.data);

        } catch (erro) {

            console.error(erro);

        }
    }

    function calcularDias(dataValidade) {

        const hoje = new Date();

        const validade =
            new Date(dataValidade);

        const diff =
            validade - hoje;

        return Math.ceil(
            diff / (1000 * 60 * 60 * 24)
        );
    }

    return (

        <MainLayout>

            <h1>Controle de Lotes</h1>

            <table className="table table-striped">

                <thead>

                    <tr>

                        <th>Produto</th>
                        <th>Lote</th>
			<th>Ações</th>
                        <th>Quantidade</th>
                        <th>Validade</th>
                        <th>Dias</th>
                        <th>Status</th>

                    </tr>

                </thead>

                <tbody>

                    {lotes.map(lote => {

                        const dias =
                            calcularDias(
                                lote.dataValidade
                            );

                        return (

                            <tr key={lote.id}>

                                <td>
                                    {lote.produto?.nome}
                                </td>

                                <td>
                                    {lote.numeroLote}
                                </td>

                                <td>
                                    {lote.quantidade}
                                </td>

                                <td>
                                    {new Date(
                                        lote.dataValidade
                                    ).toLocaleDateString()}
                                </td>
				<td>
    					<Link
       					 to={`/lotes/produto/${lote.produto.id}`}
        				className="btn btn-sm btn-primary"
   					 >
       					 Ver Lotes
    					</Link>
				</td>

                                <td>
                                    {dias}
                                </td>

                                <td>

                                    {dias < 0 ? (

                                        <span className="badge bg-danger">
                                            Vencido
                                        </span>

                                    ) : dias <= 30 ? (

                                        <span className="badge bg-warning text-dark">
                                            Próximo
                                        </span>

                                    ) : (

                                        <span className="badge bg-success">
                                            OK
                                        </span>

                                    )}

                                </td>

                            </tr>

                        );

                    })}

                </tbody>

            </table>

        </MainLayout>

    );
}

export default Lotes;