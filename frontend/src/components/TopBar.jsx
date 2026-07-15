import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import api from "../services/api";

import {
    FaUserCircle,
    FaSignOutAlt,
    FaClock,
    FaHome
} from "react-icons/fa";

function TopBar() {

    const navigate = useNavigate();

    const location = useLocation();

    const [hora, setHora] = useState(new Date());
    const [prefeituras, setPrefeituras] = useState([]);
    
    const role = localStorage.getItem("role");
   const [prefeituraSelecionada, setPrefeituraSelecionada] = useState(
    localStorage.getItem("prefeituraSelecionada") || ""
);
    const prefeituraAtual = prefeituras.find(

    p => String(p.id) === String(prefeituraSelecionada)

);

   useEffect(() => {

    const timer = setInterval(() => {
        setHora(new Date());
    }, 1000);

    if (role === "ROLE_SUPER_ADMIN") {
        carregarPrefeituras();
    }

    return () => clearInterval(timer);

}, []);

    function sair(){

        localStorage.clear();

        navigate("/login");

    }

    const paginas = {

        "/dashboard":"Dashboard",

        "/produtos":"Produtos",

        "/categorias":"Categorias",

        "/fornecedores":"Fornecedores",

        "/setores":"Setores",

        "/usuarios":"Usuários",

        "/prefeituras":"Prefeituras",

        "/movimentacoes":"Movimentações",

        "/movimentacoes/entrada":"Entrada",

        "/movimentacoes/saida":"Saída",

        "/auditoria":"Auditoria"

    };

    const titulo = paginas[location.pathname] || "Sistema";
    
    const nomeUsuario =
    localStorage.getItem("nomeUsuario") || "";

const iniciais = nomeUsuario
    .split(" ")
    .filter(p => p.length > 0)
    .slice(0, 2)
    .map(p => p[0].toUpperCase())
    .join("");

async function carregarPrefeituras() {

    try {

        console.log("Buscando prefeituras...");

        const response = await api.get("/api/prefeituras");

        console.log("STATUS:", response.status);
        console.log("DADOS:", response.data);

        setPrefeituras(response.data);

    } catch (erro) {

        console.log("ERRO COMPLETO:");
        console.log(erro);

        console.log("STATUS:");
        console.log(erro.response?.status);

        console.log("BODY:");
        console.log(erro.response?.data);

    }

}
function trocarPrefeitura(e) {

    const id = e.target.value;

    setPrefeituraSelecionada(id);

    localStorage.setItem(
        "prefeituraSelecionada",
        id
    );

    window.location.reload();

}
console.log(prefeituraAtual);
console.log(prefeituraAtual?.logo);
console.log(import.meta.env.VITE_API_URL);
console.log(
    `${import.meta.env.VITE_API_URL}${prefeituraAtual?.logo}`
);

    return(

        <header className="topbar">

            <div>

                <div className="breadcrumb-top">

                    <FaHome />

                    <span>

                        Dashboard

                    </span>

                    <span>

                        /

                    </span>

                    <strong>

                        {titulo}

                    </strong>

                </div>

                <div className="topbar-title">

                    Sistema de Almoxarifado

                </div>

            </div>

            <div
    className="d-flex align-items-center me-3"
    style={{
        gap: "22px"
    }}
>

    {prefeituraAtual?.logo && (

        <img
            src={`${import.meta.env.VITE_API_URL}${prefeituraAtual.logo}`}
            alt="Logo"
            style={{
                width: "45px",
                height: "45px",
                objectFit: "contain",
                marginRight: "10px"
            }}
        />

    )}

 {

        role === "ROLE_SUPER_ADMIN" && (
	<div className="topbar-prefeitura">
            <select
                className="form-select form-select-sm"
                style={{ width: "300px" }}
                value={prefeituraSelecionada}
                onChange={trocarPrefeitura}
            >

                <option value="">

                    Selecione uma prefeitura

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

	<div className="topbar-divider"></div>
    	<div className="topbar-item">

    <FaClock
        style={{
            color:"#d39a32"
        }}
    />

    <span>

        {hora.toLocaleDateString("pt-BR")}

        <br/>

        <small>

            {hora.toLocaleTimeString("pt-BR")}

        </small>

    </span>

</div>

	<div className="topbar-divider"></div>

       <div className="topbar-user-info">

    <div className="topbar-avatar">

        {iniciais}

    </div>

    <div>

        <div className="topbar-user-name">

            {nomeUsuario}

        </div>

        <small className="topbar-user-role">

            Usuário

        </small>

    </div>

</div>


	<div className="topbar-divider"></div>
    <button
        className="btn btn-outline-danger btn-sm"
        onClick={sair}
    >

        <FaSignOutAlt className="me-2"/>

        Sair

    </button>

</div>

        </header>

    );

}

export default TopBar;
