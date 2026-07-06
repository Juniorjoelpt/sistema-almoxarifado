import { useEffect, useState } from "react";
import MainLayout from "../layouts/MainLayout";
import api from "../services/api";
import { FaClipboardCheck } from "react-icons/fa";
import PageHeader from "../components/PageHeader";
import { Link, useNavigate } from "react-router-dom";

function Auditoria() {

    const [auditorias, setAuditorias] = useState([]);

    const [usuarioFiltro, setUsuarioFiltro] =
        useState("");

    useEffect(() => {

        carregarAuditoria();

    }, []);

    async function carregarAuditoria() {

        try {

            const response =
                await api.get("/api/auditoria");

            setAuditorias(response.data);

        } catch (erro) {

            console.error(erro);

        }
    }

    async function filtrarUsuario(usuario) {

        try {

            if (!usuario) {

                carregarAuditoria();

                return;
            }

            const response =
                await api.get(
                    `/api/auditoria/usuario/${usuario}`
                );

            setAuditorias(response.data);

        } catch (erro) {

            console.error(erro);

        }
    }

    async function exportarPdf() {

    try {

        const response = await api.get(
            "/api/auditoria/pdf",
            {
                responseType: "blob"
            }
        );

        const url = window.URL.createObjectURL(
            new Blob([response.data])
        );

        const link = document.createElement("a");

        link.href = url;

        link.setAttribute(
            "download",
            "auditoria.pdf"
        );

        document.body.appendChild(link);

        link.click();

        link.remove();

    } catch (erro) {

        console.error(erro);

    }

}

    async function exportarExcel() {

    try {

        const response = await api.get(
            "/api/auditoria/excel",
            {
                responseType: "blob"
            }
        );

        const url = window.URL.createObjectURL(
            new Blob([response.data])
        );

        const link = document.createElement("a");

        link.href = url;

        link.setAttribute(
            "download",
            "auditoria.xlsx"
        );

        document.body.appendChild(link);

        link.click();

        link.remove();

    } catch (erro) {

        console.error(erro);

    }

}

    return (

        <MainLayout>

            <div className="d-flex justify-content-between mb-3 ">

                <PageHeader
    titulo="Auditoria"
    subtitulo="Logs do sistema"
    icone={<FaClipboardCheck color="#c48a2a" size={24} />}
/>

                <div>

                    <button
                        className="btn btn-danger me-2"
                        onClick={exportarPdf}
                    >
                        PDF
                    </button>

                    <button
                        className="btn btn-success"
                        onClick={exportarExcel}
                    >
                        Excel
                    </button>

                </div>

            </div>

            <div className="mb-3 dashboard-fade-in">

                <input
                    type="text"
                    className="form-control"
                    placeholder="Filtrar por usuário"
                    value={usuarioFiltro}
                    onChange={(e) => {

                        setUsuarioFiltro(
                            e.target.value
                        );

                        filtrarUsuario(
                            e.target.value
                        );

                    }}
                />

            </div>

            <table className="table table-striped dashboard-fade-in">

                <thead>

                    <tr>

                        <th>ID</th>
                        <th>Usuário</th>
                        <th>Produto</th>
                        <th>Tipo</th>
                        <th>Quantidade</th>
                        <th>Data</th>
                        <th>Observação</th>

                    </tr>

                </thead>

                <tbody>

                    {auditorias.map(a => (

                        <tr key={a.id}>

                            <td>{a.id}</td>

                            <td>{a.usuario}</td>

                            <td>{a.produto}</td>

                            <td>

                                {a.tipo === "ENTRADA" ? (

                                    <span className="badge bg-success">
                                        Entrada
                                    </span>

                                ) : (

                                    <span className="badge bg-danger">
                                        Saída
                                    </span>

                                )}

                            </td>

                            <td>{a.quantidade}</td>

                            <td>
                                {
                                    new Date(
                                        a.dataHora
                                    ).toLocaleString()
                                }
                            </td>

                            <td>
                                {a.observacao}
                            </td>

                        </tr>

                    ))}

                </tbody>

            </table>

        </MainLayout>

    );
}

export default Auditoria;