import { useEffect, useRef } from "react";
import logoMs from "../assets/logo-ms.png";
import { NavLink,useLocation,useNavigate } from "react-router-dom";
import {
  FaChartPie,
  FaBox,
  FaTags,
  FaTruck,
  FaBuilding,
  FaArrowDown,
  FaArrowUp,
  FaClipboardList,
  FaHistory,
  FaUserCircle,
  FaSignOutAlt
} from "react-icons/fa";

import "../styles/layout.css";

function Sidebar() {
	const sidebarMenuRef = useRef(null);
	const location = useLocation();
	const role =localStorage.getItem("role");
	const navigate = useNavigate();

function sair() {

    localStorage.clear();

    sessionStorage.clear();

    navigate("/login");

}

useEffect(() => {

    const menu = sidebarMenuRef.current;

    if (!menu) return;

    const posicao = sessionStorage.getItem("sidebarScroll");

    if (posicao) {
        menu.scrollTop = Number(posicao);
    }

}, [location]);

useEffect(() => {

    const menu = sidebarMenuRef.current;

    if (!menu) return;

    const salvarScroll = () => {

        sessionStorage.setItem(
            "sidebarScroll",
            menu.scrollTop
        );

    };

    menu.addEventListener("scroll", salvarScroll);

    return () => {

        menu.removeEventListener("scroll", salvarScroll);

    };

}, []);




  return (
	

      <aside className="sidebar">

    <div className="sidebar-header text-center mb-4">

      <img
    src={logoMs}
    alt="MS Soluções"
    className="sidebar-logo"
	/>
	

    </div>
	<div className="sidebar-menu"ref={sidebarMenuRef}>
      <nav>
	<div className="menu-title">
        GERAL
    </div>
        <NavLink  to="/dashboard"
    className="sidebar-link">
          <FaChartPie className="menu-icon" />
          Dashboard
        </NavLink>

	<div className="menu-title">
        CADASTROS
    </div>

	 <NavLink to="/produtos"
    className="sidebar-link">
          <FaBox className="menu-icon"/>
          Produtos
        </NavLink>
	
	 <NavLink to="/categorias"
    className="sidebar-link">
          <FaTags className="menu-icon" />
          Categorias
        </NavLink>

	 <NavLink to="/fornecedores"
    className="sidebar-link">
          <FaTruck className="menu-icon" />
          Fornecedores
        </NavLink>

	 <NavLink to="/setores"
    className="sidebar-link">
          <FaBuilding className="menu-icon"/>
          Setores
        </NavLink>

	<div className="menu-title">
        Movimentação
    </div>
	
	<NavLink to="/movimentacoes/entrada"
    className="sidebar-link">
          <FaArrowDown className="menu-icon"/>
          Entrada
        </NavLink>

	<NavLink to="/movimentacoes/saida"className="sidebar-link">
          <FaArrowUp className="menu-icon"/>
          Saída
        </NavLink>

	<NavLink to="/movimentacoes" end className="sidebar-link">
          <FaClipboardList className="menu-icon"/>
          Movimentações
        </NavLink>

	<div className="menu-title">
        CONTROLE
    </div>
	<NavLink to="/auditoria"className="sidebar-link">
          <FaHistory className="menu-icon"/>
          Auditoria
        </NavLink>

{
  	role === "ROLE_SUPER_ADMIN" && (

	<div className="menu-title">
        ADMINISTRAÇÃO
    </div>
	)
	}

	{
  	role === "ROLE_SUPER_ADMIN" && (

    	<NavLink
     	 to="/prefeituras"
      	className="sidebar-link"
    	>
      	<FaBuilding  className="menu-icon"/>
      	Prefeituras
    	</NavLink>
	)
	}
	
	{
  role === "ROLE_SUPER_ADMIN" && (

    	<NavLink
      	to="/usuarios"
      	className="sidebar-link"
    	>
      	<FaUserCircle  className="menu-icon"/>
      	Usuários
   	</NavLink>
	)
	}


     

      </nav>
	</div>
	 <div className="sidebar-footer">

        <div className="user-box">

            <strong>
    {localStorage.getItem("nomeUsuario") ||
     localStorage.getItem("username")}
</strong>

            <small>{localStorage.getItem("prefeituraNome")}</small>

        </div>

        <button 
	className="btn btn-outline-light btn-sm w-100 mt-3"
    	onClick={sair}
	>
		
            <FaSignOutAlt className="me-2"/>
		
            Sair

        </button>

        <hr />

        <small>© 2026</small>

        <br/>

        <small>MS Soluções Inteligentes</small>

    </div>

</aside>
	
  );

}



export default Sidebar;