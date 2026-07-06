import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import MainLayout from "../layouts/MainLayout";
import api from "../services/api";

function LotesProduto() {

    const { id } = useParams();

    const [lotes, setLotes] = useState([]);

    useEffect(() => {

        carregarLotes();

    }, []);

    async function carregarLotes() {

        try {

            const response =
                await api.get(
                    `/api/lotes/produto/${id}`
                );

            setLotes(response.data);

        } catch (erro) {

            console.error(erro);

        }
    }

    return (

        <MainLayout>

            <h1>Lotes do Produto</h1>

            <table className="table table-striped">

                <thead>

                    <tr>

                        <th>Lote</th>
                        <th>Quantidade</th>
                        <th>Validade</th>

                    </tr>

                </thead>

                <tbody>

                    {lotes.map(lote => (

                        <tr key={lote.id}>

                            <td>
                                {lote.numeroLote}
                            </td>

                            <td>
                                {lote.quantidade}
                            </td>

                            <td>
                                {
                                    new Date(
                                        lote.dataValidade
                                    ).toLocaleDateString()
                                }
                            </td>

                        </tr>

                    ))}

                </tbody>

            </table>

        </MainLayout>

    );
}

export default LotesProduto;