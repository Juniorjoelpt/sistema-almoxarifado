import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import MainLayout from "../layouts/MainLayout";
import PageHeader from "../components/PageHeader";
import AppCard from "../components/AppCard";

import api from "../services/api";

function UsuarioForm() {

    const navigate = useNavigate();
    const { id } = useParams();

    const [usuario, setUsuario] = useState({

        nome: "",

        username: "",

        senha: "",

        role: "ROLE_USUARIO",

        prefeituraId: ""

    });

    const [prefeituras, setPrefeituras] = useState([]);

    useEffect(() => {

        carregarPrefeituras();

        if (id) {

            carregarUsuario();

        }

    }, []);

    async function carregarPrefeituras() {

        try {

            const response =
                await api.get("/api/prefeituras");

            setPrefeituras(response.data);

        } catch (erro) {

            console.error(erro);

        }

    }

    async function carregarUsuario() {

        try {

            const response =
                await api.get(`/api/usuarios/${id}`);

            setUsuario({

                nome: response.data.nome,

                username: response.data.username,

                senha: "",

                role: response.data.role,

                prefeituraId:
                    response.data.prefeitura?.id || ""

            });

        } catch (erro) {

            console.error(erro);

        }

    }

    async function salvar(e) {

        e.preventDefault();

        try {

            if (id) {

                await api.put(

                    `/api/usuarios/${id}`,

                    usuario

                );

            } else {
		console.log(usuario);
                await api.post(

                    "/api/usuarios",

                    usuario

                );

            }

            alert("Usuário salvo com sucesso!");

            navigate("/usuarios");

        }

      catch (erro) {

    console.log("STATUS:");
    console.log(erro.response?.status);

    console.log("BODY:");
    console.log(erro.response?.data);

    console.log("REQUEST:");
    console.log(usuario);

    alert(JSON.stringify(erro.response?.data));

}

    }

    return (

        <MainLayout>

            <PageHeader

                titulo={
                    id
                        ? "Editar Usuário"
                        : "Novo Usuário"
                }

                subtitulo="Cadastro de usuários"

            />

            <AppCard>

                <form onSubmit={salvar}>

                    <div className="mb-3">

                        <label className="form-label">

                            Nome

                        </label>

                        <input

                            className="form-control"

                            value={usuario.nome}

                            onChange={(e) =>

                                setUsuario({

                                    ...usuario,

                                    nome: e.target.value

                                })

                            }

                            required

                        />

                    </div>

                    <div className="mb-3">

                        <label className="form-label">

                            Username

                        </label>

                        <input

                            className="form-control"

                            value={usuario.username}

                            onChange={(e) =>

                                setUsuario({

                                    ...usuario,

                                    username: e.target.value

                                })

                            }

                            required

                        />

                    </div>

                    <div className="mb-3">

                        <label className="form-label">

                            Senha

                        </label>

                        <input

                            type="password"

                            className="form-control"

                            placeholder={
                                id
                                    ? "Deixe em branco para manter a senha"
                                    : ""
                            }

                            value={usuario.senha}

                            onChange={(e) =>

                                setUsuario({

                                    ...usuario,

                                    senha: e.target.value

                                })

                            }

                            required={!id}

                        />

                    </div>

                    <div className="mb-3">

                        <label className="form-label">

                            Perfil

                        </label>

                        <select

                            className="form-select"

                            value={usuario.role}

                            onChange={(e) =>

                                setUsuario({

                                    ...usuario,

                                    role: e.target.value,

                                    prefeituraId:

                                        e.target.value === "ROLE_SUPER_ADMIN"

                                            ? ""

                                            : usuario.prefeituraId

                                })

                            }

                        >

                            <option value="ROLE_SUPER_ADMIN">

                                Super Administrador

                            </option>

                            <option value="ROLE_ADMIN_PREFEITURA">

                                Administrador Prefeitura

                            </option>

                            <option value="ROLE_USUARIO">

                                Usuário

                            </option>

                        </select>

                    </div>

                    {

                        usuario.role !== "ROLE_SUPER_ADMIN" && (

                            <div className="mb-3">

                                <label className="form-label">

                                    Prefeitura

                                </label>

                                <select

                                    className="form-select"

                                    value={usuario.prefeituraId}

                                    onChange={(e) =>

                                        setUsuario({

                                            ...usuario,

                                            prefeituraId:

                                                e.target.value

                                        })

                                    }

                                    required

                                >

                                    <option value="">

                                        Selecione...

                                    </option>

                                    {

                                        prefeituras.map(prefeitura => (

                                            <option

                                                key={prefeitura.id}

                                                value={prefeitura.id}

                                            >

                                                {prefeitura.nome}

                                            </option>

                                        ))

                                    }

                                </select>

                            </div>

                        )

                    }

                    <div className="mt-4 d-flex gap-2">

                        <button

                            type="submit"

                            className="btn btn-gold"

                        >

                            Salvar

                        </button>

                        <button

                            type="button"

                            className="btn btn-secondary"

                            onClick={() =>

                                navigate("/usuarios")

                            }

                        >

                            Cancelar

                        </button>

                    </div>

                </form>

            </AppCard>

        </MainLayout>

    );

}

export default UsuarioForm;