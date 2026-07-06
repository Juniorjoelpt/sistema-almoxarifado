import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
    FaEdit,
    FaPowerOff
} from "react-icons/fa";
import {
    listarUsuarios,
    alterarStatus
} from "../services/usuarioService";
import MainLayout from "../layouts/MainLayout";
import PageHeader from "../components/PageHeader";
import AppCard from "../components/AppCard";

import api from "../services/api";

function Usuarios() {

    const navigate = useNavigate();

    const [usuarios, setUsuarios] = useState([]);

    useEffect(() => {
        carregarUsuarios();
    }, []);

    async function carregarUsuarios() {

        try {

            const response =
                await api.get("/api/usuarios");

            setUsuarios(response.data);

        } catch (erro) {

            console.error(erro);

        }

    }
	async function alterarStatus(usuario){

    try{

        await api.patch(

            `/api/usuarios/${usuario.id}/ativo?ativo=${!usuario.ativo}`

        );

        carregarUsuarios();

    }

    catch(err){

        console.error(err);

    }

}

    return (

        <MainLayout>

            <PageHeader
                titulo="Usuários"
                subtitulo="Gerenciamento de usuários"
                botaoTexto="Novo Usuário"
                onClick={() =>
                    navigate("/usuarios/novo")
                }
            />

	<div className="row mb-4 dashboard-fade-in">

            <AppCard>

                <table className="table table-hover">

                    <thead>

                        <tr>

                            <th>Nome</th>
                            <th>Username</th>
                            <th>Perfil</th>
                            <th>Prefeitura</th>
                            <th>Status</th>
                            <th>Ações</th>

                        </tr>

                    </thead>

                    <tbody>

                        {usuarios.map(usuario => (

                            <tr key={usuario.id}>

                                <td>{usuario.nome}</td>

                                <td>{usuario.username}</td>

                                <td>

{usuario.role==="ROLE_SUPER_ADMIN" &&

<span className="badge bg-danger">

Super Administrador

</span>

}

{usuario.role==="ROLE_ADMIN_PREFEITURA" &&

<span className="badge bg-warning text-dark">

Administrador

</span>

}

{usuario.role==="ROLE_USUARIO" &&

<span className="badge bg-primary">

Usuário

</span>

}

</td>

                                <td>

                                    {usuario.prefeitura?.nome || "-"}

                                </td>

                                <td>

                                    {usuario.ativo ? (

                                        <span className="badge bg-success">

						Ativo

					</span>

                                    ) : (

                                       <span className="badge bg-secondary">

					Inativo

					</span>

                                    )}

                                </td>

                                <td>

    <button

        className="btn btn-gold btn-sm me-2"

        onClick={() =>

            navigate(`/usuarios/editar/${usuario.id}`)

        }

    >

        <FaEdit />

    </button>

    <button

        className={
            usuario.ativo
                ? "btn btn-danger btn-sm"
                : "btn btn-success btn-sm"
        }

        onClick={() => alterarStatus(usuario)}

    >

        <FaPowerOff />

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

export default Usuarios;