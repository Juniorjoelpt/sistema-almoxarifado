import { useNavigate } from "react-router-dom";
import { useState } from "react";
import api from "../services/api";
import "./Login.css";
import {
    FaUser,
    FaLock,
    FaEye,
    FaEyeSlash
} from "react-icons/fa";

function Login() {

    const [username, setUsername] = useState("");
    const [senha, setSenha] = useState("");
    const [erro, setErro] = useState("");
    const navigate = useNavigate();
    const [mostrarSenha, setMostrarSenha] = useState(false);
    const [carregando, setCarregando] = useState(false);
    const [usuarioFocus, setUsuarioFocus] = useState(false);
    const [senhaFocus, setSenhaFocus] = useState(false);

    async function handleLogin(e) {

        e.preventDefault();
        setCarregando(true);
	setErro("");

        try {

            const response = await api.post("/auth/login", {
                username,
                senha
            });

            console.log("LOGIN RESPONSE");
            console.log(response.data);

            localStorage.setItem("token", response.data.token);
            localStorage.setItem("nomeUsuario", response.data.nome);
            localStorage.setItem("username", response.data.username);
            localStorage.setItem("role", response.data.role);

            navigate("/dashboard", { replace: true });

        } catch (err) {

            console.error(err);

            setErro(
                err.response?.data?.message ||
                "Erro ao fazer login"
            );
		setCarregando(false);

        }

    }

    return (

<div className="login-page">

	<div className="background-circle circle-1"></div>

    <div className="background-circle circle-2"></div>

    <div className="background-circle circle-3"></div>

    <div className="login-card">

        <div className="login-header">

            <img
                src="/images/ms-logo.png"
                alt="MS Soluções Inteligentes"
                className="login-logo"
            />

            <h2>MS Soluções Inteligentes</h2>

            <p>
                Sistema de Gestão de Almoxarifado
            </p>

        </div>

        <form onSubmit={handleLogin}>

           <div className="mb-3">

    <label>Usuário</label>

    <div
    className={`input-icon ${
        usuarioFocus ? "input-focus" : ""
    }`}
>

        <FaUser
    className={`input-icon-left ${
        usuarioFocus ? "icon-focus" : ""
    }`}
/>

        <input
            type="text"
            className="form-control input-custom"
            value={username}
            onChange={(e)=>setUsername(e.target.value)}
		onFocus={() => setUsuarioFocus(true)}

		onBlur={() => setUsuarioFocus(false)}
        />

    </div>

</div>

            <div className="mb-3">

    <label>Senha</label>

    <div
className={`input-icon ${
    senhaFocus ? "input-focus" : ""
}`}
>

       <FaLock
    className={`input-icon-left ${
        senhaFocus ? "icon-focus" : ""
    }`}
/>

        <input

            type={mostrarSenha ? "text" : "password"}

            className="form-control input-custom"

            value={senha}

            onChange={(e)=>setSenha(e.target.value)}
		onFocus={() => setSenhaFocus(true)}

		onBlur={() => setSenhaFocus(false)}

        />

        <button

            type="button"

            className="btn-eye"

            onClick={()=>

                setMostrarSenha(!mostrarSenha)

            }

        >

            {

                mostrarSenha

                    ? <FaEyeSlash/>

                    : <FaEye/>

            }

        </button>

    </div>

</div>

            {

                erro && (

                    <div className="alert alert-danger">

                        {erro}

                    </div>

                )

            }

            <button
    type="submit"
    className="btn-login w-100"
    disabled={carregando}
>

    {
        carregando ? (
            <>
                <span className="spinner-border spinner-border-sm me-2"></span>
                Entrando...
            </>
        ) : (
            "Entrar"
        )
    }

</button>

        </form>

    </div>

</div>

);

}

export default Login;
